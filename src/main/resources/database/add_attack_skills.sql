CREATE TABLE IF NOT EXISTS game.p_attack_skills
(
    id SERIAL PRIMARY KEY,
    description VARCHAR(60) NOT NULL,
    pokemon_type_id INT NOT NULL,
    attack_points INT not null,
    FOREIGN KEY (pokemon_type_id) REFERENCES game.p_pokemons_types(id)
)