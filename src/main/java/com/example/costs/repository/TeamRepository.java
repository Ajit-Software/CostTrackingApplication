package com.example.costs.repository;

import com.example.costs.model.Team;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TeamRepository {

    private final JdbcTemplate jdbcTemplate;

    public TeamRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Team create(UUID tenantId, String name) {
        UUID id = UUID.randomUUID();
        jdbcTemplate.update(
                "INSERT INTO teams (id, tenant_id, name) VALUES (?, ?, ?)",
                id.toString(), tenantId.toString(), name
        );
        return new Team(id, tenantId, name);
    }

    public Optional<Team> findByIdAndTenant(UUID teamId, UUID tenantId) {
        List<Team> teams = jdbcTemplate.query(
                """
                SELECT id, tenant_id, name
                FROM teams
                WHERE id = ? AND tenant_id = ?
                """,
                (rs, i) -> new Team(
                        UUID.fromString(rs.getString("id")),
                        UUID.fromString(rs.getString("tenant_id")),
                        rs.getString("name")
                ),
                teamId.toString(), tenantId.toString()
        );
        return teams.stream().findFirst();
    }
}
