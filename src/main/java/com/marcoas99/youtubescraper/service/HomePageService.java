package com.marcoas99.youtubescraper.service;

import com.marcoas99.youtubescraper.model.Channel;
import com.marcoas99.youtubescraper.model.Playlist;
import com.marcoas99.youtubescraper.model.Video;
import com.marcoas99.youtubescraper.repository.ChannelRepository;
import com.marcoas99.youtubescraper.repository.PlaylistRepository;
import com.marcoas99.youtubescraper.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomePageService {

    private final YoutubeScraperService youtubeScraperService;
    private final VideoRepository videoRepository;
    private final PlaylistRepository playlistRepository;
    private final ChannelRepository channelRepository;

    public Channel getHomePage(String homePageUrl) throws MalformedURLException {
        Document document = youtubeScraperService.getPage(homePageUrl);
        Video featuredVideo = youtubeScraperService.getFeaturedVideo(document);
        String channelName = youtubeScraperService.getChannelName(document);
        String subscriberCount = youtubeScraperService.getChannelSubscribers(document);
        List<Playlist> playlistList = youtubeScraperService.getPlaylistList(document);

        videoRepository.save(featuredVideo);
        playlistList.forEach(playlist-> videoRepository.saveAll(playlist.getVideoList()));
        playlistRepository.saveAll(playlistList);

        Channel channel = Channel.builder()
                .name(channelName)
                .url(new URL(homePageUrl))
                .subscriberCount(subscriberCount)
                .featuredVideo(featuredVideo)
                .featuredPlaylists(playlistList)
                .build();

        return channelRepository.save(channel);
    }
}
