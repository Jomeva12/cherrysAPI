/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jomeva.crearapi.security;

import com.jomeva.crearapi.model.Rol;
import com.jomeva.crearapi.model.Users;
import com.jomeva.crearapi.repository.RolRepository;
import com.jomeva.crearapi.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author johan
 */
@Slf4j
@Service
public class UsuarioDetailService implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;
     @Autowired
private RolRepository rolRepository;
  private Users usuario;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    usuario=usuarioRepository.findByEmail(username);
    if (!Objects.isNull(usuario)) {
          Set<GrantedAuthority> authorities = new HashSet<>();
            
            // Obtener el id del rol del usuario
            Rol rol = usuario.getRol(); // Supongo que tienes un método para obtener el id del rol
            
            // Buscar el rol por id
            Optional<Rol> rolOptional = rolRepository.findById(rol.getId());
            if (rolOptional.isPresent()) {
                String rolType = rolOptional.get().getType();
                authorities.add(new SimpleGrantedAuthority(rolType));
            } else {
                throw new UsernameNotFoundException("Rol no encontrado para el usuario");
            }
      
      
      
      
      
//       Set<GrantedAuthority> authorities = new HashSet<>();   
//        authorities.add(new SimpleGrantedAuthority(usuario.getRol().toString()));    
      return new User(usuario.getEmail(), usuario.getPassword(), authorities);
    }else{
      throw new UsernameNotFoundException("Usuario no encontrado");
    }
  }

  public Users getUsuarioDetail(){
    return usuario;
  }
}
