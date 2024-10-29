package com.wszib.userservice.adapter.out.persistance.jpa;

import com.wszib.userservice.adapter.out.persistance.jpa.model.UserData;
import com.wszib.userservice.domain.querry.FilterCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserPersistenceAdapter implements UserPersistenceInterface {

    private final EntityManager entityManager;
    private final SpringDataRepository springDataRepository;

    @Autowired
    public UserPersistenceAdapter(
            EntityManager entityManager,
            SpringDataRepository springDataRepository) {
        this.entityManager = entityManager;
        this.springDataRepository = springDataRepository;
    }

    @Override
    public List<UserData> findAllBy(FilterCriteria filterCriteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserData> query = cb.createQuery(UserData.class);

        Root<UserData> root = query.from(UserData.class);

        Predicate predicates = getPredicatesForFilteringUserData(filterCriteria, cb, root);
        query.where(predicates);

        TypedQuery<UserData> typedQuery = entityManager.createQuery(query);

        return typedQuery.getResultList();
    }

    private static Predicate getPredicatesForFilteringUserData(
            FilterCriteria filterCriteria, CriteriaBuilder cb, Root<UserData> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (filterCriteria.email() != null) {
            predicates.add(cb.like(root.get("email"), "%" + filterCriteria.email() + "%"));
        }
        if (filterCriteria.firstName() != null) {
            predicates.add(cb.like(root.get("firstName"), "%" + filterCriteria.firstName() + "%"));
        }
        if (filterCriteria.lastName() != null) {
            predicates.add(cb.like(root.get("lastName"), "%" + filterCriteria.lastName() + "%"));
        }
        if (filterCriteria.active() != null) {
            predicates.add(cb.equal(root.get("active"), filterCriteria.active()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }


    @Override
    public Optional<UserData> findById(String id) {
        return springDataRepository.findById(id);
    }

    @Override
    public void save(UserData userData) {
        springDataRepository.save(userData);
    }

    @Override
    public void delete(String id) {
        springDataRepository.deleteById(id);
    }
}
