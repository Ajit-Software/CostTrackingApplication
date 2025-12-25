package com.example.costs.model;

import java.util.UUID;

public record Team(UUID id, UUID tenantId, String name) {}
