/**
 * 
 */
package org.isuper.telegram.models;

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
public class User {

	@JsonProperty("id")
	public final long id;
	@JsonProperty("first_name")
	public final String firstName;
	@JsonProperty("last_name")
	public final String lastName;
	@JsonProperty("username")
	public final String username;
	
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
			@JsonProperty("id") Long id,
			@JsonProperty("first_name") String firstName,
			@JsonProperty("last_name") String lastName,
			@JsonProperty("username") String username) {
		Preconditions.notNull(id, "User's or bot's ID should be provided.");
		this.id = id;
		Preconditions.notNull(firstName, "User's or bot's first name should be provided.");
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
	}

	public String toJSON() {
		try {
			return TelegramUtils.getObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "{}";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.toJSON();
	}
	
}
