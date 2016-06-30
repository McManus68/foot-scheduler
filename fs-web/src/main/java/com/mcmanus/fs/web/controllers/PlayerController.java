package com.mcmanus.fs.web.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.mcmanus.fs.model.jpa.Player;
import com.mcmanus.fs.model.views.View;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/players")
public class PlayerController extends EntityController<Player> {

    @Override
    @JsonView(View.Player.class)
    public List<Player> getAll() {
        return super.getAll();
    }
}
