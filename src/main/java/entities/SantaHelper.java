package entities;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import message.Message;

public class SantaHelper {
    private Message currentBall;
    private Set<Chit> winningChits;

    public SantaHelper(final List<Chit> chits) {
        this.winningChits = new HashSet<>(chits);
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
        Iterator<Chit> iterator = winningChits.iterator();
        while (iterator.hasNext()) {
            Chit currentChit = iterator.next();
            boolean shouldNoteDownCurrent = currentChit.isSatisfyChitCriteria(currentBall.getBallNumber(), currentBall.getFloor());
            if (shouldNoteDownCurrent && !currentBall.equals(currentChit.getLatestNotedDownBall())) {
                System.out.println(String.format("[DEBUG] Guest%s cannot win the game", currentChit.getAssignedGuestId()));
                iterator.remove();
            }
        }
    }
}
