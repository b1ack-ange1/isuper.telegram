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
 * Upon receiving a message with this object, Telegram clients will display a reply interface to the user (act as if the user has selected the bot‘s message and tapped ’Reply').
 * This can be extremely useful if you want to create user-friendly step-by-step interfaces without having to sacrifice privacy mode.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ForceReply implements ReplyMarkup {
	
	@JsonProperty("force_reply")
	public final boolean forceReply = true;
	@JsonProperty("selective")
	public final boolean selective;
	
	/**
	 * @param selective
	 * 				Optional. Use this parameter if you want to force reply from specific users only. Targets:
	 * 				1) users that are @mentioned in the text of the Message object;
	 * 				2) if the bot's message is a reply (has reply_to_message_id), sender of the original message.
	 */
	public ForceReply(@JsonProperty("selective") Boolean selective) {
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
