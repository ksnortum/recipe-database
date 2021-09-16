package recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.businesslayer.Account;
import recipes.persistance.AccountRepository;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository repository;

    public boolean existsByEmail(String email) {
        return repository.existsById(email);
    }

    public Optional<Account> findByEmail(String email) {
        return repository.findById(email);
    }

    public void save(Account account) {
        repository.save(account);
    }
}
