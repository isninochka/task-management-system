-- =====================
-- Create ENUM for task status
-- =====================
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'task_status') THEN
        CREATE TYPE task_status AS ENUM ('NEW', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED');
END IF;
END$$;

-- =====================
-- Tasks table
-- =====================
CREATE TABLE IF NOT EXISTS tasks (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    task_status task_status,
    username VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================
-- Task history table
-- =====================
CREATE TABLE IF NOT EXISTS task_history (
    id SERIAL PRIMARY KEY,
    task_id INT NOT NULL,
    username VARCHAR(255),
    previous_status task_status,
    new_status task_status,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================
-- Sample tasks
-- =====================
INSERT INTO tasks (title, description, task_status, username)
VALUES
    ('Refactor service', 'Rewrite monolithic service to microservices', 'IN_PROGRESS', 'alice'),
    ('Prepare report', 'Financial report for 2024', 'NEW', 'bob'),
    ('Run tests', 'Execute all unit tests before release', 'COMPLETED', 'alice'),
    ('Update documentation', 'API documentation update', 'CANCELLED', 'charlie');

-- =====================
-- Sample task history
-- =====================
INSERT INTO task_history (task_id, username, previous_status, new_status)
VALUES
    (1, 'alice', 'NEW', 'IN_PROGRESS'),
    (2, 'bob', NULL, 'NEW'),
    (3, 'alice', 'IN_PROGRESS', 'COMPLETED'),
    (4, 'charlie', 'IN_PROGRESS', 'CANCELLED');