/**
 * 
 */
package org.isuper.telegram.models;

import org.isuper.common.utils.Preconditions;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the content of a text message to be sent as the result of an inline query.
 * 
 * @author Super Wang
 *
 */
public class InputTextMessageContent implements InputMessageContent {
	
	@JsonProperty("message_text")
	public final String text;
	@JsonProperty("parse_mode")
	public final MessageParseMode parseMode;
	@JsonProperty("disable_web_page_preview")
	public final boolean disableWebPagePreview;
	
	/**
	 * @param text
	 * 					Text of the message to be sent, 1-4096 characters
	 * @param parseMode
	 * 					Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
	 * @param disableWebPagePreview
	 * 					Optional. Disables link previews for links in the sent message
	 */
	public InputTextMessageContent(String text, MessageParseMode parseMode, boolean disableWebPagePreview) {
		Preconditions.notEmptyString(text, "Text of message should be provided");
		this.text = text.length() > 2096 ? text.substring(0, 4096) : text;
		this.parseMode = parseMode;
		this.disableWebPagePreview = disableWebPagePreview;
	}

}
