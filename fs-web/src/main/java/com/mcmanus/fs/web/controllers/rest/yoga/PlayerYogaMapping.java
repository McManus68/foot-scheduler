package com.mcmanus.fs.web.controllers.rest.yoga;

import com.mcmanus.fs.model.jpa.Player;
import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
public class PlayerYogaMapping extends YogaEntityConfiguration<Player> {

    @Override
    public Collection<String> getCoreFields() {
        return Arrays.asList("id", "username");
    }

    @Override
    public Collection<String> getSelectableFields() {
        return Arrays.asList("mail", "role");

    }
}
