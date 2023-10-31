/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jomeva.crearapi.controller;

import com.jomeva.crearapi.model.Curriculum;
import com.jomeva.crearapi.model.Users;
import com.jomeva.crearapi.security.UsuarioDetailService;
import com.jomeva.crearapi.service.UsuarioService;
import com.jomeva.crearapi.util.CherryUtils;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UsuarioController {
@Autowired
private UsuarioDetailService usuarioDetailService;
  @Autowired
  private UsuarioService usuarioService;

  @PostMapping("/registrar")
  public ResponseEntity<String> crearUsuario(@RequestBody Users usuario) {   
    try {
     return usuarioService.registrar(usuario);
    }catch(Exception e){
     e.printStackTrace();
    }
      return CherryUtils.getResponseEntity("No se pudo registrar", HttpStatus.BAD_REQUEST);
    }
  @GetMapping
  public List<Users> getAllUser() {
    return usuarioService.getAllUser();
  }
   @GetMapping("/{id}")
  public Users getCurriculumByIdUser(@PathVariable("id") String idUser) {
    return usuarioService.getUser(idUser);
  }
@DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
    return usuarioService.deleteUserById(id);
  }
   @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap) {
    log.info("Solicitud de inicio de sesión recibida. Datos: {}", requestMap);
    
    try {
      
        return usuarioService.login(requestMap);
    } catch (Exception e) {
        log.error("Error durante el inicio de sesión: {}", e.getMessage());
        e.printStackTrace();
        return CherryUtils.getResponseEntity("No se pudo loguear", HttpStatus.BAD_REQUEST);
    }
  }
  @GetMapping("/getUser/{email}")
    public ResponseEntity<Users> getUserByEmail(@PathVariable("email")String email) {       
        return usuarioService.getUserByEmail(email);
    }
  @PutMapping
  public ResponseEntity<String> upDateUserById(@RequestBody Users users ) { 
    return usuarioService.updateUser(users);
  }
}
