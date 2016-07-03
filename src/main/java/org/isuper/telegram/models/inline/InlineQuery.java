/**
 * 
 */
package org.isuper.telegram.models.inline;

import org.isuper.common.utils.Preconditions;
import org.isuper.telegram.models.Location;
import org.isuper.telegram.models.User;
import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This object represents an incoming inline query. When the user sends an empty query, your bot could return some default or trending results.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InlineQuery {

	@JsonProperty("id")
	public final String id;
	@JsonProperty("from")
	public final User from;
	@JsonProperty("location")
	public final Location location;
	@JsonProperty("query")
	public final String query;
	@JsonProperty("offset")
	public final String offset;
	
	/**
	 * @param id
	 * 					Unique identifier for this query
	 * @param from
	 * 					Sender
	 * @param location
	 * 					Optional. Sender location, only for bots that request user location
	 * @param query
	 * 					Text of the query
	 * @param offset
	 * 					Optional. Offset of the results to be returned, can be controlled by the bot
	 */
	@JsonCreator
	public InlineQuery(
			@JsonProperty("id") String id,
			@JsonProperty("from") User from,
			@JsonProperty("location") Location location,
			@JsonProperty("query") String query,
			@JsonProperty("offset") String offset) {
		Preconditions.notEmptyString(id, "Inline query ID should be provided.");
		this.id = id;
		Preconditions.notNull(from, "Sender should be provided.");
		this.from = from;
		this.location = location;
		Preconditions.notNull(query, "Text of the query should be provided.");
		this.query = query;
		this.offset = offset;
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
