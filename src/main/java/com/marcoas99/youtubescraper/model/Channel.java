package com.marcoas99.youtubescraper.model;

import lombok.*;
import javax.persistence.*;
import java.net.URL;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private URL url;
    private String subscriberCount;
    @OneToOne(targetEntity = Video.class)
    private Video featuredVideo;
    @OneToMany(targetEntity = Playlist.class)
    private List<Playlist> featuredPlaylists;
}
