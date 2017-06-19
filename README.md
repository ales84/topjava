[![Codacy Badge](https://api.codacy.com/project/badge/Grade/6d2c8baeb0cc4630b1c5c05eb3e62349)](https://www.codacy.com/app/ales84/topjava?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ales84/topjava&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/ales84/topjava.svg?branch=master)](https://travis-ci.org/ales84/topjava)
[![Dependency Status](https://dependencyci.com/github/ales84/topjava/badge)](https://dependencyci.com/github/ales84/topjava)

Java Enterprise Online Project 
===============================
    Стек технологий: Spring Security, Spring MVC, Spring Data JPA, Spring Security Test, Hibernate ORM, Hibernate Validator, SLF4J, Json Jackson, JSP, JSTL, Apache Tomcat, WebJars, DataTables plugin, Ehcache, PostgreSQL, JUnit, Hamcrest, jQuery, jQuery notification, Bootstrap.

    Java Enterprise проект с регистрацией/авторизацией и интерфейсом на основе ролей (USER, ADMIN). Администратор может создавать/редактировать/удалять/пользователей, а пользователь - управлять своим профилем и данными (день, еда, калории) через UI (по AJAX) и по REST интерфейсу с базовой авторизацией. Возможна фильтрация данных по датам и времени, при этом цвет записи таблицы еды зависит от того, превышает ли сумма калорий за день норму (редактируемый параметр в профиле пользователя). Весь REST интерфейс покрывается JUnit тестами, используя Spring MVC Test и Spring Security Test.