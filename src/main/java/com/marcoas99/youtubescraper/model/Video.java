package com.marcoas99.youtubescraper.model;

import lombok.Builder;
import lombok.Data;

import java.net.URL;
import java.time.Duration;

@Data
@Builder
public class Video {
    private String title;
    private URL url;
    private String duration;
    private String views;
    private String when;


}
