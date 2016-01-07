/**
 * 
 */
package org.isuper.telegram.models;

import java.io.Serializable;

import org.isuper.telegram.utils.TelegramUtils;

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
public class InlineQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5537039044986720296L;
	
	public final long id;
	public final User from;
	public final String query;
	public final String offset;
	
	/**
	 * @param id
	 * 					Unique identifier for this query
	 * @param from
	 * 					Sender
	 * @param query
	 * 					Text of the query
	 * @param offset
	 * 					Offset of the results to be returned, can be controlled by the bot
	 */
	public InlineQuery(
			@JsonProperty("id") long id,
			@JsonProperty("from") User from,
			@JsonProperty("query") String query,
			@JsonProperty("offset") String offset) {
		this.id = id;
		this.from = from;
		this.query = query;
		this.offset = offset;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (this.id ^ (this.id >>> 32));
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
		InlineQuery other = (InlineQuery) obj;
		if (this.id != other.id)
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
