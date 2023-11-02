/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jomeva.crearapi.controller;


import com.jomeva.crearapi.model.Area;
import com.jomeva.crearapi.model.Rol;
import com.jomeva.crearapi.model.Users;
import com.jomeva.crearapi.service.AreaService;
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
@RequestMapping("/area") 
public class AreaController {
  @Autowired
  private AreaService areaService;
  
  @PostMapping
  public ResponseEntity<String>  createArea(@RequestBody(required = true) Map<String, String> requestMap){   
    return areaService.crearArea(requestMap);
  }
   @DeleteMapping("/{name}")
  public ResponseEntity<String> deleteAreabyName(@PathVariable("name") String name) {
    return areaService.deleteAreaByName(name);
  } 
    @GetMapping
  public List<Area> getAllArea() {
    return areaService.getAllArea();
  }
  @PutMapping
  public ResponseEntity<String> upDateArea(@RequestBody Area area){
    return areaService.updateArea(area);
  }
}
