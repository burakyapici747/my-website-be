package com.blog.mywebsite.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class CommonSpecification<T> implements Specification<T> {
    private final transient List<SearchCriteria> searchCriteriaList;

    public CommonSpecification(){
        this.searchCriteriaList = new ArrayList<>();
    }

    public void add(SearchCriteria searchCriteria){
        searchCriteriaList.add(searchCriteria);
    }

    @Override
    public Specification<T> and(Specification<T> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<T> or(Specification<T> other) {
        return Specification.super.or(other);
    }

    @Override
    @SuppressWarnings("unchecked") // Unchecked cast warnings are suppressed here
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();

        searchCriteriaList.forEach(criteria -> {
            if(criteria.getSearchOperation().isNullable() || !ObjectUtils.isEmpty(criteria.getValue())){
                switch (criteria.getSearchOperation()){
                    case EQUAL:
                        predicateList.add(criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue()));
                        break;
                    case NOT_EQUAL:
                        predicateList.add(criteriaBuilder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
                        break;
                    case NOT_NULL:
                        predicateList.add(criteriaBuilder.isNotNull(root.get(criteria.getKey())));
                        break;
                    case GREATER_THAN:
                        predicateList.add(criteriaBuilder.greaterThan(root.get(criteria.getKey()), (Comparable<Object>) criteria.getValue()));
                        break;
                    case LESS_THAN:
                        predicateList.add(criteriaBuilder.lessThan(root.get(criteria.getKey()), (Comparable<Object>) criteria.getValue()));
                        break;
                    case GREATER_THAN_EQUAL:
                        predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()), (Comparable<Object>) criteria.getValue()));
                        break;
                    case LESS_THAN_EQUAL:
                        predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()), (Comparable<Object>) criteria.getValue()));
                        break;
                    case LIKE:
                        predicateList.add(criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%"));
                        break;
                }
            }
        });

        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}
