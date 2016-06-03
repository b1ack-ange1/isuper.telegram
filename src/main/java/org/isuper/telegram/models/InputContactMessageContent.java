/**
 * 
 */
package org.isuper.telegram.models;

import org.isuper.common.utils.Preconditions;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the content of a contact message to be sent as the result of an inline query.
 * 
 * @author Super Wang
 *
 */
public class InputContactMessageContent implements InputMessageContent {
	
	@JsonProperty("phone_number")
	public final String phoneNumber;
	@JsonProperty("first_name")
	public final String firstName;
	@JsonProperty("last_name")
	public final String lastName;
	
	/**
	 * @param phoneNumber
	 * 					Contact's phone number
	 * @param firstName
	 * 					Contact's first name
	 * @param lastName
	 * 					Optional. Contact's last name
	 */
	public InputContactMessageContent(String phoneNumber, String firstName, String lastName) {
		Preconditions.notEmptyString(phoneNumber, "Contact's phone number should be provided.");
		this.phoneNumber = phoneNumber;
		Preconditions.notEmptyString(firstName, "Contact's first name should be provided.");
		this.firstName = firstName;
		this.lastName = lastName;
	}

}
