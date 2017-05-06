package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
@RequestMapping(value = "/meals")
public class MealController {

    @Autowired
    private MealService service;

    @RequestMapping(method = RequestMethod.GET)
    public String mealsGet(@RequestParam(name = "action", required = false) String action, HttpServletRequest request) {

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                service.delete(id, AuthorizedUser.id());
                return "redirect:meals";
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        service.get(getId(request), AuthorizedUser.id());
                request.setAttribute("meal", meal);
                return "meal";
            case "all":
            default:
                request.setAttribute("meals",
                        MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()));
                return "meals";
        }

    }

    @RequestMapping(method = RequestMethod.POST)
    public String mealsPost(@RequestParam(name = "action", required = false) String action, HttpServletRequest request) {
        if (action == null) {
            final Meal meal = new Meal(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));

            if (request.getParameter("id").isEmpty()) {
                checkNew(meal);
                service.save(meal, AuthorizedUser.id());
            } else {
                checkIdConsistent(meal, getId(request));
                service.update(meal, AuthorizedUser.id());
            }
            return "redirect:meals";
        } else if ("filter".equals(action)) {
            LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
            LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
            LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
            LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));
            request.setAttribute("meals",
                    MealsUtil.getFilteredWithExceeded(
                            service.getBetweenDates(
                                    startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                                    endDate != null ? endDate : DateTimeUtil.MAX_DATE, AuthorizedUser.id()),
                            startTime != null ? startTime : LocalTime.MIN,
                            endTime != null ? endTime : LocalTime.MAX,
                            AuthorizedUser.getCaloriesPerDay()));
        }
        return "meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
