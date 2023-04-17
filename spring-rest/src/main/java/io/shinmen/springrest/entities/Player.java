package io.shinmen.springrest.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class Player {
    
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  private String nationality;

  @JsonFormat(pattern = "dd-MM-yyyy")
  private Date birthDate;

  private int titles;

  public Player(String name, String nationality, Date birthDate, int titles) {
    super();
    this.name = name;
    this.nationality = nationality;
    this.birthDate = birthDate;
    this.titles = titles;
  }
    
}
