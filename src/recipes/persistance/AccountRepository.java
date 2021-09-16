package recipes.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.businesslayer.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
}
