package com.mcmanus.fs.model.jpa;

import javax.persistence.*;

@Entity
@Table(name = "player_settings")
public class PlayerSettings extends AbstractEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "notify_new_event")
    private boolean notifyNewEvent;

    @Column(name = "notify_to_main_list")
    private boolean notifyToMainList;

    @Column(name = "notify_team_composition")
    private boolean notifyTeamComposition;

    @OneToOne(mappedBy = "settings")
    private Player player;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isNotifyNewEvent() {
        return notifyNewEvent;
    }

    public void setNotifyNewEvent(boolean notifyNewEvent) {
        this.notifyNewEvent = notifyNewEvent;
    }

    public boolean isNotifyToMainList() {
        return notifyToMainList;
    }

    public void setNotifyToMainList(boolean notifyToMainList) {
        this.notifyToMainList = notifyToMainList;
    }

    public boolean isNotifyTeamComposition() {
        return notifyTeamComposition;
    }

    public void setNotifyTeamComposition(boolean notifyTeamComposition) {
        this.notifyTeamComposition = notifyTeamComposition;
    }
}
