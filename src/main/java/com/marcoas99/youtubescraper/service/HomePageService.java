package com.marcoas99.youtubescraper.service;

import com.marcoas99.youtubescraper.model.HomePage;
import com.marcoas99.youtubescraper.model.Video;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class HomePageService {

    private final YoutubeScraperService youtubeScraperService;

    public HomePageService(YoutubeScraperService youtubeScraperService) {
        this.youtubeScraperService = youtubeScraperService;
    }

    public HomePage getHomePage(String homePageUrl) throws MalformedURLException {
        Document document = youtubeScraperService.getPage(homePageUrl);
        Video featuredVideo = youtubeScraperService.getFeaturedVideo(document);
        String channelName = youtubeScraperService.getChannelName(document);
        String subscriberCount = youtubeScraperService.getChannelSubscribers(document);

        return HomePage.builder()
                .channelName(channelName)
                .channelUrl(new URL(homePageUrl))
                .subscriberCount(subscriberCount)
                .featuredVideo(featuredVideo)
                .build();
    }
}
