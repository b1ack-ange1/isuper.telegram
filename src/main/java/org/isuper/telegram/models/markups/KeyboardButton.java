/**
 * 
 */
package org.isuper.telegram.models.markups;

import org.isuper.common.utils.Preconditions;
import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This object represents one button of the reply keyboard.
 * For simple text buttons String can be used instead of this object to specify text of the button.
 * Optional fields are mutually exclusive.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KeyboardButton {
	
	@JsonProperty("text") 
	public final String text;
	@JsonProperty("request_contact")
	public final Boolean requestContact;
	@JsonProperty("request_location")
	public final Boolean requestLocation;
	
	/**
	 * @param text
	 * 					Text of the button. If none of the optional fields are used, it will be sent to the bot as a message when the button is pressed.
	 * @param requestContact
	 * 					Optional. If True, the user's phone number will be sent as a contact when the button is pressed. Available in private chats only.
	 * @param requestLocation
	 * 					Optional. If True, the user's current location will be sent when the button is pressed. Available in private chats only.
	 */
	@JsonCreator
	public KeyboardButton(
			@JsonProperty("text") String text,
			@JsonProperty("request_contact") Boolean requestContact,
			@JsonProperty("request_location") Boolean requestLocation) {
		Preconditions.notEmptyString(text, "Text should be provided.");
		this.text = text;
		this.requestContact = requestContact;
		this.requestLocation = requestLocation;
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
