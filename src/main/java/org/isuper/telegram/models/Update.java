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
 * This object represents an incoming update.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Update implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9087313071078446145L;
	
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
		Update other = (Update) obj;
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
