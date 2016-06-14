/**
 * 
 */
package org.isuper.telegram.models;

import org.isuper.common.utils.Preconditions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Super Wang
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InlineQueryResultArticle implements InlineQueryResult {
	
	private final String type = "article";
	private final String id;
	
	@JsonProperty("title")
	public final String title;
	@JsonProperty("input_message_content")
	public final InputMessageContent inputMessageContent;
	@JsonProperty("url")
	public final String url;
	@JsonProperty("hide_url")
	public final Boolean hideURL;
	@JsonProperty("description")
	public final String description;
	@JsonProperty("thumb_url")
	public final String thumbURL;
	@JsonProperty("thumb_width")
	public final Integer thumbWidth;
	@JsonProperty("thumb_height")
	public final Integer thumbHeight;

	/**
	 * @param id
	 * 				Unique identifier for this result, 1-64 Bytes
	 * @param title
	 * 				Title of the result
	 * @param inputMessageContent
	 * 				Content of the message to be sent
	 */
	public InlineQueryResultArticle(String id, String title, InputMessageContent inputMessageContent) {
		this(id, title, inputMessageContent, null, true, null);
	}

	/**
	 * @param id
	 * 				Unique identifier for this result, 1-64 Bytes
	 * @param title
	 * 				Title of the result
	 * @param inputMessageContent
	 * 				Content of the message to be sent
	 * @param url
	 * 				Optional. URL of the result
	 * @param hideURL
	 * 				Optional. Pass True, if you don't want the URL to be shown in the message
	 * @param description
	 * 				Optional. Short description of the result
	 */
	public InlineQueryResultArticle(String id, String title, InputMessageContent inputMessageContent, String url, boolean hideURL, String description) {
		this(id, title, inputMessageContent, url, hideURL, description, null, null, null);
	}

	/**
	 * @param id
	 * 				Unique identifier for this result, 1-64 Bytes
	 * @param title
	 * 				Title of the result
	 * @param inputMessageContent
	 * 				Content of the message to be sent
	 * @param url
	 * 				Optional. URL of the result
	 * @param hideURL
	 * 				Optional. Pass True, if you don't want the URL to be shown in the message
	 * @param description
	 * 				Optional. Short description of the result
	 * @param thumbURL
	 * 				Optional. Url of the thumbnail for the result
	 * @param thumbWidth
	 * 				Optional. Thumbnail width
	 * @param thumbHeight
	 * 				Optional. Thumbnail height
	 */
	public InlineQueryResultArticle(String id, String title, InputMessageContent inputMessageContent, String url, Boolean hideURL, String description, String thumbURL, Integer thumbWidth, Integer thumbHeight) {
		Preconditions.notEmptyString(id, "Articale ID should be provided.");
		this.id = id;
		Preconditions.notEmptyString(title, "Article title should be provided.");
		this.title = title;
		Preconditions.notNull(inputMessageContent, "Message content should be provided.");
		this.inputMessageContent = inputMessageContent;
		this.url = url;
		this.hideURL = hideURL;
		this.description = description;
		this.thumbURL = thumbURL;
		this.thumbWidth = thumbWidth;
		this.thumbHeight = thumbHeight;
	}

	/* (non-Javadoc)
	 * @see org.isuper.telegram.models.InlineQueryResult#getType()
	 */
	@JsonProperty("type")
	@Override
	public String getType() {
		return this.type;
	}

	/* (non-Javadoc)
	 * @see org.isuper.telegram.models.InlineQueryResult#getID()
	 */
	@JsonProperty("id")
	@Override
	public String getID() {
		return this.id;
	}

}
