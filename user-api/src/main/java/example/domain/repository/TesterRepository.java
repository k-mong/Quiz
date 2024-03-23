package example.domain.repository;

import example.domain.entity.Tester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TesterRepository extends JpaRepository<Tester,Long> {
}
