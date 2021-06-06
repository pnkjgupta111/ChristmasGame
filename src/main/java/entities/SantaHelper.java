package entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import message.Message;

public class SantaHelper {
    private Message currentBall;
    private Set<Chit> winningChits;

    public SantaHelper(final List<Chit> chits) {
        this.winningChits = new CopyOnWriteArraySet<>(chits);
    }

    public Set<String> getWinners() {
        return winningChits.stream().map(Chit::getAssignedGuestId).collect(Collectors.toSet());
    }

    public Message getCurrentBall() {
        return currentBall;
    }

    public void setCurrentBall(final Message currentBall) {
        this.currentBall = currentBall;
    }

    public void verifyChits() {
        for (Chit chit : winningChits) {
            boolean shouldNoteDownCurrent = chit.isSatisfyChitCriteria(currentBall.getBallNumber(), currentBall.getFloor());
            if (shouldNoteDownCurrent && !currentBall.equals(chit.getLatestNotedDownBall())) {
                winningChits.remove(chit);
                System.out.println(String.format("[DEBUG] Guest%s cannot win the game", chit.getAssignedGuestId()));
            }
        }
    }
}
