/**
 * 
 */
package org.isuper.telegram;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.isuper.common.utils.Preconditions;
import org.isuper.httpclient.AsyncHttpClient;
import org.isuper.telegram.exceptions.BlankResponseException;
import org.isuper.telegram.exceptions.InvalidJsonResponseException;
import org.isuper.telegram.exceptions.NotJsonResponseException;
import org.isuper.telegram.exceptions.RecoverableErrorResponseException;
import org.isuper.telegram.exceptions.UnrecoverableErrorResponseException;
import org.isuper.telegram.models.Chat;
import org.isuper.telegram.models.ChatMember;
import org.isuper.telegram.models.InlineQueryResult;
import org.isuper.telegram.models.Message;
import org.isuper.telegram.models.MessageParseMode;
import org.isuper.telegram.models.User;
import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author Super Wang
 *
 */
public class TelegramClient implements Closeable {
	
	private static final Logger LOGGER = LogManager.getLogger(TelegramClient.class);
	
	private static final int MAX_RETRY = 20;
	
	private final AsyncHttpClient client;
	
	/**
	 * 
	 */
	public TelegramClient() {
		this(null, 1080);
	}

	/**
	 * @param proxyHostname
	 * 				Optional, unless you want to proxy your request, set the proxy hostname here
	 * @param proxyPort
	 * 				Optional, unless you want to proxy your request, set the proxy port here
	 */
	public TelegramClient(final String proxyHostname, final int proxyPort) {
		try {
			this.client = AsyncHttpClient.newInstance(proxyHostname, proxyPort);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * A simple method for testing your bot's auth token. Requires no parameters.
	 * 
	 * @param token
	 * 					The token of telegram API
	 * @return
	 * 					Returns basic information about the bot in form of a User object.
	 */
	public User getMe(String token) {
		Preconditions.notEmptyString(token, "Telegram token should be provided.");
		return this.getResourceRepeatly(User.class, token, "getMe", null);
	}

	/**
	 * @param token
	 * 					The token of telegram API
	 * @param chatID
	 * 					Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * @param text
	 * 					Text of the message to be sent
	 * @return 
	 * 					On success, the sent Message is returned.
	 */
	public Message sendMessage(String token, String chatID, String text) {
		return this.sendMessage(token, chatID, text, null);
	}

	/**
	 * @param token
	 * 					The token of telegram API
	 * @param chatID
	 * 					Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * @param text
	 * 					Text of the message to be sent
	 * @param replyTo
	 * 					If the message is a reply, ID of the original message
	 * @return 
	 * 					On success, the sent Message is returned.
	 */
	public Message sendMessage(String token, String chatID, String text, Long replyTo) {
		return this.sendMessage(token, chatID, text, null, false, false, replyTo);
	}

	/**
	 * @param token
	 * 					The token of telegram API
	 * @param chatID
	 * 					Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * @param text
	 * 					Text of the message to be sent
	 * @param parseMode
	 * 					Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
	 * @param disablePreview
	 * 					Disables link previews for links in this message or not
	 * @return 
	 * 					On success, the sent Message is returned.
	 */
	public Message sendMessage(String token, String chatID, String text, MessageParseMode parseMode, Boolean disablePreview) {
		return this.sendMessage(token, chatID, text, parseMode, disablePreview, null, null);
	}
	
	/**
	 * @param token
	 * 					The token of telegram API
	 * @param chatID
	 * 					Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * @param text
	 * 					Text of the message to be sent
	 * @param parseMode
	 * 					Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
	 * @param disablePreview
	 * 					Disables link previews for links in this message or not
	 * @param disableNotification
	 * 					Sends the message silently. iOS users will not receive a notification, Android users will receive a notification with no sound.
	 * @return 
	 * 					On success, the sent Message is returned.
	 */
	public Message sendMessage(String token, String chatID, String text, MessageParseMode parseMode, Boolean disablePreview, Boolean disableNotification) {
		return this.sendMessage(token, chatID, text, parseMode, disablePreview, disableNotification, null);
	}
	
	/**
	 * @param token
	 * 					The token of telegram API
	 * @param chatID
	 * 					Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * @param text
	 * 					Text of the message to be sent
	 * @param parseMode
	 * 					Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
	 * @param disablePreview
	 * 					Disables link previews for links in this message or not
	 * @param disableNotification
	 * 					Sends the message silently. iOS users will not receive a notification, Android users will receive a notification with no sound.
	 * @param replyTo
	 * 					If the message is a reply, ID of the original message
	 * @return 
	 * 					On success, the sent Message is returned.
	 */
	public Message sendMessage(String token, String chatID, String text, MessageParseMode parseMode, Boolean disablePreview, Boolean disableNotification, Long replyTo) {
		Preconditions.notEmptyString(token, "Telegram token should be provided.");
		if (Preconditions.isEmptyString(text)) {
			return null;
		}
		List<NameValuePair> items = new LinkedList<>();
		items.add(new BasicNameValuePair("chat_id", chatID));
		items.add(new BasicNameValuePair("text", "" + text));
		if (parseMode != null) {
			items.add(new BasicNameValuePair("parse_mode", parseMode.name()));
		}
		if (disablePreview != null) {
			items.add(new BasicNameValuePair("disable_web_page_preview", "" + disablePreview));
		}
		if (disableNotification != null) {
			items.add(new BasicNameValuePair("disable_notification", "" + disableNotification));
		}
		if (replyTo != null) {
			items.add(new BasicNameValuePair("reply_to_message_id", "" + replyTo));
		}
		return this.getResourceRepeatly(Message.class, token, "sendMessage", items);
	}

	/**
	 * Use this method to forward messages of any kind.
	 * 
	 * @param token
	 * 					The token of telegram API
	 * @param chatID
	 * 					Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * @param fromChatID
	 * 					Unique identifier for the chat where the original message was sent (or channel username in the format @channelusername)
	 * @param disableNotification
	 * 					Optional. Sends the message silently. iOS users will not receive a notification, Android users will receive a notification with no sound.
	 * @param messageID
	 * 					Unique message identifier
	 * @return
	 * 					On success, the sent Message is returned.
	 */
	public Message forwardMessage(String token, String chatID, String fromChatID, Boolean disableNotification, long messageID) {
		Preconditions.notEmptyString(token, "Telegram token should be provided.");
		List<NameValuePair> items = new LinkedList<>();
		items.add(new BasicNameValuePair("chat_id", chatID));
		items.add(new BasicNameValuePair("from_chat_id", fromChatID));
		items.add(new BasicNameValuePair("message_id", "" + messageID));
		if (disableNotification != null) {
			items.add(new BasicNameValuePair("disable_notification", disableNotification.toString()));
		}
		return this.getResourceRepeatly(Message.class, token, "forwardMessage", null);
	}

	/**
	 * Use this method to kick a user from a group or a supergroup.
	 * In the case of supergroups, the user will not be able to return to the group on their own using invite links, etc., unless unbanned first.
	 * The bot must be an administrator in the group for this to work.
	 * 
	 * Note: This will method only work if the ‘All Members Are Admins’ setting is off in the target group.
	 * Otherwise members may only be removed by the group's creator or by the member that added them.
	 * 
	 * @param token
	 * 					The token of telegram API
	 * @param chatID
	 * 					Unique identifier for the target group or username of the target supergroup (in the format @supergroupusername)
	 * @param userID
	 * 					Unique identifier of the target user
	 * @return
	 * 					Returns True on success.
	 */
	public boolean kickChatMember(String token, String chatID, long userID) {
		Preconditions.notEmptyString(token, "Telegram token should be provided.");
		Preconditions.notEmptyString(chatID, "Chat ID should be provided.");
		List<NameValuePair> items = new LinkedList<>();
		items.add(new BasicNameValuePair("chat_id", chatID));
		items.add(new BasicNameValuePair("user_id", "" + userID));
		return this.getResourceRepeatly(Boolean.class, token, "kickChatMember", items);
	}
	
	/**
	 * Use this method for your bot to leave a group, supergroup or channel.
	 * 
	 * @param token
	 * 					The token of telegram API
	 * @param chatID
	 * 					Unique identifier for the target group or username of the target supergroup (in the format @supergroupusername)
	 * @return
	 * 					Returns True on success.
	 */
	public boolean leaveChat(String token, String chatID) {
		Preconditions.notEmptyString(token, "Telegram token should be provided.");
		Preconditions.notEmptyString(chatID, "Chat ID should be provided.");
		List<NameValuePair> items = new LinkedList<>();
		items.add(new BasicNameValuePair("chat_id", chatID));
		return this.getResourceRepeatly(Boolean.class, token, "leaveChat", items);
	}
	
	/**
	 * Use this method to unban a previously kicked user in a supergroup.
	 * The user will not return to the group automatically, but will be able to join via link, etc.
	 * The bot must be an administrator in the group for this to work.
	 * 
	 * @param token
	 * 					The token of telegram API
	 * @param chatID
	 * 					Unique identifier for the target group or username of the target supergroup (in the format @supergroupusername)
	 * @param userID
	 * 					Unique identifier of the target user
	 * @return
	 * 					Returns True on success.
	 */
	public boolean unbanChatMember(String token, String chatID, long userID) {
		Preconditions.notEmptyString(token, "Telegram token should be provided.");
		Preconditions.notEmptyString(chatID, "Answered query ID should be provided.");
		List<NameValuePair> items = new LinkedList<>();
		items.add(new BasicNameValuePair("chat_id", chatID));
		items.add(new BasicNameValuePair("user_id", "" + userID));
		return this.getResourceRepeatly(Boolean.class, token, "unbanChatMember", items);
	}
	
	/**
	 * Use this method to get up to date information about the chat (current name of the user for one-on-one conversations, current username of a user, group or channel, etc.).
	 * 
	 * @param token
	 * 					The token of telegram API
	 * @param chatID
	 * 					Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
	 * @return
	 * 					Returns a Chat object on success.
	 */
	public Chat getChat(String token, String chatID) {
		Preconditions.notEmptyString(token, "Telegram token should be provided.");
		Preconditions.notEmptyString(chatID, "Chat ID should be provided.");
		List<NameValuePair> items = new LinkedList<>();
		items.add(new BasicNameValuePair("chat_id", chatID));
		return this.getResourceRepeatly(Chat.class, token, "getChat", items);
	}
	
	/**
	 * Use this method to get a list of administrators in a chat.
	 * 
	 * If the chat is a group or a supergroup and no administrators were appointed, only the creator will be returned.
	 * 
	 * @param token
	 * 					The token of telegram API
	 * @param chatID
	 * 					Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
	 * @return
	 * 					On success, returns an Array of ChatMember objects that contains information about all chat administrators except other bots.
	 */
	public List<ChatMember> getChatAdministrators(String token, String chatID) {
		Preconditions.notEmptyString(token, "Telegram token should be provided.");
		Preconditions.notEmptyString(chatID, "Chat ID should be provided.");
		List<NameValuePair> items = new LinkedList<>();
		items.add(new BasicNameValuePair("chat_id", chatID));
		return this.getResourcesRepeatly(ChatMember.class, token, "getChatAdministrators", items);
	}
	
	/**
	 * Use this method to get the number of members in a chat.
	 * 
	 * @param token
	 * 					The token of telegram API
	 * @param chatID
	 * 					Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
	 * @return
	 * 					Returns the number in Integer on success.
	 */
	public int getChatMembersCount(String token, String chatID) {
		Preconditions.notEmptyString(token, "Telegram token should be provided.");
		Preconditions.notEmptyString(chatID, "Chat ID should be provided.");
		List<NameValuePair> items = new LinkedList<>();
		items.add(new BasicNameValuePair("chat_id", chatID));
		return this.getResourceRepeatly(Integer.class, token, "getChatMembersCount", items);
	}
	
	/**
	 * Use this method to get information about a member of a chat.
	 * 
	 * @param token
	 * 					The token of telegram API
	 * @param chatID
	 * 					Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
	 * @param userID
	 * 					Unique identifier of the target user
	 * @return
	 * 					Returns a ChatMember object on success.
	 */
	public ChatMember getChatMember(String token, String chatID, long userID) {
		Preconditions.notEmptyString(token, "Telegram token should be provided.");
		Preconditions.notEmptyString(chatID, "Chat ID should be provided.");
		List<NameValuePair> items = new LinkedList<>();
		items.add(new BasicNameValuePair("chat_id", chatID));
		items.add(new BasicNameValuePair("user_id", "" + userID));
		return this.getResourceRepeatly(ChatMember.class, token, "getChatMember", items);
	}
	
	/**
	 * @param token
	 * 					The token of telegram API
	 * @param queryID
	 * 					Unique identifier for the answered query
	 * @param results
	 * 					A JSON-serialized array of results for the inline query
	 */
	public void answerInlineQuery(String token, String queryID, Collection<InlineQueryResult> results) {
		this.answerInlineQuery(token, queryID, results, null, null, null);
	}

	/**
	 * @param token
	 * 					The token of telegram API
	 * @param queryID
	 * 					Unique identifier for the answered query
	 * @param results
	 * 					A JSON-serialized array of results for the inline query
	 * @param cacheTime
	 * 					Optional. The maximum amount of time in seconds that the result of the inline query may be cached on the server.
	 * 					Defaults to 300.
	 * @param personal
	 * 					Optional. Pass True, if results may be cached on the server side only for the user that sent the query.
	 * 					By default, results may be returned to any user who sends the same query
	 * @param nextOffset
	 * 					Optional. Pass the offset that a client should send in the next query with the same text to receive more results.
	 * 					Pass an empty string if there are no more results or if you don‘t support pagination.
	 * 					Offset length can’t exceed 64 bytes.
	 */
	public void answerInlineQuery(String token, String queryID, Collection<InlineQueryResult> results, Integer cacheTime, Boolean personal, String nextOffset) {
		Preconditions.notEmptyString(token, "Telegram token should be provided.");
		Preconditions.notEmptyString(queryID, "Answered query ID should be provided.");
		List<NameValuePair> items = new LinkedList<>();
		items.add(new BasicNameValuePair("inline_query_id", queryID));
		try {
			if (results == null) {
				items.add(new BasicNameValuePair("results", TelegramUtils.getObjectMapper().writeValueAsString(Collections.emptyList())));
			} else {
				items.add(new BasicNameValuePair("results", TelegramUtils.getObjectMapper().writeValueAsString(results)));
			}
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage(), e);
			items.add(new BasicNameValuePair("results", "[]"));
		}
		if (cacheTime != null) {
			items.add(new BasicNameValuePair("cache_time", cacheTime.toString()));
		}
		if (personal != null) {
			items.add(new BasicNameValuePair("is_personal", personal.toString()));
		}
		if (!Preconditions.isEmptyString(nextOffset)) {
			items.add(new BasicNameValuePair("next_offset", nextOffset));
		}
		this.getResourceRepeatly(Boolean.class, token, "answerInlineQuery", items);
	}

	private <T> List<T> getResourcesRepeatly(Class<T> resultClass, String token, String endpoint, List<NameValuePair> items) {
		int retry = 0;
		int interval = 0;
		try {
			while (!Thread.interrupted()) {
				try {
					return this.getResources(resultClass, token, endpoint, items);
				} catch (UnrecoverableErrorResponseException e) {
					LOGGER.error(String.format("%d: %s", e.getError().getErrorCode(), e.getError().getDescription()));
					LOGGER.debug(String.format("%s: %s", e.getError().getDescription(), e.getRequestFormItems()));
					break;
				} catch (Exception ex) {
					if (ex instanceof RecoverableErrorResponseException) {
						RecoverableErrorResponseException e = (RecoverableErrorResponseException) ex;
						LOGGER.error(String.format("%d: %s", e.getError().getErrorCode(), e.getError().getDescription()));
						LOGGER.debug(String.format("%s: %s", e.getError().getDescription(), e.getRequestFormItems()));
					}
					if (interval > 17) {
						interval = 17;
					}
					if (retry < MAX_RETRY) {
						LOGGER.warn(String.format("Failed to get resource from telegram server because of %s, retry after %d second(s).", ex.getMessage(), interval++));
					}
					TimeUnit.SECONDS.sleep(interval);
				}
				if (retry >= MAX_RETRY) {
					LOGGER.warn(String.format("Maximium retry count %d reached, failed to get resource from telegram server.", retry));
					break;
				}
			}
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			LOGGER.info("Cancelling ...");
		}
		return Collections.emptyList();
	}
	
	private <T> T getResourceRepeatly(Class<T> resultClass, String token, String endpoint, List<NameValuePair> items) {
		int retry = 0;
		int interval = 0;
		try {
			while (!Thread.interrupted()) {
				try {
					return this.getResource(resultClass, token, endpoint, items);
				} catch (UnrecoverableErrorResponseException e) {
					LOGGER.error(String.format("%d: %s", e.getError().getErrorCode(), e.getError().getDescription()));
					LOGGER.debug(String.format("%s: %s", e.getError().getDescription(), e.getRequestFormItems()));
					break;
				} catch (Exception ex) {
					if (ex instanceof RecoverableErrorResponseException) {
						RecoverableErrorResponseException e = (RecoverableErrorResponseException) ex;
						LOGGER.error(String.format("%d: %s", e.getError().getErrorCode(), e.getError().getDescription()));
						LOGGER.debug(String.format("%s: %s", e.getError().getDescription(), e.getRequestFormItems()));
					}
					if (interval > 17) {
						interval = 17;
					}
					if (retry < MAX_RETRY) {
						LOGGER.warn(String.format("Failed to get resource from telegram server because of %s, retry after %d second(s).", ex.getMessage(), interval++));
					}
					TimeUnit.SECONDS.sleep(interval);
				}
				if (retry >= MAX_RETRY) {
					LOGGER.warn(String.format("Maximium retry count %d reached, failed to get resource from telegram server.", retry));
					break;
				}
			}
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			LOGGER.info("Cancelling ...");
		}
		return null;
	}
	
	private <T> List<T> getResources(Class<T> resultClass, String token, String endpoint, List<NameValuePair> items) throws IOException, ExecutionException, InterruptedException, RecoverableErrorResponseException, UnrecoverableErrorResponseException {
		JsonNode resultNode = this.send(token, endpoint, items);
		if (resultNode == null) {
			return Collections.emptyList();
		} 
		List<T> result = TelegramUtils.getObjectMapper().readValue(resultNode.traverse(), TelegramUtils.getObjectMapper().getTypeFactory().constructCollectionType(List.class, resultClass));
		if (result == null) {
			LOGGER.warn(String.format("Failed to parse valid list of %s from %s", resultClass, resultNode));
			return Collections.emptyList();
		}
		return result;
	}

	private <T> T getResource(Class<T> resultClass, String token, String endpoint, List<NameValuePair> items) throws IOException, ExecutionException, InterruptedException, RecoverableErrorResponseException, UnrecoverableErrorResponseException {
		JsonNode resultNode = this.send(token, endpoint, items);
		if (resultNode == null) {
			return null;
		} 
		T result =  TelegramUtils.getObjectMapper().treeToValue(resultNode, resultClass);
		if (result == null) {
			LOGGER.warn(String.format("Failed to parse valid object of %s from %s", resultClass, resultNode));
			return result;
		}
		return result;
	}
	
	private JsonNode send(String token, String endpoint, List<NameValuePair> items) throws IOException, ExecutionException, InterruptedException, RecoverableErrorResponseException, UnrecoverableErrorResponseException {
		Preconditions.notEmptyString(token, "Telegram token should be provided.");
		Preconditions.notEmptyString(endpoint, "Telegram endpoint should be provided.");
		
		HttpPost post = new HttpPost(String.format("https://api.telegram.org/bot%s/%s", token, endpoint));
		if (items != null && !items.isEmpty()) {
			post.setEntity(new UrlEncodedFormEntity(items, Charset.forName("UTF-8")));
		}
		LOGGER.trace(post.getRequestLine());
		
		HttpResponse resp = this.client.execute(post, null).get();
		StatusLine status = resp.getStatusLine();
		LOGGER.trace(resp.getStatusLine());
		
		HttpEntity entity = resp.getEntity();
		String contentType = entity.getContentType().getValue();
		LOGGER.trace(contentType);
		
		String content = EntityUtils.toString(entity);
		LOGGER.trace(content);
		if (Preconditions.isEmptyString(content)) {
			throw new BlankResponseException(items);
		}
		if (status.getStatusCode() == 400 || status.getStatusCode() == 403 || status.getStatusCode() == 409) {
			throw new UnrecoverableErrorResponseException(content, items);
		} else if (status.getStatusCode() != 200) {
			throw new RecoverableErrorResponseException(content, items);
		}
		JsonNode node = TelegramUtils.getObjectMapper().readTree(content);
		if (node == null) {
			throw new NotJsonResponseException(content, items);
		}
		if (!node.hasNonNull("ok") || (!node.hasNonNull("result") && !node.hasNonNull("error_code"))) {
			throw new InvalidJsonResponseException(content, items);
		}
		if (!node.get("ok").asBoolean() || node.hasNonNull("error_code")) {
			throw new RecoverableErrorResponseException(content, items);
		}
		return node.get("result");
	}

	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		this.client.close();
	}

}
