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
 * This object represents a message.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1535039204547512780L;
	
	public final long id;
	public final User from;
	public final long date;
	public final Chat chat;
	public final User forwardFrom;
	public final long forwardDate;
	public final Message replyTo;
	public final String text;
	public final Location location;
	public final User newChatParticipant;
	public final User leftChatParticipant;
	public final String newChatTitle;
	
	/**
	 * @param id
	 * 					Unique message identifier
	 * @param from
	 * 					Optional. Sender, can be empty for messages sent to channels
	 * @param date
	 * 					Date the message was sent in Unix time
	 * @param chat
	 * 					Conversation the message belongs to
	 * @param forwardFrom
	 * 					Optional. For forwarded messages, sender of the original message
	 * @param forwardDate
	 * 					Optional. For forwarded messages, date the original message was sent in Unix time
	 * @param replyTo
	 * 					Optional. For replies, the original message. Note that the Message object in this field will not contain further reply_to_message fields even if it itself is a reply.
	 * @param text
	 * 					Optional. For text messages, the actual UTF-8 text of the message
	 * @param location
	 * 					Optional. Message is a shared location, information about the location
	 * @param newChatParticipant
	 * 					Optional. A new member was added to the group, information about them (this member may be the bot itself)
	 * @param leftChatParticipant
	 * 					Optional. A member was removed from the group, information about them (this member may be the bot itself)
	 * @param newChatTitle
	 * 					Optional. A chat title was changed to this value
	 */
	public Message(
			@JsonProperty("message_id") long id,
			@JsonProperty("from") User from,
			@JsonProperty("date") long date,
			@JsonProperty("chat") Chat chat,
			@JsonProperty("forward_from") User forwardFrom,
			@JsonProperty("forward_date") long forwardDate,
			@JsonProperty("reply_to_message") Message replyTo,
			@JsonProperty("text") String text,
			@JsonProperty("location") Location location,
			@JsonProperty("new_chat_participant") User newChatParticipant,
			@JsonProperty("left_chat_participant") User leftChatParticipant,
			@JsonProperty("new_chat_title") String newChatTitle) {
		this.id = id;
		this.from = from;
		this.date = date;
		this.chat = chat;
		this.forwardFrom = forwardFrom;
		this.forwardDate = forwardDate;
		this.replyTo = replyTo;
		this.text = text;
		this.location = location;
		this.newChatParticipant = newChatParticipant;
		this.leftChatParticipant = leftChatParticipant;
		this.newChatTitle = newChatTitle;
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
		Message other = (Message) obj;
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
