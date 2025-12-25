package com.example.costs.config;

import com.example.costs.repository.TenantRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class TenantFilter extends OncePerRequestFilter {

    private final TenantRepository tenantRepository;

    public TenantFilter(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        if (request.getRequestURI().equals("/tenants")) {
            filterChain.doFilter(request, response);
            return;
        }

        String tenantHeader = request.getHeader("X-Tenant-Id");
        if (tenantHeader == null) {
            response.sendError(400, "Missing X-Tenant-Id");
            return;
        }

        UUID tenantId;
        try {
            tenantId = UUID.fromString(tenantHeader);
        } catch (Exception e) {
            response.sendError(400, "Invalid X-Tenant-Id");
            return;
        }

        if (!tenantRepository.exists(tenantId)) {
            response.sendError(400, "Tenant not found");
            return;
        }

        TenantContext.set(tenantId);
        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
