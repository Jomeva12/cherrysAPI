package com.jomeva.crearapi.service;

import com.jomeva.crearapi.model.Area;
import com.jomeva.crearapi.model.Cargo;
import com.jomeva.crearapi.repository.AreaRespository;
import com.jomeva.crearapi.util.CherryUtils;
import java.util.Collections;
import java.util.List;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AreaService {

  @Autowired
  private AreaRespository areaRespository;

  public ResponseEntity<String> crearArea(Map<String, String> requestMap) {
    Area area = getAreaFromMap(requestMap);
   
    areaRespository.save(area);
    return CherryUtils.getResponseEntity("Area registrado con éxito", HttpStatus.CREATED);

  }

  private Area getAreaFromMap(Map<String, String> requestMap) {
    Area area = new Area();  
    area.setDescription(requestMap.get("description"));
       area.setName(requestMap.get("name"));
    area.setDisabled(Boolean.parseBoolean(requestMap.get("disabled")));
    return area;
  }

  public ResponseEntity<String> deleteAreaByName(String name) {
    Area area = areaRespository.findByName(name);

    if (area != null) {

      area.setDisabled(true);
      areaRespository.save(area);
      return CherryUtils.getResponseEntity("Area eliminado con éxito", HttpStatus.OK);
    } else {
      return CherryUtils.getResponseEntity("Error al eliminar el Area: ", HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  public ResponseEntity<String> updateArea(Area updatedArea) {
    // Verificar si el rol con el ID proporcionado existe en la base de datos
    Optional<Area> existingArea = areaRespository.findById(updatedArea.getId());

    if (existingArea.isPresent()) {
      Area area = existingArea.get();
     
      if (updatedArea.getName() != null) {
        area.setName(updatedArea.getName());
      }
      if (updatedArea.getDescription() != null) {
        area.setDescription(updatedArea.getDescription());
      }
      if (updatedArea.getDisabled() != null) {
        area.setDisabled(updatedArea.getDisabled());
      }

      areaRespository.save(area);
      return CherryUtils.getResponseEntity("ctulizado el Area: ", HttpStatus.CREATED);
    } else {
      return CherryUtils.getResponseEntity("Error al actualizar el Area: ", HttpStatus.INTERNAL_SERVER_ERROR);
    }
   
  }

  public List<Area> getAllArea() {
    log.info("dadda {}", "ggg");
    try {

      return (List<Area>) areaRespository.findByDisabled(Boolean.FALSE);
      //return null;
    } catch (DataAccessException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }
}
