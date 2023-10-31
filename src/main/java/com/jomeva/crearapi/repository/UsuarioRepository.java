
package com.jomeva.crearapi.repository;
import com.jomeva.crearapi.model.Curriculum;
import com.jomeva.crearapi.model.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<Users, Long>{
  Users findByEmail(String email);
    List<Users> findByDisabled(Boolean disabled);
}
