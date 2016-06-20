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
public class TelegramMessageResultResponse extends TelegramResultResponse<Message> {
	
	/**
	 * @param ok
	 * 					The status of the response.
	 * @param result
	 * 					The result.
	 */
	@JsonCreator
	public TelegramMessageResultResponse(
			@JsonProperty("ok") boolean ok,
			@JsonProperty("result") Message result) {
		super(ok, result);
	}

}
