package com.marcoas99.youtubescraper.model;

import lombok.Builder;
import lombok.Data;

import java.net.URL;
import java.util.List;

@Data
@Builder
public class HomePage {
    private String channelName;
    private URL channelUrl;
    private String subscriberCount;
    private Video featuredVideo;
    private List<Playlist> featuredPlaylists;
}
