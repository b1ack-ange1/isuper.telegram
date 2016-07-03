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
 * This object contains information about one member of the chat.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatMember {
	
	@JsonProperty("user")
	public final User user;
	@JsonProperty("status")
	public final String status;
	
	/**
	 * @param user
	 * 					Information about the user
	 * @param status
	 * 					The member's status in the chat. Can be “creator”, “administrator”, “member”, “left” or “kicked”
	 */
	@JsonCreator
	public ChatMember(@JsonProperty("user") User user, @JsonProperty("status") String status) {
		Preconditions.notNull(user, "User should be provided.");
		this.user = user;
		Preconditions.notEmptyString(status, "Status should be provided.");
		this.status = status;
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
