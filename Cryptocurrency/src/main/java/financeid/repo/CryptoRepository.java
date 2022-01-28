package financeid.repo;

import financeid.model.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CryptoRepository extends JpaRepository<Crypto, Integer> {
}
