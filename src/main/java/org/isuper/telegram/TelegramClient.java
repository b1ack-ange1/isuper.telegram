/**
 * 
 */
package org.isuper.telegram;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
import org.isuper.httpclient.AsyncHttpClient;

/**
 * @author Super Wang
 *
 */
public class TelegramClient implements Closeable {
	
	private static final Logger LOG = LogManager.getLogger("telegram-client");
	
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
	 * @param chatIDs
	 * 					Multiple chat IDs
	 * @param msg
	 * 					The content of message
	 * @param useMarkdown
	 * 					Parse the message with markdown or not
	 * @param disablePreview
	 * 					Disable web page preview or not
	 */
	public void sendMessageToMultipleTelegramChats(String token, Long[] chatIDs, String msg, boolean useMarkdown, boolean disablePreview) {
		if (chatIDs == null || chatIDs.length < 1) {
			return;
		}
		for (long chatID : chatIDs) {
			sendMessageToSingleTelegramChat(token, chatID, msg, useMarkdown, disablePreview);
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
	public void sendMessageToSingleTelegramChat(String token, long chatID, String msg) {
		this.sendMessageToSingleTelegramChat(token, chatID, msg, false, false);
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
	public void sendMessageToSingleTelegramChat(String token, long chatID, String msg, boolean useMarkdown, boolean disablePreview) {
		List<NameValuePair> items = new LinkedList<>();
		items.add(new BasicNameValuePair("chat_id", "" + chatID));
		items.add(new BasicNameValuePair("text", "" + msg));
		if (useMarkdown) {
			items.add(new BasicNameValuePair("parse_mode", "Markdown"));
		}
		items.add(new BasicNameValuePair("disable_web_page_preview", "" + disablePreview));
		this.sendMessage(token, items);
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
	public void sendReplyMessage(String token, Long chatID, long replyTo, String msg) {
		this.sendReplyMessage(token, chatID.toString(), replyTo, msg, false, false);
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
	public void sendReplyMessage(String token, String chatID, long replyTo, String msg) {
		this.sendReplyMessage(token, chatID, replyTo, msg, false, false);
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
	public void sendReplyMessage(String token, String chatID, long replyTo, String msg, boolean useMarkdown, boolean disablePreview) {
		List<NameValuePair> items = new LinkedList<>();
		items.add(new BasicNameValuePair("chat_id", chatID));
		items.add(new BasicNameValuePair("text", "" + msg));
		if (useMarkdown) {
			items.add(new BasicNameValuePair("parse_mode", "Markdown"));
		}
		items.add(new BasicNameValuePair("disable_web_page_preview", "" + disablePreview));
		items.add(new BasicNameValuePair("reply_to_message_id", "" + replyTo));
		this.sendMessage(token, items);
	}

	private void sendMessage(String token, List<NameValuePair> items) {
		try {
			HttpPost post = new HttpPost("https://api.telegram.org/bot" + token + "/sendMessage");
			post.setEntity(new UrlEncodedFormEntity(items, "UTF-8"));
			
			LOG.trace(post.getRequestLine());

			Future<HttpResponse> future = this.client.execute(post, null);
			HttpResponse resp = future.get();
			
			StatusLine status = resp.getStatusLine();
			HttpEntity entity = resp.getEntity();
			String contentType = entity.getContentType().getValue();
			String content = EntityUtils.toString(entity);
			
			LOG.trace(resp.getStatusLine());
			LOG.trace(contentType);
			LOG.trace(content);
			
			if (status.getStatusCode() != 200) {
				throw new IOException(String.format("%d response received from server: %s", status.getStatusCode(), content));
			}
		} catch (InterruptedException | ExecutionException | IllegalStateException | IOException e) {
			LOG.error(e.getLocalizedMessage(), e);
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
