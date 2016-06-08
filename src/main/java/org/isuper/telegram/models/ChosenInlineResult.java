/**
 * 
 */
package org.isuper.telegram.models;

import java.io.Serializable;

import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This object represents a message.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ChosenInlineResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1360477690034862457L;
	
	public final long result_id;
	public final User from;
	public final String query;
	
	/**
	 * @param result_id
	 * 					The unique identifier for the result that was chosen.
	 * @param from
	 * 					The user that chose the result.
	 * @param query
	 * 					The query that was used to obtain the result.
	 */
	@JsonCreator
	public ChosenInlineResult(
			@JsonProperty("result_id") long result_id,
			@JsonProperty("from") User from,
			@JsonProperty("query") String query) {
		this.result_id = result_id;
		this.from = from;
		this.query = query;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (this.result_id ^ (this.result_id >>> 32));
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
		ChosenInlineResult other = (ChosenInlineResult) obj;
		if (this.result_id != other.result_id)
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
