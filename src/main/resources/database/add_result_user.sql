CREATE TABLE IF NOT EXISTS game.p_result_user(
     id SERIAL PRIMARY KEY,
     user_id INT NOT NULL,
     result_id INT NOT NULL,
     endResult VARCHAR(6) NOT NULL,
     FOREIGN KEY (user_id) REFERENCES game.p_users(id),
     FOREIGN KEY (result_id) REFERENCES game.p_results(id)
    );