package com.example.taskmanager.entities;

import java.util.HashSet;
import java.util.Set;

import com.example.taskmanager.core.BaseEntity;
import com.example.taskmanager.services.HashService;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @JsonIgnore
  @Column(name = "password", nullable = false)
  private String password;

  @JsonIgnore
  @Column(name = "salt", nullable = false)
  private final byte[] salt = HashService.generateSalt();

  @JsonIgnore
  @OneToMany(mappedBy = "author")
  private final Set<Task> tasks = new HashSet<>();

  @Override
  @PrePersist
  public void prePersist() {
    super.prePersist();
    this.password = HashService.apply(password, this.salt);
  }

}
