package lt.kono.tmb.repositories;

import lt.kono.tmb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
