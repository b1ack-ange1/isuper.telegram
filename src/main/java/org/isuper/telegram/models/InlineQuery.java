/**
 * 
 */
package org.isuper.telegram.models;

import java.io.Serializable;

import org.isuper.common.utils.Preconditions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This object represents a message.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InlineQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5537039044986720296L;
	
	public final String id;
	public final User from;
	public final Location location;
	public final String query;
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

}
