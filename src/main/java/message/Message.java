package message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    int ballNumber;
    int floor;
}
