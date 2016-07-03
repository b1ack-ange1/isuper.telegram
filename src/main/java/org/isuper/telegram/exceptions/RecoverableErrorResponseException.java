/**
 * 
 */
package org.isuper.telegram.exceptions;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.isuper.common.utils.Preconditions;
import org.isuper.telegram.models.TelegramError;
import org.isuper.telegram.utils.TelegramUtils;

/**
 * @author Super Wang
 *
 */
public class RecoverableErrorResponseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6826632520034414214L;
	
	private final TelegramError error;
	private final List<NameValuePair> requestFormItems;

	/**
	 * @param rawContent
	 * 					The raw content of the response.
	 * @param requestFormItems
	 * 					The items sent in the request.
	 */
	public RecoverableErrorResponseException(String rawContent, List<NameValuePair> requestFormItems) {
		try {
			this.error = TelegramUtils.getObjectMapper().readValue(rawContent, TelegramError.class);
		} catch (IOException e) {
			throw new IllegalArgumentException(String.format("Failed to parse TelegramErrorResponse from %s", rawContent));
		}
		this.requestFormItems = requestFormItems;
	}

	/**
	 * @param error
	 * 					The detailed error of the response.
	 * @param requestFormItems
	 * 					The items sent in the request.
	 */
	public RecoverableErrorResponseException(TelegramError error, List<NameValuePair> requestFormItems) {
		Preconditions.notNull(error, "Error should be provided.");
		this.error = error;
		this.requestFormItems = requestFormItems;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return String.format("%d: %s", getError().getErrorCode(), getError().getDescription());
	}

	/**
	 * @return the error
	 */
	public TelegramError getError() {
		return this.error;
	}

	/**
	 * @return the items
	 */
	public List<NameValuePair> getRequestFormItems() {
		return this.requestFormItems;
	}
	
}
