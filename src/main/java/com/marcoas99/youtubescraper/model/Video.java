package com.marcoas99.youtubescraper.model;

import lombok.Builder;
import lombok.Data;

import java.net.URL;
import java.time.LocalTime;

@Data
@Builder
public class Video {
    private String title;
    private LocalTime duration;
    private int likes;
    private int views;
    private String playlist;
    private URL url;

}
