package message;

public interface Subscriber {
    void onMessageReceive(Message message);
}
