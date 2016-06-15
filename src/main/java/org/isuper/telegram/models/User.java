/**
 * 
 */
package org.isuper.telegram.models;

import java.io.Serializable;

import org.isuper.common.utils.Preconditions;
import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This object represents a Telegram user or bot.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4052116696388288998L;
	
	private final long id;
	private final String firstName;
	private final String lastName;
	private final String username;
	
	/**
	 * @param id
	 * 					Unique identifier for this user or bot
	 * @param firstName
	 * 					User's or bot's first name
	 * @param lastName
	 * 					Optional. User's or bot's last name
	 * @param username
	 * 					Optional. User's or bot's username
	 */
	@JsonCreator
	public User(
			@JsonProperty("id") long id,
			@JsonProperty("first_name") String firstName,
			@JsonProperty("last_name") String lastName,
			@JsonProperty("username") String username) {
		this.id = id;
		Preconditions.notNull(firstName, "User's or bot's first name cannot be null!");
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (this.id ^ (this.id >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (this.id != other.id)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		try {
			return TelegramUtils.getObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "{}";
	}
	
}
