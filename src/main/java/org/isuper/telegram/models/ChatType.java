/**
 * 
 */
package org.isuper.telegram.models;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author Super Wang
 *
 */
public enum ChatType {
	
	PRIVATE,
	GROUP,
	SUPERGROUP,
	CHANNEL,
	;
	
	/**
	 * Parse ChatType from string
	 * 
	 * @param str
	 * 				The string to parse from
	 * @return
	 * 				An instance of ChatType
	 */
	@JsonCreator
	public static ChatType fromString(String str) {
		if (str == null) {
			throw new IllegalArgumentException("Cannot parse ChatType from null or empty string!");
		}
		return ChatType.valueOf(str.toUpperCase());
	}

}
