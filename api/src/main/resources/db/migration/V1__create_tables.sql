CREATE TABLE patients (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE
);

CREATE TABLE treatment_plans (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL REFERENCES patients(id) ON DELETE CASCADE,
    name VARCHAR(255),
    description TEXT,
    start_date DATE,
    end_date DATE
);

CREATE TABLE treatment_steps (
    id BIGSERIAL PRIMARY KEY,
    plan_id BIGINT NOT NULL REFERENCES treatment_plans(id) ON DELETE CASCADE,
    title VARCHAR(255),
    instructions TEXT,
    order_index INTEGER,
    scheduled_date DATE
);

CREATE TABLE medication_intakes (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL REFERENCES patients(id) ON DELETE CASCADE,
    plan_id BIGINT REFERENCES treatment_plans(id) ON DELETE SET NULL,
    medication_name VARCHAR(255) NOT NULL,
    dosage VARCHAR(255),
    scheduled_time TIMESTAMP,
    taken_at TIMESTAMP,
    status VARCHAR(32) NOT NULL
);

CREATE TABLE vital_signs (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL REFERENCES patients(id) ON DELETE CASCADE,
    type VARCHAR(128) NOT NULL,
    value VARCHAR(255) NOT NULL,
    unit VARCHAR(64),
    recorded_at TIMESTAMP NOT NULL
);

CREATE TABLE alerts (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT REFERENCES patients(id) ON DELETE SET NULL,
    message TEXT NOT NULL,
    status VARCHAR(32) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    acknowledged_at TIMESTAMP,
    resolved_at TIMESTAMP
);

CREATE TABLE user_accounts (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255)
);

CREATE TABLE user_account_roles (
    user_id BIGINT NOT NULL REFERENCES user_accounts(id) ON DELETE CASCADE,
    role VARCHAR(64) NOT NULL
);

CREATE UNIQUE INDEX user_account_roles_unique ON user_account_roles(user_id, role);

INSERT INTO user_accounts (username, password, full_name)
VALUES
    ('admin', '$2a$10$Dow1PFeZVt8iDSHPsuacpeuXgfko.uqrtfve/ncqH1/SuKD7YLmcu', 'Administrator'),
    ('user', '$2a$10$Dow1PFeZVt8iDSHPsuacpeuXgfko.uqrtfve/ncqH1/SuKD7YLmcu', 'Standard User');

INSERT INTO user_account_roles (user_id, role)
SELECT id, 'ADMIN' FROM user_accounts WHERE username = 'admin';

INSERT INTO user_account_roles (user_id, role)
SELECT id, 'USER' FROM user_accounts WHERE username IN ('admin', 'user');
