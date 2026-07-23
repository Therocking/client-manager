CREATE TABLE addresses (
    id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    street  VARCHAR(255) NOT NULL,
    city    VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    zip     VARCHAR(20)  NOT NULL,
    user_id UUID         NOT NULL REFERENCES users(id) ON DELETE CASCADE
);
