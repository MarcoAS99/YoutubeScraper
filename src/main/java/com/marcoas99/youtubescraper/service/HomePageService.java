package com.marcoas99.youtubescraper.service;

import com.marcoas99.youtubescraper.model.HomePage;
import com.marcoas99.youtubescraper.model.Playlist;
import com.marcoas99.youtubescraper.model.Video;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

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
        List<Playlist> playlistList = youtubeScraperService.getPlaylistList(document);

        return HomePage.builder()
                .channelName(channelName)
                .channelUrl(new URL(homePageUrl))
                .subscriberCount(subscriberCount)
                .featuredVideo(featuredVideo)
                .featuredPlaylists(playlistList)
                .build();
    }
}
