package com.example.costs.controller;

import com.example.costs.config.TenantContext;
import com.example.costs.model.Team;
import com.example.costs.repository.TeamRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @PostMapping
    public Team createTeam(@RequestBody Map<String, String> body) {
        UUID tenantId = TenantContext.get();
        return teamRepository.create(tenantId, body.get("name"));
    }
}
