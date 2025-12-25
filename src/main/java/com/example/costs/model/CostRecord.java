package com.example.costs.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CostRecord(
        UUID id,
        UUID teamId,
        LocalDate date,
        BigDecimal costAmount
) {}
