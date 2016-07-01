package com.mcmanus.fs.model.jpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "foot_event_player")
public class FootEventPlayer extends AbstractEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "foot_event_id")
    private FootEvent footEvent;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public FootEvent getFootEvent() {
        return footEvent;
    }

    public void setFootEvent(FootEvent footEvent) {
        this.footEvent = footEvent;
    }
}
