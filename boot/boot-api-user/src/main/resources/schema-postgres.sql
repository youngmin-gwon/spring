DROP TABLE IF EXISTS scientist;

CREATE TABLE IF NOT EXISTS scientist 
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(255),
  create_date TIMESTAMP DEFAULT NOW(),
  modify_date TIMESTAMP DEFAULT NOW(),
  create_id SMALLINT,
  modify_id SMALLINT
);
