package recipes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.businesslayer.Account;
import recipes.services.AccountService;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<Account> accountOptional = accountService.findByEmail(email);

        if (accountOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Email %s not found", email));
        }

        return new MyUserPrincipal(accountOptional.get());
    }
}