package com.jomeva.crearapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@NamedQuery(name = "Usuario.findByEmail", query = "select u from Usuario u where u.email = :email")

@DynamicUpdate
@DynamicInsert
@Data
@Entity
@Table(name = "user")
public class Usuario {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long iduser;
    @Column(name = "userName")
  private String userName;
    @Column(name = "email")
  private String email;
     @Column(name = "password")
  private String password;
      @Column(name = "disabled")
  private Boolean disabled;      
  @Column(name = "nombres")
  private String nombres; 
  
  @ManyToOne
  @JoinColumn(name = "rol") // Nombre de la columna que almacena la relación en la tabla de Usuario
  private Rol rol; 
    @Column(name = "created")
  private String created;
  
  @Column(name = "edit")
  private String edit;
  
@Column(name = "userEdit")
 private String userEdit;

}
