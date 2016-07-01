package com.mcmanus.fs.model.jpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "foot_event")
public class FootEvent extends AbstractEntity {

    @Id @GeneratedValue
    private Long id;

    private LocalDateTime date;

    private String description;

    @OneToMany(mappedBy = "footEvent", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<FootEventPlayer> footEventPlayers;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FootEventPlayer> getFootEventPlayers() {
        return footEventPlayers;
    }

    public void setFootEventPlayers(List<FootEventPlayer> footEventPlayers) {
        this.footEventPlayers = footEventPlayers;
    }
}
