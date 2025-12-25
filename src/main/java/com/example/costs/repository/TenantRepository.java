package com.example.costs.repository;

import com.example.costs.model.Tenant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TenantRepository {

    private final JdbcTemplate jdbcTemplate;

    public TenantRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Tenant create(String name) {
        UUID id = UUID.randomUUID();
        jdbcTemplate.update(
                "INSERT INTO tenants (id, name) VALUES (?, ?)",
                id.toString(), name
        );
        return new Tenant(id, name);
    }

    public boolean exists(UUID tenantId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM tenants WHERE id = ?",
                Integer.class,
                tenantId.toString()
        );
        return count != null && count > 0;
    }
}
