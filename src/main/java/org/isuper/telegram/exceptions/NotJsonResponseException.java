/**
 * 
 */
package org.isuper.telegram.exceptions;

import java.util.List;

import org.apache.http.NameValuePair;
import org.isuper.telegram.models.TelegramError;

/**
 * @author Super Wang
 *
 */
public class NotJsonResponseException extends RecoverableErrorResponseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5813779808855724381L;

	/**
	 * @param rawContent
	 * 					The raw content of the response.
	 * @param requestFormItems
	 * 					The items sent in the request.
	 */
	public NotJsonResponseException(String rawContent, List<NameValuePair> requestFormItems) {
		super(new TelegramError(false, 500, String.format("Response content not json string: %s", rawContent)), requestFormItems);
	}

}
