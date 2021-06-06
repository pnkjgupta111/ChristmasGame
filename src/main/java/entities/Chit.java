package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import message.Message;

@EqualsAndHashCode
@Getter
@Setter
public class Chit {
    private Predicate<Integer> ballCriteria;
    private Predicate<Integer> floorCriteria;
    private List<Message> matchingBalls;

    public Chit(final Predicate<Integer> ballCriteria, final Predicate<Integer> floorCriteria) {
        this.ballCriteria = ballCriteria;
        this.floorCriteria = floorCriteria;
        matchingBalls = new ArrayList<>();
    }

    public void writeOnChit(final Message message) {
        matchingBalls.add(message);
    }

    public boolean isSatisfyChitCriteria(final int ballNumber, final int floorNumber) {
        return ballCriteria.test(ballNumber) && floorCriteria.test(floorNumber);
    }

    public boolean areAllMatchingBallsCorrect() {
        Optional<Message> anyIncorrectBall =
                matchingBalls.stream().filter(message -> !isSatisfyChitCriteria(message.getBallNumber(), message.getFloor())).findAny();
        return !anyIncorrectBall.isPresent();
    }
}
