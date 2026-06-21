package entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public record AuthUser(
        UUID id,
        String username,
        Set<Role> roles
) {
    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .toList();
    }
}