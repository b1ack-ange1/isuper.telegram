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
import org.isuper.telegram.models.InlineQueryResult;
import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author Super Wang
 *
 */
public class TelegramClient implements Closeable {
	
	private static final Logger LOGGER = LogManager.getLogger("telegram-client");
	
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
	 * @param token
	 * 					The token of telegram API
	 * @param chatID
	 * 					Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * @param msg
	 * 					Text of the message to be sent
	 */
	public void sendMessage(String token, String chatID, String msg) {
		this.sendMessage(token, chatID, msg, false, false);
	}

	/**
	 * @param token
	 * 					The token of telegram API
	 * @param chatID
	 * 					Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * @param msg
	 * 					Text of the message to be sent
	 * @param useMarkdown
	 * 					Send Markdown or not
	 * @param disablePreview
	 * 					Disables link previews for links in this message or not
	 */
	public void sendMessage(String token, String chatID, String msg, boolean useMarkdown, boolean disablePreview) {
		List<NameValuePair> items = new LinkedList<>();
		items.add(new BasicNameValuePair("chat_id", "" + chatID));
		items.add(new BasicNameValuePair("text", "" + msg));
		if (useMarkdown) {
			items.add(new BasicNameValuePair("parse_mode", "Markdown"));
		}
		items.add(new BasicNameValuePair("disable_web_page_preview", "" + disablePreview));
		this.sendRepeatly(token, "sendMessage", items);
	}

	/**
	 * @param token
	 * 					The token of telegram API
	 * @param chatID
	 * 					Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * @param replyTo
	 * 					If the message is a reply, ID of the original message
	 * @param msg
	 * 					Text of the message to be sent
	 */
	public void replyMessage(String token, String chatID, long replyTo, String msg) {
		this.replyMessage(token, chatID, replyTo, msg, false, false);
	}

	/**
	 * @param token
	 * 					The token of telegram API
	 * @param chatID
	 * 					Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * @param replyTo
	 * 					If the message is a reply, ID of the original message
	 * @param msg
	 * 					Text of the message to be sent
	 * @param useMarkdown
	 * 					Send Markdown or not
	 * @param disablePreview
	 * 					Disables link previews for links in this message or not
	 */
	public void replyMessage(String token, String chatID, long replyTo, String msg, boolean useMarkdown, boolean disablePreview) {
		List<NameValuePair> items = new LinkedList<>();
		items.add(new BasicNameValuePair("chat_id", chatID));
		items.add(new BasicNameValuePair("text", "" + msg));
		if (useMarkdown) {
			items.add(new BasicNameValuePair("parse_mode", "Markdown"));
		}
		items.add(new BasicNameValuePair("disable_web_page_preview", "" + disablePreview));
		items.add(new BasicNameValuePair("reply_to_message_id", "" + replyTo));
		this.sendRepeatly(token, "sendMessage", items);
	}

	private void sendRepeatly(String token, String endpoint, List<NameValuePair> items) {
		int interval = 1;
		try {
			while (!Thread.interrupted()) {
				try {
					this.send(token, endpoint, items);
					break;
				} catch (BotRemovedException | MessageNotFoundException e) {
					LOGGER.error(e.getMessage());
					break;
				} catch (Exception e) {
					if (interval > 17) {
						interval = 17;
					}
					TimeUnit.SECONDS.sleep(interval);
					LOGGER.warn(String.format("Failed to send response back to telegram server because of %s, retry after %d second(s).", e.getMessage(), interval++));
				}
			}
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			LOGGER.info("Cancelling ...");
		}
	}

	private void send(String token, String endpoint, List<NameValuePair> items) throws IOException, ExecutionException, InterruptedException, BotRemovedException, MessageNotFoundException, TooManyRequestsException {
		HttpPost post = new HttpPost(String.format("https://api.telegram.org/bot%s/%s", token, endpoint));
		post.setEntity(new UrlEncodedFormEntity(items, Charset.forName("UTF-8")));
		LOGGER.trace(post.getRequestLine());
		
		HttpResponse resp = this.client.execute(post, null).get();
		StatusLine status = resp.getStatusLine();
		LOGGER.trace(resp.getStatusLine());
		
		HttpEntity entity = resp.getEntity();
		String contentType = entity.getContentType().getValue();
		LOGGER.trace(contentType);
		
		String content = EntityUtils.toString(entity);
		LOGGER.trace(content);
		if (status.getStatusCode() == 400) {
			throw new MessageNotFoundException(content);
		} else if (status.getStatusCode() == 403) {
			throw new BotRemovedException(content);
		} else if (status.getStatusCode() == 429) {
			throw new TooManyRequestsException(content);
		} else if (status.getStatusCode() != 200) {
			throw new IOException(String.format("%d response received from server: %s", status.getStatusCode(), content));
		}
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
		Preconditions.notEmptyString(queryID, "Answered query ID should be provided.");
		List<NameValuePair> items = new LinkedList<>();
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
		try {
			this.send(token, "answerInlineQuery", items);
		} catch (IOException | ExecutionException | InterruptedException | BotRemovedException | MessageNotFoundException | TooManyRequestsException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		this.client.close();
	}

}
