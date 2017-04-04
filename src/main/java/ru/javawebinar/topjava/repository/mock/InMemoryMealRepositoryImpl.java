package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> this.save(AuthorizedUser.id(), meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        LOG.info("save " + meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.putIfAbsent(userId, new ConcurrentHashMap<>());
        repository.get(userId).put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int userId, int id) {
        LOG.info("delete " + id);
        Map<Integer, Meal> userMeal = repository.get(userId);
        return (userMeal != null && userMeal.remove(id) != null);
    }

    @Override
    public Meal get(int userId, int id) {
        LOG.info("get " + id);
        Map<Integer, Meal> userMeal = repository.get(userId);
        return (userMeal == null) ? null : userMeal.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        LOG.info("getAll");
        Map<Integer, Meal> userMeal = repository.get(userId);
        return (userMeal == null) ? Collections.EMPTY_LIST : userMeal
                .values()
                .stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }
}

