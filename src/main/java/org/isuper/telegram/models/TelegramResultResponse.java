/**
 * 
 */
package org.isuper.telegram.models;

import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author Super Wang
 *
 */
public class TelegramResultResponse<T> implements TelegramResponse {
	
	private final boolean ok;
	private final T result;
	
	/**
	 * @param ok
	 * 					The status of the response.
	 * @param result
	 * 					The result.
	 */
	public TelegramResultResponse(boolean ok, T result) {
		this.ok = ok;
		this.result = result;
	}

	/**
	 * @return the ok
	 */
	@Override
	public boolean isOk() {
		return this.ok;
	}

	/**
	 * @return the result
	 */
	public T getResult() {
		return this.result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		try {
			return TelegramUtils.getObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "{}";
	}
	
}
