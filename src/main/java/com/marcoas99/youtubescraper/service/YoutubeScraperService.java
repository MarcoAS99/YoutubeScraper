package com.marcoas99.youtubescraper.service;

import com.marcoas99.youtubescraper.model.Playlist;
import com.marcoas99.youtubescraper.model.Video;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class YoutubeScraperService {

    public Document getPage(String url) {
        WebDriver webDriver;
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless","--mute-audio","start-maximized");
        webDriver = WebDriverManager.chromedriver().capabilities(chromeOptions).create();

        webDriver.get(url);

        //Skip YouTube Terms
        WebElement element = webDriver.findElement(new By.ByXPath("//*[@id=\"yDmH0d\"]/c-wiz/div/div/div/div[2]/div[1]/div[3]/div[1]/form[2]/div/div/button"));
        element.click();
        //Get the full html loaded page
        By.ByXPath featuredVideoXPath = new By.ByXPath("//*[@id=\"c4-player\"]/div[1]/video");
        element = webDriver.findElement(featuredVideoXPath);
        element.click();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofMillis(1000));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#metadata-container > ytd-video-meta-block")));
        String pageHtml = ((JavascriptExecutor)webDriver).executeScript("return document.documentElement.outerHTML;").toString();
        webDriver.quit();

        return Jsoup.parse(pageHtml);
    }
    public String getChannelName(Document document){
        return document.selectFirst("#text").text();
    }

    public String getChannelSubscribers(Document document){
        return document.getElementById("subscriber-count").text();
    }

    public Video getFeaturedVideo(Document document) throws MalformedURLException {
        Element videoThumbnail = document.getElementsByTag("ytd-item-section-renderer").first().getElementsByTag("ytd-player").first();
        String duration = videoThumbnail.getElementsByClass("ytp-time-duration").text();
        String title = videoThumbnail.getElementsByClass("ytp-title-text").text();
        String href = videoThumbnail.getElementsByClass("ytp-title").select("a.ytp-title-link").attr("href");

        Element featuredMetadata = document.select("#content").first();
        String views = featuredMetadata.getElementById("metadata-line").select("span:nth-child(3)").text();
        String when = featuredMetadata.getElementById("metadata-line").select("span:nth-child(4)").text();

        return Video.builder()
                .title(title)
                .url(new URL(href))
                .duration(duration)
                .views(views)
                .uploaded(when)
                .build();
    }

    public List<Playlist> getPlaylistList(Document document) throws MalformedURLException {
        List<Playlist> playlistList = new ArrayList<>();
        Elements sections = document.getElementsByTag("ytd-item-section-renderer");
        for (int i = 1; i<sections.size(); i++){
            if (sections.get(i).getElementsByTag("ytd-grid-video-renderer").isEmpty()){
                continue;
            }
            Element playlistSection = sections.get(i).getElementsByTag("ytd-shelf-renderer").first();
            playlistList.add(getPlaylist(playlistSection));
        }
        return playlistList;
    }
    private Playlist getPlaylist(Element playlistSection) throws MalformedURLException {
        String playlistName = playlistSection.getElementById("title-text").text();
        Elements videosSection = playlistSection.getElementsByTag("ytd-grid-video-renderer");
        List<Video> videoList = new ArrayList<>();
        for (Element video : videosSection) {
            videoList.add(getVideo(video));
        }
        return Playlist.builder()
                .name(playlistName)
                .videoList(videoList)
                .build();
    }

    private Video getVideo(Element videoSection) throws MalformedURLException {
        Element videoThumbnail = videoSection.getElementsByTag("ytd-thumbnail").first();
        Element videoDetails = videoSection.getElementById("details");

        return Video.builder()
                .title(videoDetails.getElementsByTag("h3").text())
                .url(new URL("https://www.youtube.com" + videoDetails.getElementsByTag("a").first().attr("href")))
                .duration(videoThumbnail.getElementsByTag("span").first().getElementById("text").text())
                .views(videoDetails.getElementById("metadata-line").getElementsByTag("span").get(0).text())
                .uploaded(videoDetails.getElementById("metadata-line").getElementsByTag("span").get(1).text())
                .build();
    }
}
