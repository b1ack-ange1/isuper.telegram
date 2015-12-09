/**
 * 
 */
package org.isuper.telegram.models;

import java.io.Serializable;

import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This object represents a chat.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Chat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4478241009092585429L;
	
	public final long id;
	public final ChatType type;
	public final String title;
	public final String username;
	public final String firstName;
	public final String lastName;
	
	/**
	 * @param id
	 * 					Unique identifier for this chat should not exceeding 1e13 by absolute value
	 * @param type
	 * 					Type of chat
	 * @param title
	 * 					Optional. Title, for channels and group chats
	 * @param username
	 * 					Optional. Username, for private chats and channels if available
	 * @param firstName
	 * 					Optional. First name of the other party in a private chat
	 * @param lastName
	 * 					Optional. Last name of the other party in a private chat
	 */
	@JsonCreator
	public Chat(
			@JsonProperty("id") long id,
			@JsonProperty("type") ChatType type,
			@JsonProperty("title") String title,
			@JsonProperty("username") String username,
			@JsonProperty("first_name") String firstName,
			@JsonProperty("last_name") String lastName) {
		if (Math.abs(id) > 1e13) {
			throw new IllegalArgumentException(String.format("Unique identifier for this chat should not exceeding 1e13 by absolute value, but got %d", id));
		}
		this.id = id;
		this.type = type;
		if (type == null) {
			throw new IllegalArgumentException("ChatType should not be null!");
		}
		this.title = title;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
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
		Chat other = (Chat) obj;
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
