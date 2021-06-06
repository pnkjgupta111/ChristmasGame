package factory;

import java.util.function.Predicate;

import model.CriteriaType;

public interface CriteriaFactory {
    Predicate<Integer> createCriteria(CriteriaType criteriaType);
}
