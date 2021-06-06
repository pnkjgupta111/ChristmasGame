package message;

public interface Publisher {
    void publishMessage(Message message);

    void subscribe(Subscriber subscriber);
}
