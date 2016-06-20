/**
 * 
 */
package org.isuper.telegram.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TelegramBooleanResultResponse extends TelegramResultResponse<Boolean> {
	
	/**
	 * @param ok
	 * 					The status of the response.
	 * @param result
	 * 					The result.
	 */
	@JsonCreator
	public TelegramBooleanResultResponse(
			@JsonProperty("ok") boolean ok,
			@JsonProperty("result") boolean result) {
		super(ok, result);
	}

}
