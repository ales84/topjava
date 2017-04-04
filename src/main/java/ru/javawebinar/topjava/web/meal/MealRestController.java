package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAllWithExceeded() {
        LOG.info("getAllWithExceeded");
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getFilteredWithExceeded(String startDate, String endDate, String startTime, String endTime) {
        LOG.info("getFilteredWithExceeded");
        return MealsUtil.getFilteredWithExceeded(service.getAll(AuthorizedUser.id()),
                startDate.isEmpty() ? LocalDate.MIN : LocalDate.parse(startDate),
                endDate.isEmpty() ? LocalDate.MAX : LocalDate.parse(endDate),
                startTime.isEmpty() ? LocalTime.MIN : LocalTime.parse(startTime),
                endTime.isEmpty() ? LocalTime.MAX : LocalTime.parse(endTime),
                AuthorizedUser.getCaloriesPerDay());
    }

    public List<Meal> getAll() {
        LOG.info("getAll");
        return service.getAll(AuthorizedUser.id());
    }

    public Meal get(int id) {
        LOG.info("get " + id);
        return service.get(AuthorizedUser.id(), id);
    }

    public Meal create(Meal meal) {
        LOG.info("create " + meal);
        checkNew(meal);
        return service.save(AuthorizedUser.id(), meal);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(AuthorizedUser.id(), id);
    }

    public void update(Meal meal, int id) {
        LOG.info("update " + meal);
        checkIdConsistent(meal, id);
        service.update(AuthorizedUser.id(), meal);
    }


}