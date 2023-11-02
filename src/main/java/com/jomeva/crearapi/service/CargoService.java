package com.jomeva.crearapi.service;

import com.jomeva.crearapi.model.Area;
import com.jomeva.crearapi.model.Cargo;
import com.jomeva.crearapi.model.Rol;
import com.jomeva.crearapi.model.Users;
import com.jomeva.crearapi.repository.AreaRespository;
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
  @Autowired
  private AreaRespository areaRespository;
  public ResponseEntity<String> crearCargo(Cargo cargo) {
    //Cargo cargo = getCargoFromMap(requestMap);
   log.info("cargo {} ",cargo);
    cargoRepository.save(cargo);
    return CherryUtils.getResponseEntity("Cargo registrado con éxito", HttpStatus.CREATED);

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

public ResponseEntity<String> updateCargo(Cargo updatedCargo) {
    // Verificar si el cargo con el ID proporcionado existe en la base de datos
    Optional<Cargo> existingCargo = cargoRepository.findById(updatedCargo.getId());

    if (existingCargo.isPresent()) {
        Cargo cargo = existingCargo.get();

        if (updatedCargo.getTitle() != null) {
            cargo.setTitle(updatedCargo.getTitle());
        }
        if (updatedCargo.getName() != null) {
            cargo.setName(updatedCargo.getName());
        }
        if (updatedCargo.getDescription() != null) {
            cargo.setDescription(updatedCargo.getDescription());
        }
        if (updatedCargo.getDisabled() != null) {
            cargo.setDisabled(updatedCargo.getDisabled());
        }
        if (updatedCargo.getStartDate() != null) {
            cargo.setStartDate(updatedCargo.getStartDate());
        }
        if (updatedCargo.getFinalDate() != null) {
            cargo.setFinalDate(updatedCargo.getFinalDate());
        }
        if (updatedCargo.getSalary() != null) {
            cargo.setSalary(updatedCargo.getSalary());
        }
        if (updatedCargo.getArea() != null && updatedCargo.getArea().getId() != null) {
            Area updatedArea = areaRespository.findById(updatedCargo.getArea().getId()).orElse(null);
            if (updatedArea != null) {
                cargo.setArea(updatedArea);
            }
        }

        cargoRepository.save(cargo);
        return CherryUtils.getResponseEntity("Actualizado el Cargo: ", HttpStatus.CREATED);
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
