/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jomeva.crearapi.controller;


import com.jomeva.crearapi.model.Cargo;
import com.jomeva.crearapi.model.Rol;
import com.jomeva.crearapi.model.Users;
import com.jomeva.crearapi.service.CargoService;
import com.jomeva.crearapi.service.RolService;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
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


@RestController
@Slf4j
@RequestMapping("/cargo") 
public class CargoController {
  @Autowired
  private CargoService cargoService;
  
  @PostMapping
  public ResponseEntity<String>  createCargo(@RequestBody(required = true) Cargo cargo){   
    return cargoService.crearCargo(cargo);
  }
   @DeleteMapping("/{name}")
  public ResponseEntity<String> deleteCargoByName(@PathVariable("name") String name) {
     log.info("pathhh------ {}", name);
    return cargoService.deleteCargoByName(name);
  } 
    @GetMapping
  public List<Cargo> getAllCargo() {
    return cargoService.getAllCargo();
  }
  @PutMapping
  public ResponseEntity<String> upDateRol(@RequestBody Cargo cargo){
    return cargoService.updateCargo(cargo);
  }
}
