# Логирование и визуализация данных OPC UA

Этот проект представляет собой комплексное решение для мониторинга и логирования тегов OPC UA сервера в базу данных PostgreSQL с визуализацией данных в режиме реального времени с помощью Grafana. Приложение построено с использованием Spring Boot, Grafana и Docker, что обеспечивает простую интеграцию, хранение данных и их визуальный анализ.

## Основные возможности

- **OPC UA клиент**: Подключается к OPC UA серверу и подписывается на указанные теги для сбора данных.
- **Логирование данных**: Сохраняет значения тегов в базе данных PostgreSQL.
- **Визуализация в реальном времени**: Использует Grafana для визуализации собранных данных в режиме реального времени, предоставляет дашборды с графиками и текущими значениями.
- **Обработка ошибок**: Включает обработку ошибок для управления переподключением к OPC UA серверу и исключениями при обработке данных.
- **Docker-контейнеры**: Использует Docker Compose для развертывания PostgreSQL и Grafana, что облегчает развертывание проекта.

## Используемые технологии

- Java, Spring Boot
- PostgreSQL
- OPC UA (Eclipse Milo)
- Grafana
- Docker, Docker Compose

## Предварительные требования

- Установленные Docker и Docker Compose
- Java Development Kit (JDK) 17 или выше
- Maven

## Начало работы

### 1. Клонируйте репозиторий

```bash
git clone https://github.com/Bunchiek/scada-service.git
```

### 2. Настройка переменных окружения

Настройте Application.yml файл в корневой директории для указания переменных окружения для подключения к базе данных:
```bash
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=opcua_db
```
Настройте tags.yml для динамического добавления\удаления тегов из OPC сервера:
```bash
opcua:
  tags:
    - "Simulation Examples.Functions.Random1"
    - "Simulation Examples.Functions.Random2"
    - "Simulation Examples.Functions.Random3"
    ...
```

### 3. Сборка приложения

Соберите приложение Spring Boot с помощью Maven:
```bash
mvn clean install
```
### 4. Настройка Docker-контейнеров

Этот проект включает docker-compose.yml файл для запуска контейнеров PostgreSQL, Grafana и Prometheus. Выполните команду для запуска всех сервисов:
```bash
docker-compose up -d
```
- Grafana: Доступна по адресу http://localhost:3000
- PostgreSQL: Работает на порту 5432
### 5. Настройка Grafana

1. Откройте Grafana в браузере по адресу: [http://localhost:3000](http://localhost:3000).
2. Войдите, используя стандартные учетные данные:
   - **Логин**: `admin`
   - **Пароль**: `admin`
3. Добавьте новый источник данных:
   - **Тип**: PostgreSQL
   - **Хост**: `postgres:5432`
   - **База данных**: `opcua_db`
   - **Пользователь**: `postgres`
   - **Пароль**: `postgres`
4. Создайте дашборды и панели для визуализации данных, хранящихся в таблице `opcua_data`.

## Пример дашборда Grafana
### Воспользуемся симуляцией работы OPC UA сервера:
![image](https://github.com/user-attachments/assets/148a1f9c-eaa8-4d57-b758-2dc7c2a577d3)
### Тренд показаний за выбранный период
Благодаря записанным в БД данным мы можем отслеживать поведение того или иного тега
![image](https://github.com/user-attachments/assets/6c9a065e-b850-49d7-ac13-97cc344afcc0)
### Дашборд с текущими показаниями, обновляется каждую секунду 
Здесь дополнительно настроен аларминг, если показания (допустим температура) привысит 80 градусов, то будет оповещение
![image](https://github.com/user-attachments/assets/a96c54e9-2404-42ca-8338-b53f9b33463f)








