CREATE DATABASE db;

use db;

CREATE TABLE IF NOT EXISTS user
(
    user_id  INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    login  VARCHAR(20) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    email  VARCHAR(20) NOT NULL,
    birthdate  DATE NOT NULL,
    role  VARCHAR(20) NOT NULL,
    full_name  VARCHAR(80) NOT NULL
    );

CREATE TABLE IF NOT EXISTS friendship
(
    user2  INTEGER NOT NULL,
    user1  INTEGER NOT NULL,
    category  VARCHAR(20) NULL,
    timestamp  DATETIME NOT NULL,
    PRIMARY KEY (user2, user1),
    FOREIGN KEY (user2) REFERENCES user(user_id),
    FOREIGN KEY (user1) REFERENCES user(user_id)
    );

ALTER TABLE friendship
    ADD FOREIGN KEY (user2) REFERENCES user(user_id);

CREATE TABLE IF NOT EXISTS post
(
    post_id  INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    timestamp  DATETIME NOT NULL,
    content  VARCHAR(200) NULL,
    post_author  INTEGER NOT NULL,
    FOREIGN KEY (post_author) REFERENCES user(user_id)
    );




CREATE TABLE IF NOT EXISTS comment
(
    comment_id  INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    content  VARCHAR(200) NULL,
    post_id  INTEGER NOT NULL,
    comment_author  INTEGER NOT NULL,
    timestamp  DATETIME NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post(post_id),
    FOREIGN KEY (comment_author) REFERENCES user(user_id)
    );

CREATE TABLE IF NOT EXISTS community
(
    community_id  INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(20) NULL,
    creation_date  DATE NOT NULL,
    topic  VARCHAR(20) NOT NULL,
    age_limit  INTEGER NULL
    );



CREATE TABLE IF NOT EXISTS likes
(
    user_id  INTEGER NOT NULL,
    post_id  INTEGER NOT NULL,
    timestamp  DATETIME NOT NULL,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (post_id) REFERENCES post(post_id)
    );


CREATE TABLE IF NOT EXISTS membership
(
    user_id  INTEGER NOT NULL,
    community_id  INTEGER NOT NULL,
    entry_date  DATETIME NOT NULL,
    PRIMARY KEY (user_id, community_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (community_id) REFERENCES community(community_id)
    );

CREATE TABLE IF NOT EXISTS message
(
    message_id  INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    timestamp  DATETIME NOT NULL,
    content  TEXT NULL,
    sender  INTEGER NOT NULL,
    recipient  INTEGER NOT NULL,
    has_been_read  BOOLEAN DEFAULT false,
    FOREIGN KEY (sender) REFERENCES user(user_id),
    FOREIGN KEY (recipient) REFERENCES user(user_id)
    );

