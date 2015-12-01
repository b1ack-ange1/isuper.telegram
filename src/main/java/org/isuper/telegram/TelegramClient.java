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
	 * @param useMarkdown
	 * 					Parse the message with markdown or not
	 * @param disablePreview
	 * 					Disable web page preview or not
	 * @param chatIDs
	 * 					Multiple chat IDs
	 * @param msg
	 * 					The content of message
	 */
	public void sendMessageToMultipleTelegramChats(String token, boolean useMarkdown, boolean disablePreview, Long[] chatIDs, String msg) {
		if (chatIDs == null || chatIDs.length < 1) {
			return;
		}
		for (Long chatID : chatIDs) {
			sendMessageToSingleTelegramChat(token, useMarkdown, disablePreview, chatID, msg);
		}
	}
	
	/**
	 * @param token
	 * 					The token of telegram API
	 * @param useMarkdown
	 * 					Parse the message with markdown or not
	 * @param disablePreview
	 * 					Disable web page preview or not
	 * @param chatID
	 * 					The chat ID to send message into
	 * @param msg
	 * 					The content of message
	 */
	public void sendMessageToSingleTelegramChat(String token, boolean useMarkdown, boolean disablePreview, Long chatID, String msg) {
		List<NameValuePair> items = new LinkedList<>();
		items.add(new BasicNameValuePair("chat_id", "" + chatID));
		items.add(new BasicNameValuePair("text", "" + msg));
		if (useMarkdown) {
			items.add(new BasicNameValuePair("parse_mode", "Markdown"));
		}
		items.add(new BasicNameValuePair("disable_web_page_preview", "" + disablePreview));
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
