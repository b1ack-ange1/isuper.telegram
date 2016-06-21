/**
 * 
 */
package org.isuper.telegram.models;

import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This object represents an incoming update.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Update {
	
	public final long id;
	public final Message message;
	public final Message editedMessage;
	public final InlineQuery inlineQuery;
	public final ChosenInlineResult chosenInlineResult;
	
	/**
	 * @param id
	 * 				The update‘s unique identifier. Update identifiers start from a certain positive number and increase sequentially.
	 * @param message
	 * 				Optional. New incoming message of any kind — text, photo, sticker, etc.
	 * @param editedMessage
	 * 				Optional. New version of a message that is known to the bot and was edited
	 * @param inlineQuery
	 * 				Optional. Optional. New incoming inline query.
	 * @param chosenInlineResult
	 * 				Optional. Optional. The result of a inline query that was chosen by a user and sent to their chat partner.
	 */
	@JsonCreator
	public Update(
			@JsonProperty("update_id") long id,
			@JsonProperty("message") Message message,
			@JsonProperty("edited_message") Message editedMessage,
			@JsonProperty("inline_query") InlineQuery inlineQuery,
			@JsonProperty("chosen_inline_result") ChosenInlineResult chosenInlineResult) {
		this.id = id;
		this.message = message;
		this.editedMessage = editedMessage;
		this.inlineQuery = inlineQuery;
		this.chosenInlineResult = chosenInlineResult;
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
