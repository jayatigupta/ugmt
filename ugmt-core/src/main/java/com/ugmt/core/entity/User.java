package com.ugmt.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user_detail", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
public class User extends AbstractEntity {

	private static final long serialVersionUID = 8400222749579308466L;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "password", nullable = false, length = 512)
	private String password;

	@Column(name = "email", nullable = false, length = 100)
	private String email;

	@Column(name = "verified", nullable = false)
	private boolean verified;

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
