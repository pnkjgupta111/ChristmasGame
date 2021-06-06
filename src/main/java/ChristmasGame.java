import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import entities.Chit;
import entities.Guest;
import entities.Santa;
import factory.CriteriaFactory;
import factory.CriteriaFactoryImpl;
import message.Message;
import message.MessageBroker;
import model.CriteriaType;
import utils.CommonUtils;

public class ChristmasGame {
    private static final int MIN_NUMBER_OF_YELLS = 5;
    private CriteriaFactory criteriaFactory;
    private MessageBroker messageBroker;
    private Santa santa;
    private List<Chit> chits;
    private List<Guest> guests;
    private int numberOfYells;

    public ChristmasGame() {
        prepareGameEntities();
    }

    private void prepareGameEntities() {
        numberOfYells = MIN_NUMBER_OF_YELLS + CommonUtils.getRandom(MIN_NUMBER_OF_YELLS);
        System.out.println(String.format("Ready!!!!! Santa will yell %s number of times", numberOfYells));
        criteriaFactory = new CriteriaFactoryImpl();
        messageBroker = new MessageBroker();
        santa = new Santa(messageBroker);
        chits = createChits();
        guests = createGuests();
    }

    public void launch() {
        List<Message> messages = IntStream.range(0, numberOfYells).mapToObj(i -> santa.yell()).collect(Collectors.toList());
        printWinners(messages);
    }

    private boolean isWinner(final Guest guest, final List<Message> messages) {
        List<Message> expectedBalls = getBallsMatchingChitCriteria(guest.getChit(), messages);
        List<Message> actualBallsNotedByGuest = getBallsMatchingChitCriteria(guest.getChit(), guest.getChit().getMatchingBalls());
        return expectedBalls.equals(actualBallsNotedByGuest);
    }

    private List<Message> getBallsMatchingChitCriteria(final Chit chit, final List<Message> balls) {
        return balls
                .stream()
                .filter(message -> chit.isSatisfyChitCriteria(message.getBallNumber(), message.getFloor()))
                .collect(Collectors.toList());
    }

    private void printWinners(final List<Message> messages) {
        System.out.println("\nWINNERS:-");
        guests
                .stream()
                .filter(guest -> isWinner(guest, messages))
                .forEach(guest -> System.out.println(String.format("Guest%s wins the game", guest.getGuestId())));
    }

    private List<Guest> createGuests() {
        AtomicInteger i = new AtomicInteger();
        return chits
                .stream()
                .map(chit -> new Guest(messageBroker, chit, i.getAndIncrement() + ""))
                .peek(guest -> System.out.println(String.format("Guest%s joins the game", guest.getGuestId())))
                .collect(Collectors.toList());
    }

    private List<Chit> createChits() {
        System.out.println("Creating chits.......");
        CommonUtils.sleepThreeSeconds();
        List<Chit> chits = new ArrayList<>();
        chits.add(new Chit(criteriaFactory.createCriteria(CriteriaType.EVEN), criteriaFactory.createCriteria(CriteriaType.EVEN)));
        chits.add(new Chit(criteriaFactory.createCriteria(CriteriaType.ODD), criteriaFactory.createCriteria(CriteriaType.ODD)));
        chits.add(new Chit(criteriaFactory.createCriteria(CriteriaType.EVEN), criteriaFactory.createCriteria(CriteriaType.ODD)));
        chits.add(
                new Chit(criteriaFactory.createCriteria(CriteriaType.DIVISBLE_BY_FIVE), criteriaFactory.createCriteria(CriteriaType.ANY)));
        return chits;
    }
}
