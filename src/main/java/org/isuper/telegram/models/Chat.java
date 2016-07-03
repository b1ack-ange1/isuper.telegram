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
 * This object represents a chat.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Chat {

	@JsonProperty("id")
	public final long id;
	@JsonProperty("type")
	public final ChatType type;
	@JsonProperty("title")
	public final String title;
	@JsonProperty("username")
	public final String username;
	@JsonProperty("first_name")
	public final String firstName;
	@JsonProperty("last_name")
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
			@JsonProperty("id") Long id,
			@JsonProperty("type") ChatType type,
			@JsonProperty("title") String title,
			@JsonProperty("username") String username,
			@JsonProperty("first_name") String firstName,
			@JsonProperty("last_name") String lastName) {
		Preconditions.notNull(id, "Chat ID should be provided.");
		if (Math.abs(id) > 1e13) {
			throw new IllegalArgumentException(String.format("Unique identifier for this chat should not exceeding 1e13 by absolute value, but got %d", id));
		}
		this.id = id;
		Preconditions.notNull(type, "ChatType should be provided.");
		this.type = type;
		this.title = title;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
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
