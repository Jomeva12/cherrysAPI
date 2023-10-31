package com.jomeva.crearapi.repository;
import com.jomeva.crearapi.model.Rol;
import com.jomeva.crearapi.model.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
   Rol findByType(String type);
   List<Rol> findByDisabled(Boolean disabled);
}
