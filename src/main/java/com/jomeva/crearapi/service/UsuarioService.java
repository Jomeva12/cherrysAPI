package com.jomeva.crearapi.service;

import com.jomeva.crearapi.model.Rol;
import com.jomeva.crearapi.model.Users;
import com.jomeva.crearapi.repository.UsuarioRepository;
import com.jomeva.crearapi.security.UsuarioDetailService;
import com.jomeva.crearapi.security.jwt.JwtUtil;
import com.jomeva.crearapi.util.CancionesUtil;

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

      if (usuarioDetailService.getUsuarioDetail().getDisabled()) {

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

      return CancionesUtil.getResponseEntity("Usuario registrado con existo", HttpStatus.CREATED);
    } else {
      return CancionesUtil.getResponseEntity("El usuario con este email ya existe", HttpStatus.BAD_REQUEST);
    }

    // } catch (Exception e) {
    //System.out.println("Error al crear usuario " + e.toString());
    // }
    //return CancionesUtil.getResponseEntity("Error en los datos", HttpStatus.INTERNAL_SERVER_ERROR);
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

}
