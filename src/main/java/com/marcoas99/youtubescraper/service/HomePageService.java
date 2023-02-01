package com.marcoas99.youtubescraper.service;

import com.marcoas99.youtubescraper.model.HomePage;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class HomePageService {

    public HomePage getHomePage(URL homePageUrl){
        return HomePage.builder().build();
    }
}
