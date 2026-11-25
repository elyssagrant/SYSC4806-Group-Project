package sysc4806.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sysc4806.project.accounts.Account;
import sysc4806.project.accounts.AccountRepository;

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


}
