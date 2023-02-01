package com.marcoas99.youtubescraper.api;

import com.marcoas99.youtubescraper.model.HomePage;
import com.marcoas99.youtubescraper.service.HomePageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URL;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class HomePageController {

    private final HomePageService homePageService;

    @PostMapping
    @ApiOperation(value = "Given a YouTube HomePage URL it returns the a resume of the HomePage.", produces = "application/json")
    public ResponseEntity<HomePage> getHomePage(@RequestBody URL homePageUrl) {
        log.debug("Requesting homepage...");
        return ResponseEntity.ok(homePageService.getHomePage(homePageUrl));
    }

    @GetMapping("/test")
    public ResponseEntity<String> search() {
        log.debug("Requesting /test...");
        return ResponseEntity.ok("test");
    }
}
