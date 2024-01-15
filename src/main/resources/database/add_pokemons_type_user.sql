CREATE TABLE IF NOT EXISTS game.p_pokemons_type_user
(
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    pokemon_type_id INT NOT NULL,
    experience_points INT NOT null,
    FOREIGN KEY (user_id) REFERENCES game.p_users(id),
    FOREIGN KEY (pokemon_type_id) REFERENCES game.p_pokemons_types(id)
)