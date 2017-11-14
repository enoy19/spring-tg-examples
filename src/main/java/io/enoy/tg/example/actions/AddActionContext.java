package io.enoy.tg.example.actions;

import io.enoy.tg.action.TgActionContext;
import org.telegram.telegrambots.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @see TgActionContext
 */
public class AddActionContext extends TgActionContext {

    private double currentNumber = 0;

    public void addNumber(double number) {
        currentNumber += number;
    }

    public double getCurrentNumber() {
        return currentNumber;
    }
}
