package entities;

import static utils.CommonUtils.getRandom;

import lombok.AllArgsConstructor;
import message.Message;
import message.MessageBroker;
import utils.CommonUtils;

@AllArgsConstructor
public class Santa {
    private static final int MAX_FLOOR = 10;
    private static final int MAX_BALL_NUMBER = 100;
    private MessageBroker messageBroker;
    private SantaHelper santaHelper;

    public void yell() {
        Message yellMessage = Message.builder().ballNumber(getRandomBallNumber()).floor(getRandomFloor()).build();
        System.out.println("\nHohoooooo!");
        System.out.println(String.format("Santa yells ball %s on floor %s", yellMessage.getBallNumber(), yellMessage.getFloor()));
        santaHelper.setCurrentBall(yellMessage);
        CommonUtils.sleepThreeSeconds();
        messageBroker.publishMessage(yellMessage);
        santaHelper.verifyChits();
    }

    private int getRandomFloor() {
        return getRandom(MAX_FLOOR);
    }

    private int getRandomBallNumber() {
        return getRandom(MAX_BALL_NUMBER);
    }

}
