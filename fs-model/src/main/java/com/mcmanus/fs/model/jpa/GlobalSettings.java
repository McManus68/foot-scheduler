package com.mcmanus.fs.model.jpa;

import javax.persistence.*;

@Entity
@Table(name = "global_setings")
public class GlobalSettings extends AbstractEntity {

    @Id @GeneratedValue
    private Long id;

    private String adress;

    @Column(name = "max_players")
    private Integer maxPlayers;

    @Column(name = "event_day")
    private String eventDay;

    @Column(name = "event_hour")
    private Integer eventHour;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getEventDay() {
        return eventDay;
    }

    public void setEventDay(String eventDay) {
        this.eventDay = eventDay;
    }

    public Integer getEventHour() {
        return eventHour;
    }

    public void setEventHour(Integer eventHour) {
        this.eventHour = eventHour;
    }
}
