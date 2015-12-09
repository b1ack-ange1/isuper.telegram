/**
 * 
 */
package org.isuper.telegram.models;

import java.io.Serializable;

import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This object represents a point on the map.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Location implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1567963952505782270L;
	
	public final float longitude;
	public final float latitude;
	
	/**
	 * @param longitude
	 * 					Longitude as defined by sender
	 * @param latitude
	 * 					Latitude as defined by sender
	 */
	public Location(float longitude, float latitude) {
		if (Math.abs(longitude) > 180) {
			throw new IllegalArgumentException(String.format("Invalid longitude %f", longitude));
		}
		this.longitude = longitude;
		if (Math.abs(latitude) > 90) {
			throw new IllegalArgumentException(String.format("Invalid latitude %f", latitude));
		}
		this.latitude = latitude;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(this.latitude);
		result = prime * result + Float.floatToIntBits(this.longitude);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (Float.floatToIntBits(this.latitude) != Float.floatToIntBits(other.latitude))
			return false;
		if (Float.floatToIntBits(this.longitude) != Float.floatToIntBits(other.longitude))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		try {
			return TelegramUtils.getObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "{}";
	}
	
}
