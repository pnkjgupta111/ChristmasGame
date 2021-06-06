package entities;

import java.util.UUID;
import java.util.function.Predicate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import message.Message;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Chit {
    private Predicate<Integer> ballCriteria;
    private Predicate<Integer> floorCriteria;
    private Message latestNotedDownBall;
    private String assignedGuestId;
    @EqualsAndHashCode.Include private String chitId = UUID.randomUUID().toString();

    public Chit(final Predicate<Integer> ballCriteria, final Predicate<Integer> floorCriteria) {
        this.ballCriteria = ballCriteria;
        this.floorCriteria = floorCriteria;
    }

    public void writeOnChit(final Message message) {
        latestNotedDownBall = message;
    }

    public boolean isSatisfyChitCriteria(final int ballNumber, final int floorNumber) {
        return ballCriteria.test(ballNumber) && floorCriteria.test(floorNumber);
    }
}
