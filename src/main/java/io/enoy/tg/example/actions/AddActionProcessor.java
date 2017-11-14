package io.enoy.tg.example.actions;

import io.enoy.tg.action.TgActionProcessor;
import io.enoy.tg.action.message.TgMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;

import java.util.Objects;

/**
 * Scope tg = per telegram message
 */
@Component
@Scope("tg")
public class AddActionProcessor implements TgActionProcessor<AddActionContext, AddActionDefinition> {

    private final TgMessageService messageService;

    @Autowired
    public AddActionProcessor(TgMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public boolean process(AddActionContext addActionContext) {


        if (addActionContext.getInputs().size() == 1) {
            messageService.sendMessage("Send any number that you want to add together.\nsend OK if youre done!");
            // return FALSE when this action is not done. (needs some input...)
            return false;
        }

        Message latestMessage = addActionContext.getInputs().get(addActionContext.getInputs().size() - 1);
        String latestText = latestMessage.getText();

        // if an image was sent or anything but text...
        if (Objects.isNull(latestText) || latestText.trim().isEmpty()) {
            messageService.sendMessage("Invalid Message! Only Text allowed");
            return false;
        }

        latestText = latestText.trim();

        if(latestText.equalsIgnoreCase("ok")) {
            messageService.sendMessage(String.format("Total: %f", addActionContext.getCurrentNumber()));
            // return TRUE when this action is done!
            return true;
        }

        try {
            double number = Double.parseDouble(latestText);
            addActionContext.addNumber(number);
            messageService.sendMessage(String.format("Added %f!", number));
        } catch (NumberFormatException e) {
            messageService.sendMessage(String.format("Invalid Number: %s", latestText));
        }

        return false;

    }

    @Override
    public Class<AddActionContext> getActionContextClass() {
        return AddActionContext.class;
    }

    @Override
    public Class<AddActionDefinition> getActionDefinitionClass() {
        return AddActionDefinition.class;
    }

}
