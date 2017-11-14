package io.enoy.tg.example.actions;

import io.enoy.tg.action.SimpleTgActionRegexDefinition;
import org.springframework.stereotype.Component;

@Component
public class SaveImageDefinition implements SimpleTgActionRegexDefinition {

    @Override
    public String getRegex() {
        return "\\/image";
    }

    @Override
    public String getName() {
        return "Save Image";
    }

    @Override
    public String getDescription() {
        return "Saves any image to the disk";
    }

}
