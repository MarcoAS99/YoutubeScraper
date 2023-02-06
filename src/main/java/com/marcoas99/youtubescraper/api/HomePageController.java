package com.marcoas99.youtubescraper.api;

import com.marcoas99.youtubescraper.model.HomePage;
import com.marcoas99.youtubescraper.service.HomePageService;
import com.marcoas99.youtubescraper.service.YoutubeScraperService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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
    @ApiOperation(value = "Given a YouTube HomePage URL it returns the a resume of the HomePage.",
    consumes = MediaType.TEXT_PLAIN_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HomePage> getHomePage(@RequestBody String homePageUrlString) throws MalformedURLException{
        HomePage homePage = homePageService.getHomePage(homePageUrlString);
        return ResponseEntity.ok(homePage);
    }

}
