package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoMemory implements MealDao {

    private List<Meal> meals;
    private static final AtomicInteger id = new AtomicInteger(0);

    public MealDaoMemory() {
        meals = new CopyOnWriteArrayList<>();
        meals.add(new Meal(id.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(id.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(id.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(id.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(id.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(id.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public void add(Meal meal) {
        meal.setId(id.getAndIncrement());
        meals.add(meal);
    }

    @Override
    public void delete(int id) {
        Meal meal = getById(id);
        if (meal != null) meals.remove(getById(id));
    }

    @Override
    public void update(Meal meal) {
        Meal updatedMeal = getById(meal.getId());
        updatedMeal.setDateTime(meal.getDateTime());
        updatedMeal.setDescription(meal.getDescription());
        updatedMeal.setCalories(meal.getCalories());
    }

    @Override
    public List<Meal> getAll() {
        return meals;
    }

    @Override
    public Meal getById(int id) {
        return meals.stream().filter(meal -> meal.getId() == id).findFirst().orElse(null);
    }
}
