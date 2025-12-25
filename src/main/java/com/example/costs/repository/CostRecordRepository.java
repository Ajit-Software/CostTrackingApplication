package com.example.costs.repository;

import com.example.costs.model.CostRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Repository
public class CostRecordRepository {

    private final JdbcTemplate jdbcTemplate;

    public CostRecordRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public CostRecord create(UUID teamId, LocalDate date, BigDecimal costAmount) {
        UUID id = UUID.randomUUID();
        jdbcTemplate.update(
                """
                INSERT INTO cost_records (id, team_id, date, cost_amount)
                VALUES (?, ?, ?, ?)
                """,
                id.toString(), teamId.toString(), date, costAmount
        );
        return new CostRecord(id, teamId, date, costAmount);
    }
}
