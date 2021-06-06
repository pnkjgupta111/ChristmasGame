package message;

import java.util.ArrayList;
import java.util.List;

public class MessageBroker implements Publisher {
    private List<Subscriber> subscribers = new ArrayList<>();

    @Override
    public void publishMessage(final Message message) {
        subscribers.forEach(subscriber -> subscriber.onMessageReceive(message));
    }

    @Override
    public void subscribe(final Subscriber subscriber) {
        subscribers.add(subscriber);
    }
}
