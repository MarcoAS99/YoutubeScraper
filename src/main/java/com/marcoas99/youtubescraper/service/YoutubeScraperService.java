package com.marcoas99.youtubescraper.service;

import com.marcoas99.youtubescraper.model.Video;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Service
public class YoutubeScraperService {

    public Document getPage(String url) {
        WebDriver webDriver;
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ytp-time-duration")));
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
        Element mainSection = document.getElementById("contents");
        String duration = mainSection.getElementsByClass("ytp-time-duration").text();
        String title = mainSection.getElementsByClass("ytp-title").text();
        String href = mainSection.getElementsByClass("ytp-title").select("a.ytp-title-link").attr("href");
        String views = document.select("#metadata-line > span:nth-child(3)").first().text();
        String when = document.select("#metadata-line > span:nth-child(4)").first().text();

        return Video.builder()
                .title(title)
                .url(new URL(href))
                .duration(duration)
                .views(views)
                .when(when)
                .build();
    }
}
