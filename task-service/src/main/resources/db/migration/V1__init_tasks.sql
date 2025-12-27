-- =====================
-- Tasks table
-- =====================

CREATE TABLE IF NOT EXISTS tasks (
  id SERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  task_status VARCHAR(50),
  assignee_id BIGINT,
  created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
