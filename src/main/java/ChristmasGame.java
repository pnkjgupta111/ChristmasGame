import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import entities.Chit;
import entities.Guest;
import entities.Santa;
import entities.SantaHelper;
import factory.CriteriaFactory;
import factory.CriteriaFactoryImpl;
import message.MessageBroker;
import model.CriteriaType;
import utils.CommonUtils;

public class ChristmasGame {
    private static final int MIN_NUMBER_OF_YELLS = 7;
    private CriteriaFactory criteriaFactory;
    private MessageBroker messageBroker;
    private Santa santa;
    private List<Chit> chits;
    private List<Guest> guests;
    private int numberOfYells;
    private SantaHelper santaHelper;

    public ChristmasGame() {
        prepareGameEntities();
    }

    private void prepareGameEntities() {
        numberOfYells = MIN_NUMBER_OF_YELLS + CommonUtils.getRandom(MIN_NUMBER_OF_YELLS);
        System.out.println(String.format("Ready!!!!! Santa will yell %s number of times", numberOfYells));
        criteriaFactory = new CriteriaFactoryImpl();
        messageBroker = new MessageBroker();
        chits = createChits();
        santaHelper = new SantaHelper(chits);
        guests = createGuests();
        santa = new Santa(messageBroker, santaHelper);
    }

    public void launch() {
        IntStream.range(0, numberOfYells).forEach(i -> santa.yell());
        printWinners();
    }

    private void printWinners() {
        System.out.println("\nWINNERS:-");
        santaHelper.getWinners().forEach(guestId -> System.out.println(String.format("Guest%s wins the game", guestId)));
    }

    private List<Guest> createGuests() {
        AtomicInteger i = new AtomicInteger();
        return chits
                .stream()
                .map(chit -> new Guest(messageBroker, chit, i.getAndIncrement() + "", santaHelper))
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
