/**
 * 
 */
package org.isuper.telegram.models;

import org.isuper.common.utils.Preconditions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatMember {
	
	public final User user;
	public final String status;
	
	/**
	 * @param user
	 * 					Information about the user
	 * @param status
	 * 					The member's status in the chat. Can be “creator”, “administrator”, “member”, “left” or “kicked”
	 */
	@JsonCreator
	public ChatMember(
			@JsonProperty("user") User user,
			@JsonProperty("status") String status) {
		Preconditions.notNull(user, "User should be provided.");
		this.user = user;
		Preconditions.notEmptyString(status, "Status should be provided.");
		this.status = status;
	}

}
