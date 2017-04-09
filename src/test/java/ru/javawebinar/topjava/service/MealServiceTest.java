package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(MEAL5_ID, USER_ID);
        MATCHER.assertEquals(MEAL5, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetOtherUserMeal() throws Exception {
        service.get(MEAL5_ID, ADMIN_ID);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(MEAL4_ID, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL3, MEAL2, MEAL1), service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(MEAL8_ID + 1, USER_ID);
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL3, MEAL2, MEAL1),
                service.getBetweenDates(
                        LocalDate.of(2015, 5, 30),
                        LocalDate.of(2015, 5, 30), USER_ID));
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        MATCHER.assertCollectionEquals(Collections.singletonList(MEAL7),
                service.getBetweenDateTimes(
                        LocalDateTime.of(2015, 6, 1, 13, 0),
                        LocalDateTime.of(2015, 6, 1, 17, 0), ADMIN_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL8, MEAL7), service.getAll(ADMIN_ID));
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), service.getAll(USER_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL5);
        updated.setCalories(10);
        updated.setDescription("Полдник");
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(MEAL5_ID, USER_ID));
    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.of(2015, 7, 29, 10, 0), "New", 10);
        Meal created = service.save(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(newMeal, MEAL8, MEAL7), service.getAll(ADMIN_ID));
    }

}