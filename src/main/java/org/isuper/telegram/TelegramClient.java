/**
 * 
 */
package org.isuper.telegram;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
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
	
	private static final Logger LOGGER = LogManager.getLogger("telegram-client");
	
	private static final TelegramAsyncResponseHandler DEFAULT_ASYNC_RESPONSE_HANDLER = new TelegramAsyncResponseHandler();

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
		this.send(token, items);
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
		this.send(token, items);
	}

	private void send(String token, List<NameValuePair> items) {
		HttpPost post = new HttpPost("https://api.telegram.org/bot" + token + "/sendMessage");
		post.setEntity(new UrlEncodedFormEntity(items, Charset.forName("UTF-8")));
		LOGGER.trace(post.getRequestLine());
		try {
			this.client.execute(post, DEFAULT_ASYNC_RESPONSE_HANDLER).get();
		} catch (InterruptedException | ExecutionException e) {
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
	
	private static class TelegramAsyncResponseHandler implements FutureCallback<HttpResponse> {

		/* (non-Javadoc)
		 * @see org.apache.http.concurrent.FutureCallback#completed(java.lang.Object)
		 */
		@Override
		public void completed(HttpResponse resp) {
			StatusLine status = resp.getStatusLine();
			LOGGER.trace(resp.getStatusLine());
			
			HttpEntity entity = resp.getEntity();
			String contentType = entity.getContentType().getValue();
			LOGGER.trace(contentType);
			
			try {
				String content = EntityUtils.toString(entity);
				LOGGER.trace(content);
				if (status.getStatusCode() != 200) {
					LOGGER.warn(String.format("%d response received from server: %s", status.getStatusCode(), content));
				}
			} catch (ParseException | IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}

		/* (non-Javadoc)
		 * @see org.apache.http.concurrent.FutureCallback#failed(java.lang.Exception)
		 */
		@Override
		public void failed(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

		/* (non-Javadoc)
		 * @see org.apache.http.concurrent.FutureCallback#cancelled()
		 */
		@Override
		public void cancelled() {
			LOGGER.error("Request to telegram server has been canncelled.");
		}
		
	}

}
