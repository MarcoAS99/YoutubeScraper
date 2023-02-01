package com.marcoas99.youtubescraper.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HomePage {
    private Video featuredVideo;
    private List<Playlist> featuredPlaylists;
}
