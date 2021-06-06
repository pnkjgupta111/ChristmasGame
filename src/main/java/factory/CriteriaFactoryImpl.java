package factory;

import java.util.function.Predicate;

import model.CriteriaType;

public class CriteriaFactoryImpl implements CriteriaFactory {
    @Override
    public Predicate<Integer> createCriteria(final CriteriaType criteriaType) {
        switch (criteriaType) {
            case ODD:
                return number -> number % 2 != 0;
            case EVEN:
                return number -> number % 2 == 0;
            case DIVISBLE_BY_FIVE:
                return number -> number % 5 == 0;
            default:
                return number -> true;
        }
    }
}
