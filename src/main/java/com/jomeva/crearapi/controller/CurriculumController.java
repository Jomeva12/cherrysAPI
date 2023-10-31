package com.jomeva.crearapi.controller;

import com.jomeva.crearapi.model.Curriculum;
import com.jomeva.crearapi.service.CurriculumService;
import com.jomeva.crearapi.util.CherryUtils;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cv")
@Slf4j
public class CurriculumController {

  @Autowired
  private CurriculumService curriculumService;

  @PostMapping
  public ResponseEntity<String> crearCurriculum(@RequestBody Curriculum curriculum) {
    return curriculumService.crearCurriculum(curriculum);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCurriculum(@PathVariable("id") Long id) {
    return curriculumService.deleteCurriculumById(id);
  }

  @GetMapping
  public List<Curriculum> getAllCurriculum() {
    return curriculumService.getAllCurriculum();
  }

  @GetMapping("/{id}")
  public Curriculum getCurriculumByIdUser(@PathVariable("id") String idUser) {
    return curriculumService.getCurriculumByIdUser(idUser);
  }

  @PutMapping
  public ResponseEntity<String> upDateCurriculumById(@RequestBody Curriculum curriculum) {
    log.info("curri {} ", curriculum);
    return curriculumService.updateCurriculum(curriculum);
  }
}
