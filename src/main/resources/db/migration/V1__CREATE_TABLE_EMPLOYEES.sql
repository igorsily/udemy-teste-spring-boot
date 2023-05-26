CREATE TABLE IF NOT EXISTS employees
(
--     id UUID NOT NULL default uuid_generate_v4(),
    id UUID NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(80) NOT NULL UNIQUE ,
    PRIMARY KEY (id)
);
