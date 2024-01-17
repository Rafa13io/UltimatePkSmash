-- helper file to keep track of changes to database
-- has no effect on the application
-- execute those manually (for now)


CREATE TABLE IF NOT EXISTS p_user
(
    id SERIAL PRIMARY KEY,
    username VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    number_of_played_games INT NOT NULL,
    number_of_wins INT NOT NULL
);

CREATE TABLE IF NOT EXISTS p_smasher
(
    id SERIAL PRIMARY KEY,
    "name" VARCHAR(30) NOT NULL,
    description VARCHAR NOT NULL,
    health_points DECIMAL(10,2) NOT NULL,
    ECTS INT NOT NULL,
    photo_path VARCHAR
);

CREATE TABLE IF NOT EXISTS p_attack_skill
(
    id SERIAL PRIMARY KEY,
    "name" VARCHAR not null,
    description VARCHAR(60),
    "type" VARCHAR(60) NOT NULL,
    attack_points INT not null,
    smasher_id INT NOT NULL,
    FOREIGN KEY (smasher_id) REFERENCES p_smasher(id)
);

CREATE TABLE IF NOT EXISTS p_defence_skill
(
    id SERIAL PRIMARY KEY,
    "name" VARCHAR not null,
    description VARCHAR(60),
    "type" VARCHAR(60) NOT NULL,
    defence_points INT NOT NULL,
    smasher_id INT NOT NULL,
    FOREIGN KEY (smasher_id) REFERENCES p_smasher(id)
);


CREATE TABLE IF NOT EXISTS p_smasher_user
(
    user_id INT NOT NULL,
    smasher_id INT NOT NULL,
    PRIMARY KEY (user_id, smasher_id),
    FOREIGN KEY (user_id) REFERENCES p_user(id),
    FOREIGN KEY (smasher_id) REFERENCES p_smasher(id)
);


CREATE TABLE IF NOT EXISTS p_result_1vs1 (
    id SERIAL PRIMARY KEY,
    "date" DATE NOT NULL, -- dd.MM.yyyy
    winner_id INT NOT NULL,
    loser_id  INT NOT NULL,
    FOREIGN KEY (winner_id) REFERENCES p_user(id),
    FOREIGN KEY (loser_id) REFERENCES p_user(id)
);

CREATE TABLE IF NOT EXISTS p_result_2vs2 (
    id SERIAL PRIMARY KEY,
    "date" DATE NOT NULL, -- dd.MM.yyyy
    winner1_id INT NOT NULL,
    winner2_id INT NOT NULL,
    loser1_id  INT NOT NULL,
    loser2_id  INT NOT NULL,
    FOREIGN KEY (winner1_id) REFERENCES p_user(id),
    FOREIGN KEY (winner2_id) REFERENCES p_user(id),
    FOREIGN KEY (loser1_id) REFERENCES p_user(id),
    FOREIGN KEY (loser2_id) REFERENCES p_user(id)
);

CREATE TABLE IF NOT EXISTS p_result_3vs3 (
    id SERIAL PRIMARY KEY,
    "date" DATE NOT NULL, -- dd.MM.yyyy
    winner1_id INT NOT NULL,
    winner2_id INT NOT NULL,
    winner3_id INT NOT NULL,
    loser1_id  INT NOT NULL,
    loser2_id  INT NOT NULL,
    loser3_id  INT NOT NULL,
    FOREIGN KEY (winner1_id) REFERENCES p_user(id),
    FOREIGN KEY (winner2_id) REFERENCES p_user(id),
    FOREIGN KEY (winner3_id) REFERENCES p_user(id),
    FOREIGN KEY (loser1_id) REFERENCES p_user(id),
    FOREIGN KEY (loser2_id) REFERENCES p_user(id),
    FOREIGN KEY (loser3_id) REFERENCES p_user(id)
);
