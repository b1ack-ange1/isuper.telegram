/**
 * Copyright 2014-2016 Super Wayne
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.isuper.telegram.models;

import org.isuper.common.utils.Preconditions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Super Wayne
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageEntity {
	
	@JsonProperty("type") 
	public final String type;
	@JsonProperty("offset")
	public final Integer offset;
	@JsonProperty("length")
	public final Integer length;
	@JsonProperty("url")
	public final String url;
	@JsonProperty("user")
	public final User user;
	
	/**
	 * @param type
	 * 					Type of the entity. Can be mention (@username), hashtag, bot_command, url, email,
	 * 					bold (bold text), italic (italic text), code (monowidth string), pre (monowidth block),
	 * 					text_link (for clickable text URLs), text_mention (for users without usernames)
	 * @param offset
	 * 					Offset in UTF-16 code units to the start of the entity.
	 * @param length
	 * 					Length of the entity in UTF-16 code units.
	 * @param url
	 * 					Optional. For “text_link” only, url that will be opened after user taps on the text.
	 * @param user
	 * 					Optional. For “text_mention” only, the mentioned user.
	 */
	@JsonCreator
	private MessageEntity(
			@JsonProperty("type") String type,
			@JsonProperty("offset") Integer offset,
			@JsonProperty("length") Integer length,
			@JsonProperty("url") String url,
			@JsonProperty("user") User user) {
		Preconditions.notEmptyString(type, "Message entity type should be provided.");
		this.type = type;
		Preconditions.notNull(offset, "Message entity offset should be provided.");
		this.offset = offset;
		Preconditions.notNull(length, "Message entity length should be provided.");
		this.length = length;
		this.url = url;
		this.user = user;
	}

}
