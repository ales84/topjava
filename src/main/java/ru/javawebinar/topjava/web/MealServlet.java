package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private MealDao dao;

    public MealServlet() {
        super();
        dao = new MealDaoMemory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to meals");

        String forward;
        String action = req.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(req.getParameter("id"));
            dao.delete(mealId);
            forward = "/meals.jsp";
            List<MealWithExceed> mealsWithExceeded = MealsUtil.getFilteredWithExceeded(
                    dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
            req.setAttribute("meals", mealsWithExceeded);

        } else if (action.equalsIgnoreCase("edit")) {
            int mealId = Integer.parseInt(req.getParameter("id"));
            Meal meal = dao.getById(mealId);
            forward = "/meal.jsp";
            req.setAttribute("meal", meal);

        } else if (action.equalsIgnoreCase("list")) {
            forward = "/meals.jsp";
            List<MealWithExceed> mealsWithExceeded = MealsUtil.getFilteredWithExceeded(
                    dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
            req.setAttribute("meals", mealsWithExceeded);
        } else {
            forward = "/meal.jsp";
        }

        req.getRequestDispatcher(forward).forward(req, resp);
        //resp.sendRedirect("meals.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(
                    req.getParameter("dateTime").replace("T", " "), formatter);
        } catch (DateTimeParseException e) {
            dateTime = LocalDateTime.now();
        }
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) {
            dao.add(new Meal(0, dateTime, description, calories));
        } else {
            dao.update(new Meal(Integer.parseInt(id), dateTime, description, calories));
        }

        List<MealWithExceed> mealsWithExceeded = MealsUtil.getFilteredWithExceeded(
                dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("meals", mealsWithExceeded);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
