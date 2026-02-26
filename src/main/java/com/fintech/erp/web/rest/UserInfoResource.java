package com.fintech.erp.web.rest;

import com.fintech.erp.security.SecurityUtils;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller a bejelentkezett felhasználó információinak lekérdezéséhez.
 */
@RestController
@RequestMapping("/api")
public class UserInfoResource {

    private static final Logger LOG = LoggerFactory.getLogger(UserInfoResource.class);

    /**
     * {@code GET  /user-info} : A bejelentkezett felhasználó információi.
     *
     * @return UserInfo objektum a felhasználó adataival és szerepköreivel
     */
    @GetMapping("/user-info")
    public ResponseEntity<UserInfo> getUserInfo() {
        LOG.debug("REST request to get current user info");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.ok(new UserInfo(null, null, Collections.emptyList(), false));
        }

        String username = SecurityUtils.getCurrentUserLogin().orElse("anonymous");
        boolean isAdmin = SecurityUtils.hasCurrentUserThisAuthority("ROLE_ADMIN");

        // Szerepkörök kinyerése
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).sorted().toList();

        // Email kinyerése JWT tokenből (ha van)
        String email = null;
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Map<String, Object> claims = jwtAuth.getToken().getClaims();
            email = (String) claims.get("email");

            LOG.debug("JWT Claims: preferred_username={}, email={}, roles={}", claims.get("preferred_username"), email, roles);
        }

        UserInfo userInfo = new UserInfo(username, email, roles, isAdmin);

        LOG.info("✅ Felhasználó info: username={}, email={}, admin={}, roles={}", username, email, isAdmin, roles);

        return ResponseEntity.ok(userInfo);
    }

    /**
     * {@code GET  /user-roles} : Csak a szerepkörök lekérdezése.
     *
     * @return A felhasználó szerepköreinek listája
     */
    @GetMapping("/user-roles")
    public ResponseEntity<List<String>> getUserRoles() {
        LOG.debug("REST request to get current user roles");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).sorted().toList();

        LOG.info("👤 Felhasználó szerepkörök: {}", roles);

        return ResponseEntity.ok(roles);
    }

    /**
     * Felhasználó információ DTO
     */
    public record UserInfo(String username, String email, List<String> roles, boolean isAdmin) {}
}
