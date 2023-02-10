package com.marcoas99.youtubescraper.repository;

import com.marcoas99.youtubescraper.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
