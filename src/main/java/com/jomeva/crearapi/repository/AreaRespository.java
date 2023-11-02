
package com.jomeva.crearapi.repository;

import com.jomeva.crearapi.model.Area;
import com.jomeva.crearapi.model.Cargo;
import com.jomeva.crearapi.model.Curriculum;
import com.jomeva.crearapi.model.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AreaRespository extends JpaRepository<Area, Long> {
   Area findByName(String name);
        List<Area> findByDisabled(Boolean disabled);
}
