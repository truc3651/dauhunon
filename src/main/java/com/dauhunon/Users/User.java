package com.dauhunon.Users;

import com.dauhunon.Carts.Cart;
import com.dauhunon.common.BaseEntity;
import com.dauhunon.utils.App;
import com.dauhunon.utils.Const;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints={
  @UniqueConstraint(columnNames={"email"}),
  @UniqueConstraint(columnNames={"username"})
})
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity implements Serializable {
  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
  @JsonIgnore
  private Set<Cart> carts;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(name = "reset_password_token", length = Const.PASSWORD_TOKEN_LENGTH)
  private String resetPasswordToken;

  @Column(nullable = false)
  private String fullName;

  @Column(unique = true)
  private String phoneNumber;

  @Column(insertable=true, columnDefinition = "varchar(20) default 'MALE'")
  private String gender;

  private String avatarUrl;

  @Column(columnDefinition = "varchar(20) default '" + Const.EMPLOYEE + "'")
  private String role;

  @Column(columnDefinition = "boolean default true")
  private boolean active;

  @Transient
  public String getPublicId() {
    return App.appName + "/users/" + this.getId();
  }

  public User(String email, String username, String fullName, String phoneNumber) {
    this.email = email;
    this.username = username;
    this.fullName = fullName;
    this.phoneNumber = phoneNumber;
  }
}
