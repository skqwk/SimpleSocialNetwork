-- USER

-- login,password,email,birthdate,role,full_name
-- ds_bokarev,$2a$10$f5CneVw4BH98f.YOcF/JmewkQBxvuNCLN4BuWJmZQP7HlzChFjEw6,bokarev@gmail.com,1998-06-25,user,Даниил Семенович Бокарев
-- samarynov_AV,$2a$10$f5CneVw4BH98f.YOcF/JmewkQBxvuNCLN4BuWJmZQP7HlzChFjEw6,samaryanov@gmail.com,2001-09-21,user,Александр Васильевич Самарянов
-- kush2001,$2a$10$f5CneVw4BH98f.YOcF/JmewkQBxvuNCLN4BuWJmZQP7HlzChFjEw6,kukushkin@yandex.ru,2002-11-18,user,Виктор Сергеевич Кукушкин
-- pzayzev,$2a$10$f5CneVw4BH98f.YOcF/JmewkQBxvuNCLN4BuWJmZQP7HlzChFjEw6,zayzev@yahooo.com,1998-10-12,user,Петр Максимович Зайцев
-- luda_kulikova,$2a$10$f5CneVw4BH98f.YOcF/JmewkQBxvuNCLN4BuWJmZQP7HlzChFjEw6,kulikova@mail.ru,1994-11-09,user,Людмила Александровна Куликова
-- nastya_medvedeva,$2a$10$f5CneVw4BH98f.YOcF/JmewkQBxvuNCLN4BuWJmZQP7HlzChFjEw6,medvedeva@gmail.com,2003-02-18,user,Анастасия Петровна Медведева
-- sasssha,$2a$10$f5CneVw4BH98f.YOcF/JmewkQBxvuNCLN4BuWJmZQP7HlzChFjEw6,surikova@gmail.com,1995-07-12,admin,Александра Васильевна Сурикова
-- '$2a$10$f5CneVw4BH98f.YOcF/JmewkQBxvuNCLN4BuWJmZQP7HlzChFjEw6' == '$2a$10$f5CneVw4BH98f.YOcF/JmewkQBxvuNCLN4BuWJmZQP7HlzChFjEw6'
INSERT INTO user(login, password, email, birthdate, role, full_name) VALUES (
'i_petrov','$2a$10$f5CneVw4BH98f.YOcF/JmewkQBxvuNCLN4BuWJmZQP7HlzChFjEw6','petron@ya.ru','2000-10-12','user','Ivan Ivanovich Petrov'
);

INSERT INTO user(login, password, email, birthdate, role, full_name) VALUES (
'ds_bokarev','$2a$10$f5CneVw4BH98f.YOcF/JmewkQBxvuNCLN4BuWJmZQP7HlzChFjEw6','bokarev@gmail.com','1998-06-25','user','Daniil Semyonovich Bokarev'
);

INSERT INTO user(login, password, email, birthdate, role, full_name) VALUES (
'samarynov_AV','$2a$10$f5CneVw4BH98f.YOcF/JmewkQBxvuNCLN4BuWJmZQP7HlzChFjEw6','samaryanov@gmail.com','2000-10-12','user','Samaryanov Aleksandr Vasilyevich'
);

INSERT INTO user(login, password, email, birthdate, role, full_name) VALUES (
'kush2001','$2a$10$f5CneVw4BH98f.YOcF/JmewkQBxvuNCLN4BuWJmZQP7HlzChFjEw6','kukushkin@yandex.ru','2002-11-18','user','Viktor Sergeevich Kukushkin'
);

INSERT INTO user(login, password, email, birthdate, role, full_name) VALUES (
'pzayzev','$2a$10$f5CneVw4BH98f.YOcF/JmewkQBxvuNCLN4BuWJmZQP7HlzChFjEw6','zayzev@yahooo.com','1998-10-12','user','Peter Maksimovich Zayzev'
);

INSERT INTO user(login, password, email, birthdate, role, full_name) VALUES (
'luda_kulikova','$2a$10$f5CneVw4BH98f.YOcF/JmewkQBxvuNCLN4BuWJmZQP7HlzChFjEw6','kulikova@mail.ru','1994-11-09','user','Ludmila Aleksandrovna Kulikova'
);


INSERT INTO user(login, password, email, birthdate, role, full_name) VALUES (
'nastya_medvedeva','$2a$10$f5CneVw4BH98f.YOcF/JmewkQBxvuNCLN4BuWJmZQP7HlzChFjEw6','medvedeva@gmail.com','2003-02-18','user','Anastasiya Petrovna Medvedeva'
);

INSERT INTO user(login, password, email, birthdate, role, full_name) VALUES (
'sasssha','$2a$10$f5CneVw4BH98f.YOcF/JmewkQBxvuNCLN4BuWJmZQP7HlzChFjEw6','surikova@gmail.com','1995-07-12','admin','Aleksandra Vasilieva Surkova'
);

-- FRIENDSHIP

-- user2,user1,category,timestamp
-- 1,2,Одноклассники,2021-07-12 15:23:12
-- 1,3,Одноклассники,2020-05-12 12:43:23
-- 1,4,Одноклассники,2018-07-11 00:37:46
-- 1,5,Одноклассники,2018-06-21 09:30:49
-- 5,3,Коллеги,2021-04-05 12:34:43
-- 5,2,Коллеги,2021-03-23 15:24:42
-- 5,4,Коллеги,2021-01-11 15:56:48
-- 2,6,Родственники,2021-07-12 05:23:42
-- 2,7,Родственники,2021-05-10 10:23:23
-- 1,8,Родственники,2020-12-10 12:20:12

INSERT INTO friendship(user2, user1, category, timestamp) VALUES (
1,2,'Classmates','2021-07-12 15:23:12'
);

INSERT INTO friendship(user2, user1, category, timestamp) VALUES (
1,3,'Classmates','2020-05-12 12:43:23'
);

INSERT INTO friendship(user2, user1, category, timestamp) VALUES (
1,4,'Classmates','2018-07-11 00:37:46'
);

INSERT INTO friendship(user2, user1, category, timestamp) VALUES (
1,5,'Classmates','2018-06-21 09:30:49'
);

INSERT INTO friendship(user2, user1, category, timestamp) VALUES (
5,2,'Work','2021-03-23 15:24:42'
);

INSERT INTO friendship(user2, user1, category, timestamp) VALUES (
5,3,'Work','2021-04-05 12:34:43'
);

INSERT INTO friendship(user2, user1, category, timestamp) VALUES (
5,4,'Work','2021-01-11 15:56:48'
);

INSERT INTO friendship(user2, user1, category, timestamp) VALUES (
2,6,'Relatives','2021-07-12 05:23:42'
);

INSERT INTO friendship(user2, user1, category, timestamp) VALUES (
6,1,'Relatives','2021-07-12 05:23:42'
);

INSERT INTO friendship(user2, user1, category, timestamp) VALUES (
2,7,'Relatives','2021-05-10 10:23:23'
);

INSERT INTO friendship(user2, user1, category, timestamp) VALUES (
1,8,'Relatives','2020-12-10 12:20:12'
);

-- POST

-- timestamp,content,post_author
-- 2021-05-10 10:24:13,Сегодня солнечно!,1
-- 2021-05-22 10:34:13,Купил помидоров,1
-- 2021-05-26 00:24:37,Начал писать диплом,1
-- 2021-04-24 12:34:42,Встретились с друзьями,3
-- 2021-04-23 15:24:48,Пообедал в кафе,3

INSERT INTO post(timestamp, content, post_author) VALUES (
'2021-05-10 10:24:13','Today is cloudy...',1
);

INSERT INTO post(timestamp, content, post_author) VALUES (
'2021-05-22 10:34:13','I bought some tomatos',1
);

INSERT INTO post(timestamp, content, post_author) VALUES (
'2021-05-26 00:24:37','Start work on my diploma project',1
);

INSERT INTO post(timestamp, content, post_author) VALUES (
'2021-04-24 12:34:42','Meet with friends ^^',3
);

INSERT INTO post(timestamp, content, post_author) VALUES (
'2021-04-23 15:24:48','Today I have dinner in BK',3
);

INSERT INTO post(timestamp, content, post_author) VALUES (
'2021-04-27 10:42:18','I have read the World and War',3
);

-- LIKES
-- user_id,post_id,timestamp
-- 1,1,2021-05-10 10:25:15
-- 2,1,2021-05-10 10:36:14
-- 3,1,2021-06-10 09:26:11
-- 4,1,2022-06-10 00:01:57

INSERT INTO likes(user_id, post_id, timestamp) VALUES (
1,1,'2021-05-10 10:25:15'
);

INSERT INTO likes(user_id, post_id, timestamp) VALUES (
2,1,'2021-05-10 10:36:14'
);

INSERT INTO likes(user_id, post_id, timestamp) VALUES (
3,1,'2021-06-10 09:26:11'
);

INSERT INTO likes(user_id, post_id, timestamp) VALUES (
4,1,'2022-06-10 00:01:57'
);

-- COMMENT
-- content,post_id,comment_author,timestamp
-- Да, погода отличная,1,3,2021-05-10 10:25:15
-- Будешь салат делать?),2,3,2021-05-22 10:36:23
-- Давно пора!,3,3,2021-05-26 01:30:30
-- Был рад тебя увидеть,4,1,2021-04-24 12:39:40
-- О это то самое?,5,1,2021-04-23 15:34:50

INSERT INTO comment(content, post_id, comment_author, timestamp) VALUES (
'Yeah, the weather is cool',1,3,'2021-05-10 10:25:15'
);

INSERT INTO comment(content, post_id, comment_author, timestamp) VALUES (
'Will do you cook the salad',2,3,'2021-05-22 10:36:23'
);


INSERT INTO comment(content, post_id, comment_author, timestamp) VALUES (
'Hurry up!',3,3,'2021-05-26 01:30:30'
);

INSERT INTO comment(content, post_id, comment_author, timestamp) VALUES (
'I was glad to see you',4,1,'2021-04-24 12:39:40'
);

INSERT INTO comment(content, post_id, comment_author, timestamp) VALUES (
'Oh! Is it what i thinking?',5,1,'2021-04-23 15:34:50'
);


-- MESSAGE
-- timestamp,content,recipient,sender,has_been_read
-- 2020-06-12 00:19:40,Не хочешь встретиться завтра?,1,2,TRUE
-- 2020-06-12 00:19:46,Да, давай, отличная идея,2,1,TRUE
-- 2020-06-12 00:20:17,Тогда у Аквариума?,1,2,TRUE
-- 2020-06-12 00:21:06,Может лучше у Русича? Там завтра конкурсы будут,2,1,TRUE
-- 2020-06-12 00:22:01,Договорились!,1,2,TRUE

INSERT INTO message(timestamp, content, recipient, sender, has_been_read) VALUES (
'2020-06-12 00:19:40','Do u want to meet tomorrow?',1,2,TRUE
);

INSERT INTO message(timestamp, content, recipient, sender, has_been_read) VALUES (
'2020-06-12 00:19:46','Yeah, lets go, that is great idea',2,1,TRUE
);

INSERT INTO message(timestamp, content, recipient, sender, has_been_read) VALUES (
'2020-06-12 00:20:17','So, lets come near Auquarime',1,2,TRUE
);

INSERT INTO message(timestamp, content, recipient, sender, has_been_read) VALUES (
'2020-06-12 00:21:06','I think that Rusich is better. There are competitions tomorrow',2,1,TRUE
);

INSERT INTO message(timestamp, content, recipient, sender, has_been_read) VALUES (
'2020-06-12 00:22:01','Well!',1,2,TRUE
);



-- fill community

INSERT INTO community(name, creation_date, topic, age_limit) VALUES (
    'Yandex', '2007-11-18', 'IT', null
);
INSERT INTO community(name, creation_date, topic, age_limit) VALUES (
    'Mail.ru','2006-10-23', 'IT', null
);
INSERT INTO community(name, creation_date, topic, age_limit) VALUES (
    'Arts, Music', '2013-09-21', 'Art', 16
);
INSERT INTO community(name, creation_date, topic, age_limit) VALUES (
    'Burger King', '2014-08-24', 'Food', null
);
INSERT INTO community(name, creation_date, topic, age_limit) VALUES (
    'Fun Stories', '2009-09-14', 'Humour', 16
);
INSERT INTO community(name, creation_date, topic, age_limit) VALUES (
    'Social Network', '2005-11-12', 'IT', null
);

INSERT INTO community(name, creation_date, topic, age_limit) VALUES (
    'Cinema', '2015-08-12', 'Art', null
);




-- fill membership


INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    1,1,'2019-09-14'
);
INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    1,2,'2015-10-15'
);
INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    2,4,'2020-11-11'
);
INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    3,4,'2021-10-12'
);
INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    3,5,'2015-06-04'
);
INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    4,6,'2013-07-19'
);
INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    4,1,'2015-08-09'
);
INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    7,1,'2016-11-25'
);
INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    7,2,'2018-12-12'
);


-- Общество соцсети
INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    1,6,'2005-11-12'
);

INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    2,6,'2005-11-12'
);

INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    3,6,'2005-11-12'
);

INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    4,6,'2005-11-12'
);

INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    5,6,'2005-11-12'
);

INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    6,6,'2005-11-12'
);

INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    7,6,'2005-11-12'
);

INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    8,6,'2005-11-12'
);


INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    6,7,'2005-11-12'
);

INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    7,7,'2005-11-12'
);

INSERT INTO membership(user_id, community_id, entry_date) VALUES(
    8,7,'2005-11-12'
);


