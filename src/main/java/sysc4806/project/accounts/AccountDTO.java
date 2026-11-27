package sysc4806.project.accounts;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// A DTO that just contains the information sent by the client.
// Exists in the http session
public record AccountDTO(int id, String username, String password, Collection<? extends GrantedAuthority> authorities) implements UserDetails {

    public static AccountDTO from(Account account) {
        return new AccountDTO(account.getId(), account.getUsername(), account.getPassword(),
                List.of(new SimpleGrantedAuthority("USER")));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }
}