package org.exambuilder.rest.model;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails {

	@Id
	@GeneratedValue
	Long id;
	
	@NotEmpty
	@Size(min=1, max=255)
	@Column(name="first_name", nullable=false)
	private String firstName;
	
	@Size(min=1, max=255)
	@Column(name="last_name")
	private String lastName;
	
	@NotEmpty
	@Size(min=1, max=16)
	@Column(name="user_name", nullable=false, unique=true)
	private String username;
	
	@NotEmpty
	@Size(min=1, max=255)
	@Column(nullable=false, unique=true)
	private String email;
	
	@NotEmpty
	@Size(min=4)
	@Column(nullable=false)
	private String password;
	
	@Transient
	private String confirmPassword;
	
	@Column(nullable=false)
	private boolean enabled;
	
	@Column(nullable=false)
	private boolean tokenExpired;
	
	@ElementCollection(targetClass=Role.class, fetch=FetchType.EAGER)
	@JoinTable(name="user_role",
			joinColumns=@JoinColumn(name="user_id"))
	@Column(name="role", nullable=false)
	@Enumerated(EnumType.STRING)
	private Collection<Role> roles;
	

	@Transient
	private boolean accountNonExpired = true;
	@Transient
	private boolean accountNonLocked = true;
	@Transient
	private boolean credentialsNonExpired = true;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isTokenExpired() {
		return tokenExpired;
	}

	public void setTokenExpired(boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return roles.stream()
					.map(Role::toString)
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
}
