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
public class BlankResponseException extends UnrecoverableErrorResponseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7693347878667649798L;

	/**
	 * @param requestFormItems
	 * 					The items sent in the request.
	 */
	public BlankResponseException(List<NameValuePair> requestFormItems) {
		super(new TelegramError(false, 500, "BLANK_RESPONSE_RECEIVED"), requestFormItems);
	}

}
