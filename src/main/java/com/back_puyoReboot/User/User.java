package com.back_puyoReboot.User;

import com.back_puyoReboot.core.BaseEntity;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User extends BaseEntity implements UserDetails {

  public static final int EMAIL_MAX_LENGTH = 320;
  public static final int PLAYER_NAME_LENGTH = 10;

  @Column(nullable = false, length = EMAIL_MAX_LENGTH, unique = true)
  private String email;

  @Column(nullable = false)
  private String hashedPassword;

  @Column(nullable = false, length = PLAYER_NAME_LENGTH)
  private String playerName;

  @Column(nullable = false)
  private String avatar = "/images/User/Avatar.png";

  @Column(nullable = false, unique = true)
  private Long playerNumber;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AccountEnumType accountStatus;

  @Enumerated(EnumType.STRING)
  @ElementCollection(fetch = FetchType.EAGER)
  private Set<UserEnumType> roles = new HashSet<>();

  // Necessary to have an empty constructor to instance object.
  public User() {}

  public User(
    Set<UserEnumType> roles,
    AccountEnumType accountStatus,
    String hashedPassword,
    String email,
    String playerName,
    String avatar,
    Long playerNumber
  ) {
    this.roles = roles;
    this.accountStatus = accountStatus;
    this.hashedPassword = hashedPassword;
    this.email = email;
    this.playerName = playerName;
    this.avatar = avatar;
    this.playerNumber = playerNumber;
  }

  public User(Set<UserEnumType> roles, AccountEnumType accountStatus, String hashedPassword, String email) {
    this.roles = roles;
    this.accountStatus = accountStatus;
    this.hashedPassword = hashedPassword;
    this.email = email;
  }

  public AccountEnumType getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(AccountEnumType accountStatus) {
    this.accountStatus = accountStatus;
  }

  public String getHashedPassword() {
    return hashedPassword;
  }

  public void setHashedPassword(String hashed_password) {
    this.hashedPassword = hashedPassword;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<UserEnumType> getRoles() {
    return roles;
  }

  public void setRoles(Set<UserEnumType> roles) {
    this.roles = roles;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Long getPlayerNumber() {
    return playerNumber;
  }

  public void setPlayerNumber(Long playerNumber) {
    this.playerNumber = playerNumber;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream().map(Enum::name).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
  }

  @Override
  public String getPassword() {
    return hashedPassword;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    // return true - TODO complete
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    // return true - TODO complete
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // return true - TODO complete
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    // return true - TODO complete
    return UserDetails.super.isEnabled();
  }
}
