CREATE TABLE IF NOT EXISTS game.p_pokemons_types
(
    id SERIAL PRIMARY KEY,
    itsName VARCHAR(30) NOT NULL,
    description  VARCHAR(60) NOT NULL,
    initial_life_points INT NOT NULL
)