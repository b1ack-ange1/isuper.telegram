/**
 * 
 */
package org.isuper.telegram.models.markups;

import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This object represents an inline keyboard that appears right next to the message it belongs to.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InlineKeyboardMarkup implements ReplyMarkup {
	
	@JsonProperty("inline_keyboard")
	public final InlineKeyboardButton[] inlineKeyboard;

	/**
	 * @param inlineKeyboard
	 * 				Array of button rows, each represented by an Array of InlineKeyboardButton objects.
	 */
	public InlineKeyboardMarkup(@JsonProperty("inline_keyboard") InlineKeyboardButton[] inlineKeyboard) {
		this.inlineKeyboard = inlineKeyboard;
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
