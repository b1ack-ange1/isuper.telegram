/**
 * 
 */
package org.isuper.telegram.models;

import java.io.Serializable;

import org.isuper.common.utils.Preconditions;
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
	
	public final String resultID;
	public final User from;
	public final Location location;
	public final String inlineMessageID;
	public final String query;
	
	/**
	 * @param resultID
	 * 					The unique identifier for the result that was chosen.
	 * @param from
	 * 					The user that chose the result.
	 * @param location
	 * 					Optional. Sender location, only for bots that require user location.
	 * @param inlineMessageID
	 * 					Optional. Identifier of the sent inline message. Available only if there is an inline keyboard attached to the message. Will be also received in callback queries and can be used to edit the message.
	 * @param query
	 * 					The query that was used to obtain the result.
	 */
	@JsonCreator
	public ChosenInlineResult(
			@JsonProperty("result_id") String resultID,
			@JsonProperty("from") User from,
			@JsonProperty("location") Location location,
			@JsonProperty("inline_message_id") String inlineMessageID,
			@JsonProperty("query") String query) {
		this.resultID = resultID;
		Preconditions.notNull(from, "The user that chose the result cannot be null!");
		this.from = from;
		this.location = location;
		this.inlineMessageID = inlineMessageID;
		Preconditions.notNull(query, "The query that was used to obtain the result cannot be null!");
		this.query = query;
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
