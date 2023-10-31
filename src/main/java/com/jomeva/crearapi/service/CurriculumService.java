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
  private CurriculumRespository curriculumRepository;

  public ResponseEntity<String> crearCurriculum(Curriculum curriculum) {

    try {
      // Verifica si ya existe un currículum con el mismo idUser
      Curriculum existingCurriculum = curriculumRepository.findByUsers(curriculum.getUsers());

      if (existingCurriculum != null) {
        return CherryUtils.getResponseEntity("Ya existe un currículum para este usuario. No se puede crear uno nuevo.", HttpStatus.BAD_REQUEST);
      }

      // Si no existe, guarda el nuevo currículum
      curriculumRepository.save(curriculum);

      return CherryUtils.getResponseEntity("Currículum registrado con éxito", HttpStatus.CREATED);
    } catch (Exception e) {
      return CherryUtils.getResponseEntity("Error al registrar el currículum: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  public ResponseEntity<String> deleteCurriculumById(Long id) {
    Optional<Curriculum> opt = curriculumRepository.findById(id);
    if (!opt.isEmpty()) {
      Curriculum existingCurriculum = opt.get();
      existingCurriculum.setDisabled(true);
      curriculumRepository.save(existingCurriculum);
      return CherryUtils.getResponseEntity("Curriculum eliminado con éxito", HttpStatus.OK);
    } else {
      return CherryUtils.getResponseEntity("Error al eliminar el currículum: ", HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  public List<Curriculum> getAllCurriculum() {
    try {
      return (List<Curriculum>) curriculumRepository.findByDisabled(Boolean.FALSE);
      //return null;
    } catch (DataAccessException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  public Curriculum getCurriculumByIdUser(String id) {
    Optional<Users> opt = userRepo.findById(Long.valueOf(id));
    if (!opt.isEmpty()) {
      return curriculumRepository.findByUsers(opt.get());
    } else {
      return null;
    }

  }

  public ResponseEntity<String> updateCurriculum(Curriculum updatedCurriculum) {
    try {
            Optional<Curriculum> existingCurriculumOptional = curriculumRepository.findById(updatedCurriculum.getId());

            if (existingCurriculumOptional.isEmpty()) {
                return CherryUtils.getResponseEntity("No se encontró el currículum con ID " + updatedCurriculum.getId(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Curriculum existingCurriculum = existingCurriculumOptional.get();

            if (updatedCurriculum.getFirstName() != null) {
                existingCurriculum.setFirstName(updatedCurriculum.getFirstName());
            }
            if (updatedCurriculum.getLastName() != null) {
                existingCurriculum.setLastName(updatedCurriculum.getLastName());
            }
            if (updatedCurriculum.getAddress() != null) {
                existingCurriculum.setAddress(updatedCurriculum.getAddress());
            }
            if (updatedCurriculum.getEmail() != null) {
                existingCurriculum.setEmail(updatedCurriculum.getEmail());
            }
            if (updatedCurriculum.getPhone() != null) {
                existingCurriculum.setPhone(updatedCurriculum.getPhone());
            }
            if (updatedCurriculum.getPhoneWork() != null) {
                existingCurriculum.setPhoneWork(updatedCurriculum.getPhoneWork());
            }
            if (updatedCurriculum.getProfession() != null) {
                existingCurriculum.setProfession(updatedCurriculum.getProfession());
            }
            if (updatedCurriculum.getMaritalStatus() != null) {
                existingCurriculum.setMaritalStatus(updatedCurriculum.getMaritalStatus());
            }
            if (updatedCurriculum.getCity() != null) {
                existingCurriculum.setCity(updatedCurriculum.getCity());
            }
            if (updatedCurriculum.getState() != null) {
                existingCurriculum.setState(updatedCurriculum.getState());
            }
            if (updatedCurriculum.getGender() != null) {
                existingCurriculum.setGender(updatedCurriculum.getGender());
            }
            if (updatedCurriculum.getBloodType() != null) {
                existingCurriculum.setBloodType(updatedCurriculum.getBloodType());
            }
            if (updatedCurriculum.getBirthDate() != null) {
                existingCurriculum.setBirthDate(updatedCurriculum.getBirthDate());
            }
            if (updatedCurriculum.getDisabled() != null) {
                existingCurriculum.setDisabled(updatedCurriculum.getDisabled());
            }
            if (updatedCurriculum.getCreateDate() != null) {
                existingCurriculum.setCreateDate(updatedCurriculum.getCreateDate());
            }
            if (updatedCurriculum.getEditDate() != null) {
                existingCurriculum.setEditDate(updatedCurriculum.getEditDate());
            }
            if (updatedCurriculum.getUserCreate() != null) {
                existingCurriculum.setUserCreate(updatedCurriculum.getUserCreate());
            }
            if (updatedCurriculum.getDocumentType() != null) {
                existingCurriculum.setDocumentType(updatedCurriculum.getDocumentType());
            }
            if (updatedCurriculum.getDocumentId() != null) {
                existingCurriculum.setDocumentId(updatedCurriculum.getDocumentId());
            }

            // Guarda los cambios en la base de datos
            curriculumRepository.save(existingCurriculum);

            return CherryUtils.getResponseEntity("Currículum actualizado con éxito", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return CherryUtils.getResponseEntity("Error al actualizar el currículum: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
  }
}
