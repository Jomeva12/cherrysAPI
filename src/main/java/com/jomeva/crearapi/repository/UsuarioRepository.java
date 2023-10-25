
package com.jomeva.crearapi.repository;
import com.jomeva.crearapi.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<Users, Long>{
  Users findByEmail(@Param(("email")) String email);
}
