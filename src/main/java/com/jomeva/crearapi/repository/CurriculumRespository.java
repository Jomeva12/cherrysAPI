
package com.jomeva.crearapi.repository;

import com.jomeva.crearapi.model.Curriculum;
import com.jomeva.crearapi.model.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CurriculumRespository extends JpaRepository<Curriculum, Long> {
  //@Query("SELECT c FROM Curriculum c WHERE c.users.id = :userId")
    Curriculum findByUsers(Users users);
    List<Curriculum> findByDisabled(Boolean disabled);
   
}
