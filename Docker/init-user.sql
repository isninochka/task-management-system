-- =====================
-- Users table
-- =====================
CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL UNIQUE,
                                     email VARCHAR(255) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================
-- Sample users
-- =====================
INSERT INTO users (username, email, password)
VALUES
    ('alice', 'alice@example.com', 'password1'),
    ('bob', 'bob@example.com', 'password2'),
    ('charlie', 'charlie@example.com', 'password3')
ON CONFLICT (username) DO NOTHING;