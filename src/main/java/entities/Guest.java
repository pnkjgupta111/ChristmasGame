package entities;

import java.util.Objects;

import message.Message;
import message.MessageBroker;
import message.Subscriber;

public class Guest implements Subscriber {
    private MessageBroker messageBroker;
    private Chit chit;
    private String guestId;
    private SantaHelper santaHelper;

    public Guest(final MessageBroker messageBroker, final Chit chit, final String guestId, final SantaHelper santaHelper) {
        this.messageBroker = messageBroker;
        this.chit = chit;
        this.guestId = guestId;
        this.santaHelper = santaHelper;
        chit.setAssignedGuestId(guestId);
        listenToSanta();
    }

    @Override
    public void onMessageReceive(Message message) {
        if (chit.isSatisfyChitCriteria(message.getBallNumber(), message.getFloor())) {
            System.out.println(String.format("[DEBUG] Guest%s should note down this ball", guestId));
        }
        if (isMessageLost()) {
            message = null; // replicate message lost
            if (wantToRepeat()) {
                // Ask the santa again
                message = santaHelper.getCurrentBall();
            }
        }
        noteDownBall(message);
    }

    private void noteDownBall(final Message message) {
        if (!Objects.isNull(message)) {
            if (chit.isSatisfyChitCriteria(message.getBallNumber(), message.getFloor())) {
                System.out.println(String.format("Guest%s noting down this ball", guestId));
                chit.setLatestNotedDownBall(message);
            }
        }
    }

    /**
     * This method is to simulate the behavior of player when player is not attentive.
     *
     * @return Boolean value indicating player is attentive or not.
     */
    private boolean isMessageLost() {
        boolean isLost = Math.random() < 0.4;
        if (isLost) {
            System.out.println(String.format("Guest%s did not hear the santa", guestId));
        }
        return isLost;
    }

    /**
     * This method is to simulate the behavior of player when player wants the Santa to repeat a given number.
     *
     * @return Boolean value indicating player is attentive or not.
     */
    private boolean wantToRepeat() {
        boolean wantToRepeat = Math.random() < 0.5;
        if (wantToRepeat) {
            System.out.println(String.format("Guest%s: Let me ask the Santa again", guestId));
        }
        return wantToRepeat;
    }

    public Chit getChit() {
        return chit;
    }

    private void listenToSanta() {
        messageBroker.subscribe(this);
    }

    public String getGuestId() {
        return guestId;
    }
}
