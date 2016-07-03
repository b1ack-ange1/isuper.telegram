/**
 * 
 */
package org.isuper.telegram.models.markups;

import org.isuper.common.utils.Preconditions;
import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This object represents a custom keyboard with reply options.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplyKeyboardMarkup implements ReplyMarkup {
	
	@JsonProperty("keyboard")
	public final KeyboardButton[] keyboard;
	@JsonProperty("resize_keyboard")
	public final boolean resizeKeyboard;
	@JsonProperty("one_time_Keyboard")
	public final boolean oneTimeKeyboard;
	@JsonProperty("selective")
	public final boolean selective;
	
	/**
	 * @param keyboard
	 * 				Array of button rows, each represented by an Array of KeyboardButton objects.
	 * @param resizeKeyboard
	 * 				Optional. Requests clients to resize the keyboard vertically for optimal fit (e.g., make the keyboard smaller if there are just two rows of buttons). Defaults to false, in which case the custom keyboard is always of the same height as the app's standard keyboard.
	 * @param oneTimeKeyboard
	 * 				Optional. Requests clients to hide the keyboard as soon as it's been used. The keyboard will still be available, but clients will automatically display the usual letter-keyboard in the chat – the user can press a special button in the input field to see the custom keyboard again. Defaults to false.
	 * @param selective
	 * 				Optional. Use this parameter if you want to force reply from specific users only. Targets:
	 * 				1) users that are @mentioned in the text of the Message object;
	 * 				2) if the bot's message is a reply (has reply_to_message_id), sender of the original message.
	 */
	public ReplyKeyboardMarkup(
			@JsonProperty("keyboard") KeyboardButton[] keyboard,
			@JsonProperty("resize_keyboard") Boolean resizeKeyboard,
			@JsonProperty("one_time_Keyboard") Boolean oneTimeKeyboard,
			@JsonProperty("selective") Boolean selective) {
		Preconditions.notNull(keyboard, "Keyboard should be provided.");
		if (keyboard.length < 1) {
			throw new IllegalArgumentException("Keyboard should have at least one KeyButton.");
		}
		this.keyboard = keyboard;
		this.resizeKeyboard = resizeKeyboard;
		this.oneTimeKeyboard = oneTimeKeyboard;
		this.selective = selective;
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
