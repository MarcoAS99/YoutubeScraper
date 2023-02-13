package com.marcoas99.youtubescraper.repository;

import com.marcoas99.youtubescraper.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Channel findChannelByNameEqualsIgnoreCase(String name);

}
