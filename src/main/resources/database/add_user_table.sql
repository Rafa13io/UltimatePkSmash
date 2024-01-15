CREATE TABLE IF NOT EXISTS game.p_users
(
id SERIAL PRIMARY KEY,
user_name VARCHAR(30) NOT NULL,
password  VARCHAR(30) NOT NULL,
number_of_played_games INT NOT NULL,
number_of_wins INT NOT NULL
)