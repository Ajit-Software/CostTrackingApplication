CREATE TABLE IF NOT EXISTS tenants (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS teams (
    id CHAR(36) PRIMARY KEY,
    tenant_id CHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    UNIQUE (tenant_id, name),
    CONSTRAINT fk_team_tenant
        FOREIGN KEY (tenant_id) REFERENCES tenants(id)
);

CREATE TABLE IF NOT EXISTS cost_records (
    id CHAR(36) PRIMARY KEY,
    team_id CHAR(36) NOT NULL,
    date DATE NOT NULL,
    cost_amount DECIMAL(12,2) NOT NULL,
    UNIQUE (team_id, date),
    CONSTRAINT fk_cost_team
        FOREIGN KEY (team_id) REFERENCES teams(id)
);
