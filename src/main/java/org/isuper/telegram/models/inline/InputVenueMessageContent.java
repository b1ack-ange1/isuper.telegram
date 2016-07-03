/**
 * 
 */
package org.isuper.telegram.models.inline;

import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Represents the content of a venue message to be sent as the result of an inline query.
 * 
 * @author Super Wang
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputVenueMessageContent implements InputMessageContent {

	@JsonProperty("latitude")
	public final float latitude;
	@JsonProperty("longitude")
	public final float longitude;
	@JsonProperty("title")
	public final String title;
	@JsonProperty("address")
	public final String address;
	@JsonProperty("foursquareID")
	public final String foursquareID;
	
	/**
	 * @param latitude
	 * 					Latitude of the location in degrees
	 * @param longitude
	 * 					Longitude of the location in degrees
	 * @param title
	 * 					Name of the venue
	 * @param address
	 * 					Address of the venue
	 * @param foursquareID
	 * 					Optional. Foursquare identifier of the venue, if known
	 */
	public InputVenueMessageContent(float latitude, float longitude, String title, String address, String foursquareID) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.title = title;
		this.address = address;
		this.foursquareID = foursquareID;
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
