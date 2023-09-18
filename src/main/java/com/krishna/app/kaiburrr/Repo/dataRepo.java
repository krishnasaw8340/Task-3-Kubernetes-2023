package com.krishna.app.kaiburrr.Repo;
import com.krishna.app.kaiburrr.Model.dataNew;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
public interface dataRepo extends MongoRepository<dataNew, Long> {
    List<dataNew> findAllByNameContaining(String name);
}
