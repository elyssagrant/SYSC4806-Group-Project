package sysc4806.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sysc4806.project.accounts.Account;
import sysc4806.project.accounts.AccountDTO;
import sysc4806.project.accounts.AccountRepository;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/user")
public class AccountController {
    private final AccountRepository accountRepo;

    public AccountController(AccountRepository accounts) {
        this.accountRepo = accounts;
    }

    @PostMapping("/user/register")
    public ResponseEntity<Void> createUser(@RequestParam String username, @RequestParam String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        try {
            accountRepo.save(account);
        } catch (Exception e) {
            System.out.println("There was a problem saving the account: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    // Check if the user is logged in - used by react to render the logged in pages
    @GetMapping("/auth")
    public ResponseEntity<Boolean> userExists(@AuthenticationPrincipal AccountDTO user) {
        if (user == null) {
            return ResponseEntity.ok().body(false)   ;
        }
        return ResponseEntity.ok().body(true);
    }
}
