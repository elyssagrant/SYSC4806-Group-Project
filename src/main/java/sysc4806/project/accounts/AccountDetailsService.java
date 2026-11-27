package sysc4806.project.accounts;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService implements UserDetailsService {

    private final AccountRepository accounts;

    public AccountDetailsService(AccountRepository accounts) {
        this.accounts = accounts;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accounts.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return AccountDTO.from(account);
    }
}
