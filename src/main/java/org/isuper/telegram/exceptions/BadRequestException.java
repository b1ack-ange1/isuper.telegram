/**
 * 
 */
package org.isuper.telegram.exceptions;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.isuper.telegram.models.TelegramErrorResponse;
import org.isuper.telegram.utils.TelegramUtils;

/**
 * @author Super Wang
 *
 */
public class BadRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8609417838403629018L;

	private final String rawContent;
	private final TelegramErrorResponse error;
	private final List<NameValuePair> requestFormItems;

	/**
	 * @param rawContent
	 * 					The raw content of the response.
	 * @param requestFormItems
	 * 					The items sent in the request.
	 */
	public BadRequestException(String rawContent, List<NameValuePair> requestFormItems) {
		this.rawContent = rawContent;
		try {
			this.error = TelegramUtils.getObjectMapper().readValue(rawContent, TelegramErrorResponse.class);
		} catch (IOException e) {
			throw new IllegalArgumentException(String.format("Failed to parse TelegramErrorResponse from %s", rawContent));
		}
		this.requestFormItems = requestFormItems;
	}

	/**
	 * @return the rawContent
	 */
	public String getRawContent() {
		return this.rawContent;
	}

	/**
	 * @return the error
	 */
	public TelegramErrorResponse getError() {
		return this.error;
	}

	/**
	 * @return the items
	 */
	public List<NameValuePair> getRequestFormItems() {
		return this.requestFormItems;
	}
	
}
