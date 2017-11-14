package io.enoy.tg.example.actions;

import io.enoy.tg.action.TgActionRegexDefinition;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;

/**
 * can be singleton. Must not have a reference to a tg message/tg user
 */
@Component
public class AddActionDefinition implements TgActionRegexDefinition<AddActionContext> {

    /**
     * Regex to match the given command
     * @return the regex to match a command to (don't forget the slash '/')
     * @see io.enoy.tg.action.TgActionDefinition
     */
    @Override
    public String getRegex() {
        return "\\/add";
    }

    /**
     * will be used for a future "/help" command
     * @return name of this Action.
     */
    @Override
    public String getName() {
        return "Add";
    }

    /**
     * will be used for a future "/help" command
     * @return description of this Action. May contain an example.
     */
    @Override
    public String getDescription() {
        return null;
    }

    /**
     * To instantiate the context
     * @param message the command message that was received
     * @return the context
     */
    @Override
    public AddActionContext createAction(Message message) {
        // just use new... doesn't need to be complicated
        return new AddActionContext();
    }

    /**
     * don't ask... just do what you're told to.
     */
    @Override
    public Class<AddActionContext> getActionContextClass() {
        return AddActionContext.class;
    }
}
