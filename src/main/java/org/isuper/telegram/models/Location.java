/**
 * 
 */
package org.isuper.telegram.models;

import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This object represents a point on the map.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Location {

	@JsonProperty("latitude")
	public final float latitude;
	@JsonProperty("longitude")
	public final float longitude;
	
	/**
	 * @param latitude
	 * 					Latitude as defined by sender
	 * @param longitude
	 * 					Longitude as defined by sender
	 */
	@JsonCreator
	public Location(@JsonProperty("latitude") float latitude, @JsonProperty("longitude") float longitude) {
		if (Math.abs(latitude) > 90) {
			throw new IllegalArgumentException(String.format("Invalid latitude %f", latitude));
		}
		this.latitude = latitude;
		if (Math.abs(longitude) > 180) {
			throw new IllegalArgumentException(String.format("Invalid longitude %f", longitude));
		}
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
