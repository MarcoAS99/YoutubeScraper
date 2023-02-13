package com.marcoas99.youtubescraper.api;

import com.marcoas99.youtubescraper.model.Channel;
import com.marcoas99.youtubescraper.model.Video;
import com.marcoas99.youtubescraper.repository.ChannelRepository;
import com.marcoas99.youtubescraper.repository.VideoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/data")
public class DataController {
    private final ChannelRepository channelRepository;
    private final VideoRepository videoRepository;

    @GetMapping("/channel/all")
    @Operation(description = "Get all Channels in the database.")
    public ResponseEntity<List<Channel>> getAll(){
        return ResponseEntity.ok(channelRepository.findAll());
    }

    @GetMapping("channel/{channelName}")
    @Operation(description = "Get Channel with the given name in the database.")
    public ResponseEntity<Channel> getChannelByName(@Parameter(description = "Channel name") @PathVariable String channelName){
        Channel channel = channelRepository.findChannelByNameEqualsIgnoreCase(channelName);
        if(channel == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(channel);
    }

    @GetMapping("/featuredVideo/{channelName}")
    @Operation(description = "Get the featured video of a given channel")
    public ResponseEntity<Video> getFeaturedVideoByChannel(@Parameter(description = "Channel name") @PathVariable String channelName){
        Channel channel = channelRepository.findChannelByNameEqualsIgnoreCase(channelName);
        if(channel == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(channel.getFeaturedVideo());
    }
}
