/**
 * 
 */
package org.isuper.telegram.models;

import java.io.Serializable;

import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This object represents a message.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
	public final Chat forwardFromChat;
	public final Long forwardDate;
	public final Message replyTo;
	public final Long editDate;
	public final String text;
	public final Location location;
	public final User newChatParticipant;
	public final User leftChatParticipant;
	public final String newChatTitle;
	public final PhotoSize[] newChatPhoto;
	public final Boolean deleteChatPhoto;
	public final Boolean groupChatCreated;
	public final Boolean supergroupChatCreated;
	public final Boolean channelChatCreated;
	public final Long migrateToChatID;
	public final Long migrateFromChatID;
	public final Message pinned;
	
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
	 * @param forwardFromChat
	 * 					Optional. For messages forwarded from a channel, information about the original channel
	 * @param forwardDate
	 * 					Optional. For forwarded messages, date the original message was sent in Unix time
	 * @param replyTo
	 * 					Optional. For replies, the original message. Note that the Message object in this field will not contain further reply_to_message fields even if it itself is a reply.
	 * @param editDate
	 * 					Optional. Date the message was last edited in Unix time
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
	 * @param newChatPhoto
	 * 					Optional. A chat photo was change to this value
	 * @param deleteChatPhoto
	 * 					Optional. Optional. Service message: the chat photo was deleted
	 * @param groupChatCreated
	 * 					Optional. Optional. Service message: the group has been created
	 * @param supergroupChatCreated
	 * 					Optional. Optional. Service message: the supergroup has been created. This field can‘t be received in a message coming through updates, because bot can’t be a member of a supergroup when it is created. It can only be found in reply_to_message if someone replies to a very first message in a directly created supergroup.
	 * @param channelChatCreated
	 * 					Optional. Optional. Service message: the channel has been created. This field can‘t be received in a message coming through updates, because bot can’t be a member of a channel when it is created. It can only be found in reply_to_message if someone replies to a very first message in a channel.
	 * @param migrateToChatID
	 * 					Optional. The group has been migrated to a supergroup with the specified identifier. This number may be greater than 32 bits and some programming languages may have difficulty/silent defects in interpreting it. But it smaller than 52 bits, so a signed 64 bit integer or double-precision float type are safe for storing this identifier.
	 * @param migrateFromChatID
	 * 					Optional. The supergroup has been migrated from a group with the specified identifier. This number may be greater than 32 bits and some programming languages may have difficulty/silent defects in interpreting it. But it smaller than 52 bits, so a signed 64 bit integer or double-precision float type are safe for storing this identifier.
	 * @param pinned
	 * 					Optional. Specified message was pinned. Note that the Message object in this field will not contain further reply_to_message fields even if it is itself a reply.
	 */
	public Message(
			@JsonProperty("message_id") long id,
			@JsonProperty("from") User from,
			@JsonProperty("date") long date,
			@JsonProperty("chat") Chat chat,
			@JsonProperty("forward_from") User forwardFrom,
			@JsonProperty("forward_from_chat") Chat forwardFromChat,
			@JsonProperty("forward_date") long forwardDate,
			@JsonProperty("reply_to_message") Message replyTo,
			@JsonProperty("edit_date") Long editDate,
			@JsonProperty("text") String text,
			@JsonProperty("location") Location location,
			@JsonProperty("new_chat_participant") User newChatParticipant,
			@JsonProperty("left_chat_participant") User leftChatParticipant,
			@JsonProperty("new_chat_title") String newChatTitle,
			@JsonProperty("new_chat_photo") PhotoSize[] newChatPhoto,
			@JsonProperty("delete_chat_photo") Boolean deleteChatPhoto,
			@JsonProperty("group_chat_created") Boolean groupChatCreated,
			@JsonProperty("supergroup_chat_created") Boolean supergroupChatCreated,
			@JsonProperty("channel_chat_created") Boolean channelChatCreated,
			@JsonProperty("migrate_to_chat_id") Long migrateToChatID,
			@JsonProperty("migrate_from_chat_id") Long migrateFromChatID,
			@JsonProperty("pinned_message") Message pinned
			) {
		this.id = id;
		this.from = from;
		this.date = date;
		this.chat = chat;
		this.forwardFrom = forwardFrom;
		this.forwardFromChat = forwardFromChat;
		this.forwardDate = forwardDate;
		this.replyTo = replyTo;
		this.editDate = editDate;
		this.text = text;
		this.location = location;
		this.newChatParticipant = newChatParticipant;
		this.leftChatParticipant = leftChatParticipant;
		this.newChatTitle = newChatTitle;
		this.newChatPhoto = newChatPhoto;
		this.deleteChatPhoto = deleteChatPhoto;
		this.groupChatCreated = groupChatCreated;
		this.supergroupChatCreated = supergroupChatCreated;
		this.channelChatCreated = channelChatCreated;
		this.migrateToChatID = migrateToChatID;
		this.migrateFromChatID = migrateFromChatID;
		this.pinned = pinned;
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
