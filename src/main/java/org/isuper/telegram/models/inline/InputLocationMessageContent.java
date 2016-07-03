/**
 * 
 */
package org.isuper.telegram.models.inline;

import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Represents the content of a location message to be sent as the result of an inline query.
 * 
 * @author Super Wang
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputLocationMessageContent implements InputMessageContent {

	@JsonProperty("latitude")
	public final float latitude;
	@JsonProperty("longitude")
	public final float longitude;
	
	/**
	 * @param latitude
	 * 					Latitude of the location in degrees
	 * @param longitude
	 * 					Longitude of the location in degrees
	 */
	public InputLocationMessageContent(float latitude, float longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
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
