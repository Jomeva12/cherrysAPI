package com.jomeva.crearapi.repository;
import com.jomeva.crearapi.model.Cargo;
import com.jomeva.crearapi.model.Rol;
import com.jomeva.crearapi.model.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
     Cargo findByName(String name);
        List<Cargo> findByDisabled(Boolean disabled);
}
