/**
 * 
 */
package org.isuper.telegram.utils;

import org.isuper.common.utils.Preconditions;
import org.isuper.telegram.models.Command;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Super Wang
 *
 */
public class TelegramUtils {

	private static final JsonFactory JSON_FACTORY = new JsonFactory();
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(JSON_FACTORY);
	static {
		//		OBJECT_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
	}

	/**
	 * @return
	 * 			An instance of JsonFactory
	 */
	public static JsonFactory getJsonFactory() {
		return JSON_FACTORY;
	}

	/**
	 * @return
	 * 			An instance of ObjectMapper
	 */
	public static ObjectMapper getObjectMapper() {
		return OBJECT_MAPPER;
	}
	
	/**
	 * Parse bot ID from a full token string
	 * 
	 * @param token
	 * 				The token to parse
	 * @return
	 * 				Bot ID
	 */
	public static String parseBotIDFromToken(String token) {
		if (Preconditions.isEmptyString(token)) {
			throw new IllegalArgumentException("Cannot parse valid bot ID from null or empty token!");
		}
		return token.split(":", 2)[0];
	}
	
	/**
	 * @param text
	 * 				The text to parse command from
	 * @return
	 * 				An instance of command
	 */
	public static Command parseCommandFromText(String text) {
		if (Preconditions.isEmptyString(text) || !text.startsWith("/")) {
			return null;
		}
		String[] tmp = text.split(" ", 2);
		String cmdToBot = tmp[0];
		if (Preconditions.isEmptyString(cmdToBot)) {
			return null;
		}
		String argument = null;
		if (tmp.length > 1) {
			argument = tmp[1];
		}
		cmdToBot = cmdToBot.replaceFirst("/", "");
		if (Preconditions.isEmptyString(cmdToBot)) {
			return null;
		}
		tmp = cmdToBot.split("@", 2);
		String type = tmp[0];
		if (Preconditions.isEmptyString(type)) {
			return null;
		}
		String toBot = null;
		if (tmp.length > 1) {
			toBot = tmp[1];
		}
		return new Command(type, toBot, argument);
	}

}
