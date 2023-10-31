/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jomeva.crearapi.controller;


import com.jomeva.crearapi.model.Rol;
import com.jomeva.crearapi.model.Users;
import com.jomeva.crearapi.service.RolService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para gestionar las operaciones relacionadas con las canciones.
 */
@RestController
@RequestMapping("/rol") 
public class RolController {
  @Autowired
  private RolService rolService;
  
  @PostMapping
  public ResponseEntity<String>  createRol(@RequestBody(required = true) Map<String, String> requestMap){   
    return rolService.crearRol(requestMap);
  }
   @DeleteMapping("/{type}")
  public ResponseEntity<String> deleteCurriculum(@PathVariable("type") String type) {
    return rolService.deleteRolByType(type.toUpperCase());
  } 
    @GetMapping
  public List<Rol> getAllUser() {
    return rolService.getAllRol();
  }
  @PutMapping
  public ResponseEntity<String> upDateRol(@RequestBody Rol rol){
    return rolService.updateRol(rol);
  }
}
