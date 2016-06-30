package com.mcmanus.fs.web.controllers;

import com.mcmanus.fs.model.jpa.Player;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/players")
public class PlayerController extends EntityController<Player> {
}
