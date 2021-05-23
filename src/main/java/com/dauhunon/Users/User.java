package com.dauhunon.Users;

import com.dauhunon.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity implements Serializable {
  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String fullname;
  private String phoneNumber;

  @Column(columnDefinition = "varchar(20) default 'MALE'")
  private String gender;

  private String avatar;
  private int role;

  @Column(columnDefinition = "boolean default true")
  private boolean active;
}
