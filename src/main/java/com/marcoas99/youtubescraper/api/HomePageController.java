package com.marcoas99.youtubescraper.api;

import com.marcoas99.youtubescraper.model.Channel;
import com.marcoas99.youtubescraper.service.HomePageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;



@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class HomePageController {

    private final HomePageService homePageService;

    @PostMapping("/homepage")
    @Operation(description = "Given a YouTube Channel URL it returns the a resume of the HomePage.")
    public ResponseEntity<Channel> getChannel(@RequestBody String channelUrlString) throws MalformedURLException{
        Channel channel = homePageService.getHomePage(channelUrlString);
        return ResponseEntity.ok(channel);
    }

}
