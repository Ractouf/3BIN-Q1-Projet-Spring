package be.vinci.ipl.executions;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionsRepository extends CrudRepository<String, String> {
}
