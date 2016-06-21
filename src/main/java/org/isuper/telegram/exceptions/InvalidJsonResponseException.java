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
public class InvalidJsonResponseException extends RecoverableErrorResponseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4282436892344563545L;

	/**
	 * @param rawContent
	 * 					The raw content of the response.
	 * @param requestFormItems
	 * 					The items sent in the request.
	 */
	public InvalidJsonResponseException(String rawContent, List<NameValuePair> requestFormItems) {
		super(new TelegramError(false, 500, String.format("Invalid json response: %s", rawContent)), requestFormItems);
	}

}
