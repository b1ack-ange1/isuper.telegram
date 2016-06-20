/**
 * 
 */
package org.isuper.telegram.exceptions;

import java.io.IOException;

import org.isuper.telegram.models.TelegramErrorResponse;
import org.isuper.telegram.utils.TelegramUtils;

/**
 * @author Super Wang
 *
 */
public class BotRemovedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7318177813215482061L;
	
	private final String rawContent;
	private final TelegramErrorResponse error;
	
	/**
	 * @param rawContent
	 * 					The raw content of the response.
	 */
	public BotRemovedException(String rawContent) {
		this.rawContent = rawContent;
		try {
			this.error = TelegramUtils.getObjectMapper().readValue(rawContent, TelegramErrorResponse.class);
		} catch (IOException e) {
			throw new IllegalArgumentException(String.format("Failed to parse TelegramErrorResponse from %s", rawContent));
		}
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

}
