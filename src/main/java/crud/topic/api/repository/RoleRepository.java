package crud.topic.api.repository;

import crud.topic.api.model.auth.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleNames);
}
