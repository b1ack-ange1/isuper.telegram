/**
 * 
 */
package org.isuper.telegram.models;

import org.isuper.common.utils.Preconditions;

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
public class PhotoSize {
	
	public final String fileID;
	public final int width;
	public final int height;
	public final Integer fileSize;
	
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

}
