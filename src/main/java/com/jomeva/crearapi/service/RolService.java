package com.jomeva.crearapi.service;

import com.jomeva.crearapi.model.Rol;
import com.jomeva.crearapi.model.Users;
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
public class RolService {

  @Autowired
  private RolRepository rolRepository;

  public ResponseEntity<String> crearRol(Map<String, String> requestMap) {
    Rol rol = getRolFromMap(requestMap);
    rol.setType(requestMap.get("type").toUpperCase());
    rolRepository.save(rol);
    return CherryUtils.getResponseEntity("Rol registrado con éxito", HttpStatus.CREATED);

  }

  private Rol getRolFromMap(Map<String, String> requestMap) {
    Rol rol = new Rol();
    rol.setType(requestMap.get("type"));
    rol.setDescription(requestMap.get("description"));
    rol.setDisabled(Boolean.parseBoolean(requestMap.get("disabled")));
    return rol;
  }

  public ResponseEntity<String> deleteRolByType(String type) {
    Rol rol = rolRepository.findByType(type);

    if (rol != null) {

      rol.setDisabled(true);
      rolRepository.save(rol);
      return CherryUtils.getResponseEntity("Rol eliminado con éxito", HttpStatus.OK);
    } else {
      return CherryUtils.getResponseEntity("Error al eliminar el Rol: ", HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  public ResponseEntity<String> updateRol(Rol updatedRol) {
    // Verificar si el rol con el ID proporcionado existe en la base de datos
    Optional<Rol> existingRol = rolRepository.findById(updatedRol.getId());

    if (existingRol.isPresent()) {
      Rol rol = existingRol.get();
      // Actualizar los campos necesarios del rol existente con los valores proporcionados
      if (updatedRol.getType() != null) {
        rol.setType(updatedRol.getType());
      }
      if (updatedRol.getDescription() != null) {
        rol.setDescription(updatedRol.getDescription());
      }
      if (updatedRol.getDisabled() != null) {
        rol.setDisabled(updatedRol.getDisabled());
      }

      rolRepository.save(rol);
      return CherryUtils.getResponseEntity("ctulizado el Rol: ", HttpStatus.CREATED);
    } else {
      return CherryUtils.getResponseEntity("Error al actualizar el Rol: ", HttpStatus.INTERNAL_SERVER_ERROR);
    }
   
  }

  public List<Rol> getAllRol() {
    log.info("dadda {}", "ggg");
    try {

      return (List<Rol>) rolRepository.findByDisabled(Boolean.FALSE);
      //return null;
    } catch (DataAccessException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }
}
