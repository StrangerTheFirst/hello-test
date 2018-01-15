# hello-test

[![Build Status](https://travis-ci.org/StrangerTheFirst/hello-test.svg?branch=master)](https://travis-ci.org/StrangerTheFirst/hello-test)

Развертывание и запуск приложения
=====================================================

**1. Установка и конфигурация сервера PostgreSQL**

    user: postgres
    password: 123456
    port: 5432
    
**2. Создание базы данных**

    psql -U postgres
    # (Password: 123456)
    
    CREATE DATABASE hello OWNER postgres;

**3. Запуск проекта**

    cd <projects_dir>
    git clone https://github.com/StrangerTheFirst/hello-test.git
    cd hello-test
    mvn clean package
    java -jar ./target/hello-test-0.0.1-SNAPSHOT.jar


API документация
=====================================================

**Инициализировать базу данных миллионом записей в таблице contacts**

    POST /admin/db/init

**Очистить базу данных**

    DELETE /admin/db/drop

**Получить список контактов, соответствующих регулярному выражению**

    GET /hello/contacts?nameFilter=<regex>&offset=<offset>[&limit=<page_size>]
