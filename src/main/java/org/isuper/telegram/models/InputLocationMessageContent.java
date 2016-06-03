/**
 * 
 */
package org.isuper.telegram.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the content of a location message to be sent as the result of an inline query.
 * 
 * @author Super Wang
 *
 */
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
	
}
