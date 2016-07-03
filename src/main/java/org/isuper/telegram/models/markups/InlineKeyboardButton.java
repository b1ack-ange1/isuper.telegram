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
 * This object represents one button of an inline keyboard.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InlineKeyboardButton {
	
	@JsonProperty("text")
	public final String text;
	@JsonProperty("url")
	public final String url;
	@JsonProperty("callback_data")
	public final String callbackData;
	@JsonProperty("switch_inline_query")
	public final String switchInlineQuery;
	
	/**
	 * @param text
	 * 					Label text on the button.
	 * @param url
	 * 					Optional. HTTP url to be opened when button is pressed.
	 * @param callbackData
	 * 					Optional. Data to be sent in a callback query to the bot when button is pressed, 1-64 bytes.
	 * @param switchInlineQuery
	 * 					Optional. If set, pressing the button will prompt the user to select one of their chats, open that chat and insert the bot‘s username and the specified inline query in the input field. Can be empty, in which case just the bot’s username will be inserted.
	 */
	public InlineKeyboardButton(
			@JsonProperty("text") String text,
			@JsonProperty("url") String url,
			@JsonProperty("callback_data") String callbackData,
			@JsonProperty("switch_inline_query") String switchInlineQuery) {
		Preconditions.notEmptyString(text, "Text should be provided.");
		this.text = text;
		int optionalFieldCnt = 0;
		if (!Preconditions.isEmptyString(url)) {
			optionalFieldCnt++;
		}
		if (!Preconditions.isEmptyString(callbackData)) {
			optionalFieldCnt++;
		}
		if (!Preconditions.isEmptyString(switchInlineQuery)) {
			optionalFieldCnt++;
		}
		if (optionalFieldCnt != 1) {
			throw new IllegalArgumentException("You must use exactly one of the optional fields.");
		}
		this.url = url;
		this.callbackData = callbackData;
		this.switchInlineQuery = switchInlineQuery;
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
