package com.jomeva.crearapi.service;

import com.jomeva.crearapi.model.Curriculum;
import com.jomeva.crearapi.model.Rol;
import com.jomeva.crearapi.model.Users;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Slf4j
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UsuarioDetailService usuarioDetailService;

  @Autowired
  private JwtUtil jwtUtil;

  public ResponseEntity<String> login(Map<String, String> requestMap) {
    log.info("Intento de inicio de sesión. Datos: {}", requestMap);
//    try {

    String email = requestMap.get("email");
    String pass = requestMap.get("password");
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, pass));

    log.info("ffffffffff {} ", authentication);
    if (authentication.isAuthenticated()) {

      if (!usuarioDetailService.getUsuarioDetail().getDisabled()) {

        String token = jwtUtil.generarToken(usuarioDetailService.getUsuarioDetail().getEmail(), usuarioDetailService.getUsuarioDetail().getRol().toString());

        // Devuelve una respuesta JSON con el token
        return ResponseEntity.ok("{\"token\": \"" + token + "\"}");

      }
    }
//    } catch (Exception e) {
//      log.error("Error durante el inicio de sesión: {}", e.getMessage());
//      e.printStackTrace();
//      return CancionesUtil.getResponseEntity("No se pudo loguear", HttpStatus.BAD_REQUEST);
//    }

    return new ResponseEntity<String>("{credenciales incorrectas}", HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<String> registrar(Users usuario) {

    Users users = usuarioRepository.findByEmail(usuario.getEmail());
    if (Objects.isNull(users)) {
      //agregarlo si no existe
      //usuario = getUsuarioFromMap(requestMap); // Obtén el usuario a partir del mapa
      users = usuario;
      // Encripta la contraseña antes de guardarla
      String rawPassword = usuario.getPassword();
      String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
      users.setPassword(hashedPassword);

      usuarioRepository.save(users); // Guarda el usuario en la base de datos

      return CherryUtils.getResponseEntity("Usuario registrado con existo", HttpStatus.CREATED);
    } else {
      return CherryUtils.getResponseEntity("El usuario con este email ya existe", HttpStatus.BAD_REQUEST);
    }

    // } catch (Exception e) {
    //System.out.println("Error al crear usuario " + e.toString());
    // }
    //return CancionesUtil.getResponseEntity("Error en los datos", HttpStatus.INTERNAL_SERVER_ERROR);
  }
public ResponseEntity<Users> getUserByEmail(String email) {

    Users users = usuarioRepository.findByEmail(email);
    if (!Objects.isNull(users)) {
       // Guarda el usuario en la base de datos

            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.notFound().build();
        }
     
   
    
  }
  public ResponseEntity<String> deleteUserById(Long id) {
    Optional<Users> opt = usuarioRepository.findById(id);

    if (!opt.isEmpty()) {
      Users existingUser = opt.get();
      existingUser.setDisabled(true);
      usuarioRepository.save(existingUser);
      return CherryUtils.getResponseEntity("Usuario eliminado con éxito", HttpStatus.OK);
    } else {
      return CherryUtils.getResponseEntity("Error al eliminar el Usuario: ", HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  public List<Users> getAllUser() {
    try {
      return (List<Users>) usuarioRepository.findByDisabled(Boolean.FALSE);
      //return null;
    } catch (DataAccessException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  private Users getUsuarioFromMap(Map<String, String> requestMap) {
    Users usuario = new Users();

    usuario.setUserName(requestMap.get("userName"));
    usuario.setEmail(requestMap.get("email"));
    usuario.setNombres(requestMap.get("nombres"));
    usuario.setEdit(requestMap.get("edit"));
    usuario.setUserEdit(requestMap.get("userEdit"));

    String disabledValue = requestMap.get("disabled");
    if (disabledValue != null) {
      usuario.setDisabled(Boolean.parseBoolean(disabledValue));
    }

    String rolValue = requestMap.get("rol");
    if (rolValue != null) {
      Rol rol = new Rol(rolValue);
      usuario.setRol(rol);
    }
    return usuario;
  }

  public Users getUsuario(Long id) {
    Optional<Users> usuario = usuarioRepository.findById(id);
    return usuario.get();
  }

  public ResponseEntity<String> updateUser(Users updatedUser) {
    Users existingUser = usuarioRepository.findById(updatedUser.getIduser()).orElse(null);

    if (existingUser != null) {
      // Actualiza los campos que se proporcionan en la solicitud
      if (updatedUser.getUserName() != null) {
        existingUser.setUserName(updatedUser.getUserName());
      }
      if (updatedUser.getEmail() != null) {
        existingUser.setEmail(updatedUser.getEmail());
      }
      if (updatedUser.getPassword() != null) {
        existingUser.setPassword(updatedUser.getPassword());
      }
      if (updatedUser.getDisabled() != null) {
        existingUser.setDisabled(updatedUser.getDisabled());
      }
      if (updatedUser.getNombres() != null) {
        existingUser.setNombres(updatedUser.getNombres());
      }
      if (updatedUser.getRol() != null) {
        existingUser.setRol(updatedUser.getRol());
      }
      if (updatedUser.getCreated() != null) {
        existingUser.setCreated(updatedUser.getCreated());
      }
      if (updatedUser.getEdit() != null) {
        existingUser.setEdit(updatedUser.getEdit());
      }
      if (updatedUser.getUserEdit() != null) {
        existingUser.setUserEdit(updatedUser.getUserEdit());
      }
      if (updatedUser.getCurriculum() != null) {
        existingUser.setCurriculum(updatedUser.getCurriculum());
      }
      usuarioRepository.save(existingUser);
      // Guarda los cambios en la base de datos
      return CherryUtils.getResponseEntity("Actualizado", HttpStatus.ACCEPTED);
    }

    // Devuelve null si no se encuentra el usuario
    return CherryUtils.getResponseEntity("Error al actualizar el currículum: ", HttpStatus.INTERNAL_SERVER_ERROR);
  }
public Users getUser(String id) {
    Optional<Users> userOptional = usuarioRepository.findById(Long.valueOf(id));
    
    if (userOptional.isPresent()) {
        return userOptional.get();
    } else {
        return null; // O considera lanzar una excepción si prefieres.
    }
}
}
