package repository;

import model.Anunt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnuntRepository extends JpaRepository<Anunt,Long> {
}
