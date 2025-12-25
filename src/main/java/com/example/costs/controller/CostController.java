package com.example.costs.controller;

import com.example.costs.config.TenantContext;
import com.example.costs.model.CostRecord;
import com.example.costs.repository.CostRecordRepository;
import com.example.costs.repository.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/costs")
public class CostController {

    private final TeamRepository teamRepository;
    private final CostRecordRepository costRecordRepository;

    public CostController(
            TeamRepository teamRepository,
            CostRecordRepository costRecordRepository
    ) {
        this.teamRepository = teamRepository;
        this.costRecordRepository = costRecordRepository;
    }

    @PostMapping
    public CostRecord createCost(
            @RequestParam UUID teamId,
            @RequestBody Map<String, String> body
    ) {
        UUID tenantId = TenantContext.get();

        teamRepository.findByIdAndTenant(teamId, tenantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return costRecordRepository.create(
                teamId,
                LocalDate.parse(body.get("date")),
                new BigDecimal(body.get("costAmount"))
        );
    }
}
