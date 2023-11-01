/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jomeva.crearapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "cargo")
public class Cargo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title")
  private String title;
   @Column(name = "name")
  private String name;
  @Column(name = "description")
  private String description;
  
@Column(name = "disabled")
  private Boolean disabled;

@Column(name = "startDate")
  private String startDate;

@Column(name = "finalDate")
  private String finalDate;
@Column(name = "salary")
  private String salary;

 @JsonBackReference 
  @ManyToOne
  @JoinColumn(name = "idArea") // Mapea la relación en la clase Curriculum
  private Area area;

}
