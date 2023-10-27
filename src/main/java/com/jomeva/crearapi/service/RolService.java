package com.jomeva.crearapi.service;

import com.jomeva.crearapi.model.Rol;
import com.jomeva.crearapi.model.Users;
import com.jomeva.crearapi.repository.RolRepository;
import com.jomeva.crearapi.repository.UsuarioRepository;
import com.jomeva.crearapi.security.UsuarioDetailService;
import com.jomeva.crearapi.security.jwt.JwtUtil;
import com.jomeva.crearapi.util.CherryUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
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
    rolRepository.save(rol);
    return CherryUtils.getResponseEntity("Rol registrado con éxito", HttpStatus.CREATED);
    
  }

  private Rol getRolFromMap(Map<String, String> requestMap) {
   Rol rol = new Rol();
    rol.setType(requestMap.get("type"));
    rol.setDescription(requestMap.get("description"));
   
    return rol;
  }


}
