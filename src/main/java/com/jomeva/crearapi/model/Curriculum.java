package com.jomeva.crearapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import org.springframework.security.core.userdetails.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "curriculum")
@Data
public class Curriculum {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "firstName")
  private String firstName;

  @Column(name = "lastName")
  private String lastName;

  @Column(name = "address")
  private String address;

  @Column(name = "email")
  private String email;

  @Column(name = "phone")
  private String phone;

  @Column(name = "phoneWork")
  private String phoneWork;

  @Column(name = "profession")
  private String profession;

  @Column(name = "maritalStatus")
  private String maritalStatus;

  @Column(name = "city")
  private String city;

  @Column(name = "state")
  private String state;

  @Column(name = "gender")
  private String gender;

  @Column(name = "bloodType")
  private String bloodType;

  @Column(name = "birthDate")
  private String birthDate;
  
   @Column(name = "disabled")
  private Boolean disabled;  
 
  @Column(name = "create_date")
  private String createDate;

  @Column(name = "edit_date")
  private String editDate;

  @Column(name = "userCreate")
  private String userCreate;
  
  //@JsonIgnore
  @JsonBackReference 
  @OneToOne
  @JoinColumn(name = "iduser") // Mapea la relación en la clase Curriculum
  private Users users;

  @Column(name = "documentType")
  private String documentType;

  @Column(name = "documentId")
  private String documentId;
}
