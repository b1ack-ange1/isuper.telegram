/**
 * 
 */
package org.isuper.telegram.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the content of a venue message to be sent as the result of an inline query.
 * 
 * @author Super Wang
 *
 */
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
	
}
