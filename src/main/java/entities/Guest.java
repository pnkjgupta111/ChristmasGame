package entities;

import message.Message;
import message.MessageBroker;
import message.Subscriber;

public class Guest implements Subscriber {
    private MessageBroker messageBroker;
    private Chit chit;
    private String guestId;

    public Guest(final MessageBroker messageBroker, final Chit chit, final String guestId) {
        this.messageBroker = messageBroker;
        this.chit = chit;
        this.guestId = guestId;
        listenToSanta();
    }

    @Override
    public void onMessageReceive(final Message message) {
        if (chit.isSatisfyChitCriteria(message.getBallNumber(), message.getFloor()) && isAttentive()) {
            System.out.println(String.format("Guest%s noting down this ball", guestId));
            chit.writeOnChit(message);
        }
    }

    /**
     * This method is to simulate the behavior of player when player is not attentive;
     *
     * @return Boolean value indicating player is attentive or not.
     */
    private boolean isAttentive() {
        boolean isAttentive = Math.random() < 0.6;
        if (!isAttentive) {
            System.out.println(String.format("Guest%s missed noting down the ball", guestId));
        }
        return isAttentive;
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
