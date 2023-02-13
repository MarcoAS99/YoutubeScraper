package com.marcoas99.youtubescraper.repository;

import com.marcoas99.youtubescraper.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
