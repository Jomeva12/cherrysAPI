package com.jomeva.crearapi.service;

import com.jomeva.crearapi.model.Cargo;
import com.jomeva.crearapi.model.Rol;
import com.jomeva.crearapi.model.Users;
import com.jomeva.crearapi.repository.CargoRepository;
import com.jomeva.crearapi.repository.RolRepository;
import com.jomeva.crearapi.repository.UsuarioRepository;
import com.jomeva.crearapi.security.UsuarioDetailService;
import com.jomeva.crearapi.security.jwt.JwtUtil;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CargoService {

  @Autowired
  private CargoRepository cargoRepository;

  public ResponseEntity<String> crearCargo(Map<String, String> requestMap) {
    Cargo cargo = getCargoFromMap(requestMap);
   
    cargoRepository.save(cargo);
    return CherryUtils.getResponseEntity("Cargo registrado con éxito", HttpStatus.CREATED);

  }

  private Cargo getCargoFromMap(Map<String, String> requestMap) {
    Cargo cargo = new Cargo();  
    cargo.setDescription(requestMap.get("description"));
    cargo.setDisabled(Boolean.parseBoolean(requestMap.get("disabled")));
    return cargo;
  }

  public ResponseEntity<String> deleteCargoByName(String name) {
    Cargo cargo = cargoRepository.findByName(name);

    if (cargo != null) {

      cargo.setDisabled(true);
      cargoRepository.save(cargo);
      return CherryUtils.getResponseEntity("Cargo eliminado con éxito", HttpStatus.OK);
    } else {
      return CherryUtils.getResponseEntity("Error al eliminar el Cargo: ", HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  public ResponseEntity<String> updateRol(Cargo updatedCargo) {
    // Verificar si el rol con el ID proporcionado existe en la base de datos
    Optional<Cargo> existingCargo = cargoRepository.findById(updatedCargo.getId());

    if (existingCargo.isPresent()) {
      Cargo cargo = existingCargo.get();
      // Actualizar los campos necesarios del rol existente con los valores proporcionados
      if (updatedCargo.getName() != null) {
        cargo.setName(updatedCargo.getName());
      }
      if (updatedCargo.getDescription() != null) {
        cargo.setDescription(updatedCargo.getDescription());
      }
      if (updatedCargo.getDisabled() != null) {
        cargo.setDisabled(updatedCargo.getDisabled());
      }

      cargoRepository.save(cargo);
      return CherryUtils.getResponseEntity("ctulizado el Cargo: ", HttpStatus.CREATED);
    } else {
      return CherryUtils.getResponseEntity("Error al actualizar el Cargo: ", HttpStatus.INTERNAL_SERVER_ERROR);
    }
   
  }

  public List<Cargo> getAllCargo() {
    log.info("dadda {}", "ggg");
    try {

      return (List<Cargo>) cargoRepository.findByDisabled(Boolean.FALSE);
      //return null;
    } catch (DataAccessException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }
}
