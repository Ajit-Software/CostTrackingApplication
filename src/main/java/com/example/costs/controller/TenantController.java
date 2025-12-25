package com.example.costs.controller;

import com.example.costs.model.Tenant;
import com.example.costs.repository.TenantRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tenants")
public class TenantController {

    private final TenantRepository tenantRepository;

    public TenantController(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @PostMapping
    public Tenant createTenant(@RequestBody Map<String, String> body) {
        return tenantRepository.create(body.get("name"));
    }
}
