-- Initial schema migration for CareTrack

-- Authentication tables
CREATE TABLE IF NOT EXISTS users (
    id               BIGSERIAL PRIMARY KEY,
    username         VARCHAR(100) NOT NULL UNIQUE,
    email            VARCHAR(255) UNIQUE,
    password_hash    VARCHAR(255) NOT NULL,
    is_active        BOOLEAN NOT NULL DEFAULT TRUE,
    created_at       TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS roles (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id    BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id    BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    assigned_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id)
);

CREATE INDEX IF NOT EXISTS idx_user_roles_user_id ON user_roles(user_id);

-- Core patient tables
CREATE TABLE IF NOT EXISTS patients (
    id              BIGSERIAL PRIMARY KEY,
    first_name      VARCHAR(150) NOT NULL,
    last_name       VARCHAR(150) NOT NULL,
    date_of_birth   DATE,
    gender          VARCHAR(50),
    email           VARCHAR(255) UNIQUE,
    phone_number    VARCHAR(50),
    address         TEXT,
    status          TEXT NOT NULL DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'INACTIVE', 'DECEASED')),
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS contacts_emergency (
    id             BIGSERIAL PRIMARY KEY,
    patient_id     BIGINT NOT NULL REFERENCES patients(id) ON DELETE CASCADE,
    full_name      VARCHAR(255) NOT NULL,
    relationship   VARCHAR(100),
    phone_number   VARCHAR(50) NOT NULL,
    email          VARCHAR(255),
    is_primary     BOOLEAN NOT NULL DEFAULT FALSE,
    created_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_contacts_emergency_patient_id ON contacts_emergency(patient_id);

CREATE TABLE IF NOT EXISTS treatment_plans (
    id             BIGSERIAL PRIMARY KEY,
    patient_id     BIGINT NOT NULL REFERENCES patients(id) ON DELETE CASCADE,
    plan_name      VARCHAR(255) NOT NULL,
    description    TEXT,
    status         TEXT NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'ACTIVE', 'COMPLETED', 'CANCELLED')),
    start_date     DATE,
    end_date       DATE,
    created_by     BIGINT REFERENCES users(id),
    created_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_treatment_plans_patient_id ON treatment_plans(patient_id);

CREATE TABLE IF NOT EXISTS plan_steps (
    id             BIGSERIAL PRIMARY KEY,
    plan_id        BIGINT NOT NULL REFERENCES treatment_plans(id) ON DELETE CASCADE,
    title          VARCHAR(255) NOT NULL,
    description    TEXT,
    step_order     INTEGER NOT NULL,
    due_date       DATE,
    status         TEXT NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'IN_PROGRESS', 'COMPLETED', 'SKIPPED')),
    created_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (plan_id, step_order)
);

CREATE INDEX IF NOT EXISTS idx_plan_steps_plan_id ON plan_steps(plan_id);

CREATE TABLE IF NOT EXISTS medications (
    id               BIGSERIAL PRIMARY KEY,
    patient_id       BIGINT NOT NULL REFERENCES patients(id) ON DELETE CASCADE,
    treatment_plan_id BIGINT REFERENCES treatment_plans(id) ON DELETE SET NULL,
    medication_name  VARCHAR(255) NOT NULL,
    dosage           VARCHAR(100) NOT NULL,
    frequency        VARCHAR(100),
    route            VARCHAR(100),
    start_date       DATE,
    end_date         DATE,
    instructions     TEXT,
    is_active        BOOLEAN NOT NULL DEFAULT TRUE,
    created_at       TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_medications_patient_id ON medications(patient_id);

CREATE TABLE IF NOT EXISTS medication_intakes (
    id                BIGSERIAL PRIMARY KEY,
    medication_id     BIGINT NOT NULL REFERENCES medications(id) ON DELETE CASCADE,
    scheduled_time    TIMESTAMP WITH TIME ZONE,
    intake_time       TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    dosage_taken      VARCHAR(100),
    status            TEXT NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'TAKEN', 'MISSED', 'SKIPPED')),
    notes             TEXT,
    created_at        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_medication_intakes_medication_id ON medication_intakes(medication_id);

CREATE TABLE IF NOT EXISTS vitals (
    id                 BIGSERIAL PRIMARY KEY,
    patient_id         BIGINT NOT NULL REFERENCES patients(id) ON DELETE CASCADE,
    recorded_at        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    vital_type         TEXT NOT NULL CHECK (vital_type IN ('HEART_RATE', 'BLOOD_PRESSURE', 'TEMPERATURE', 'RESPIRATION', 'OXYGEN_SATURATION', 'WEIGHT', 'HEIGHT', 'BLOOD_SUGAR')),
    value_numeric      NUMERIC(10,2),
    value_text         VARCHAR(255),
    unit               VARCHAR(50),
    recorded_by        BIGINT REFERENCES users(id),
    notes              TEXT,
    created_at         TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_vitals_patient_id ON vitals(patient_id);

CREATE TABLE IF NOT EXISTS alerts (
    id              BIGSERIAL PRIMARY KEY,
    patient_id      BIGINT NOT NULL REFERENCES patients(id) ON DELETE CASCADE,
    alert_type      VARCHAR(150) NOT NULL,
    alert_status    TEXT NOT NULL DEFAULT 'OPEN' CHECK (alert_status IN ('OPEN', 'ACKNOWLEDGED', 'RESOLVED', 'DISMISSED')),
    severity        TEXT CHECK (severity IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL')),
    message         TEXT NOT NULL,
    triggered_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    resolved_at     TIMESTAMP WITH TIME ZONE,
    created_by      BIGINT REFERENCES users(id),
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_alerts_patient_id ON alerts(patient_id);
CREATE INDEX IF NOT EXISTS idx_alerts_alert_status ON alerts(alert_status);

