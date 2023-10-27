package com.jomeva.crearapi.service;

import com.jomeva.crearapi.model.Curriculum;
import com.jomeva.crearapi.model.Rol;
import com.jomeva.crearapi.model.Users;
import com.jomeva.crearapi.repository.CurriculumRespository;
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
public class CurriculumService {
  @Autowired
private UsuarioRepository userRepo;
  @Autowired
  private CurriculumRespository curriculumRespository;

public ResponseEntity<String> crearCurriculum(Curriculum curriculum) {

        curriculumRespository.save(curriculum);
       return CherryUtils.getResponseEntity("Currículum registrado con éxito", HttpStatus.CREATED);

}
    public ResponseEntity<String> deleteCurriculumById(Long id) {
      curriculumRespository.deleteById(id);
      
        return CherryUtils.getResponseEntity("curriculum eliminado con existo", HttpStatus.CREATED);
    }

 public List<Curriculum> getAllCurriculum() {
          try {
            return curriculumRespository.findAll();
        } catch (DataAccessException e) {
           
            e.printStackTrace(); 
            return Collections.emptyList(); 
        }
    }
 public void deleteCurriculum(Long id) {
        curriculumRespository.deleteById(id);
    }
public Curriculum getCurriculumByIdUser(String id) {
  
  Optional<Users> opt=userRepo.findById(Long.valueOf(id));
  if(!opt.isEmpty()){
     return curriculumRespository.findByUsers(opt.get());
  }else{
    return null;
  }
   
}
}
