package edu.java.repository.jpa;

import edu.java.dto.domain.jpa.JpaChatEntity;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public interface JpaChatRepository extends JpaRepository<JpaChatEntity, Long> {
    List<JpaChatEntity> findByLinksId(Long linkId);
}
