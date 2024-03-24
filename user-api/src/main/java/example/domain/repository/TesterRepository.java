package example.domain.repository;

import example.domain.entity.Tester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TesterRepository extends JpaRepository<Tester,Long> {
    Optional<Tester> findByEmail(String email);
}
