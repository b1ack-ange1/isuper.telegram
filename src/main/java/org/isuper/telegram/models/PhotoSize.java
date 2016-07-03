/**
 * 
 */
package org.isuper.telegram.models;

import org.isuper.common.utils.Preconditions;
import org.isuper.telegram.utils.TelegramUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This object represents one size of a photo or a file / sticker thumbnail.
 * 
 * @author Super Wang
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhotoSize {
	
	@JsonProperty("file_id")
	public final String fileID;
	@JsonProperty("width")
	public final int width;
	@JsonProperty("height")
	public final int height;
	@JsonProperty("file_size")
	public final int fileSize;
	
	/**
	 * @param fileID
	 * 					Unique identifier for this file
	 * @param width
	 * 					Photo width
	 * @param height
	 * 					Photo height
	 * @param fileSize
	 * 					Optional. File size
	 */
	@JsonCreator
	public PhotoSize(
			@JsonProperty("file_id") String fileID,
			@JsonProperty("width") int width,
			@JsonProperty("height") int height,
			@JsonProperty("file_size") Integer fileSize) {
		Preconditions.notEmptyString(fileID, "File ID should be provided.");
		this.fileID = fileID;
		this.width = width;
		this.height = height;
		this.fileSize = fileSize;
	}

	public String toJSON() {
		try {
			return TelegramUtils.getObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "{}";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.toJSON();
	}
	
}
