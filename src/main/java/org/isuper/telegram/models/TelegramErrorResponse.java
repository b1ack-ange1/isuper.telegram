/**
 * 
 */
package org.isuper.telegram.models;

import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TelegramErrorResponse implements TelegramResponse {
	
	private final boolean ok;
	private final int errorCode;
	private final String description;
	
	/**
	 * @param ok
	 * 					The status of the response.
	 * @param errorCode
	 * 					The error code.
	 * @param description
	 * 					The description of the error.
	 */
	@JsonCreator
	public TelegramErrorResponse(
			@JsonProperty("ok") boolean ok,
			@JsonProperty("error_code") int errorCode,
			@JsonProperty("description") String description) {
		this.ok = ok;
		this.errorCode = errorCode;
		this.description = description;
	}

	/**
	 * @return the ok
	 */
	@Override
	public boolean isOk() {
		return this.ok;
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return this.errorCode;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
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
