CREATE TABLE users (
    id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    firstname VARCHAR(100) NOT NULL,
    lastname  VARCHAR(100) NOT NULL,
    email     VARCHAR(255) NOT NULL UNIQUE,
    photo     VARCHAR(500)
);
