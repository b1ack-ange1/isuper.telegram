/**
 * 
 */
package org.isuper.telegram.models;

import org.isuper.common.utils.Preconditions;
import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This object represents a phone contact.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contact {

	@JsonProperty("phone_number")
	public final String phoneNumber;
	@JsonProperty("first_name")
	public final String firstName;
	@JsonProperty("last_name")
	public final String lastName;
	@JsonProperty("user_id")
	public final long userID;
	
	/**
	 * @param phoneNumber
	 * 						Contact's phone number
	 * @param firstName
	 * 						Contact's first name
	 * @param lastName
	 * 						Optional. Contact's last name
	 * @param userID
	 * 						Optional. Contact's user identifier in Telegram
	 */
	public Contact(
			@JsonProperty("phone_number") String phoneNumber,
			@JsonProperty("first_name") String firstName,
			@JsonProperty("last_name") String lastName,
			@JsonProperty("user_id") Long userID) {
		Preconditions.notEmptyString(phoneNumber, "Phone number should be provided.");
		this.phoneNumber = phoneNumber;
		Preconditions.notEmptyString(phoneNumber, "First name should be provided.");
		this.firstName = firstName;
		this.lastName = lastName;
		this.userID = userID;
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
