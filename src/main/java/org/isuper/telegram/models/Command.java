/**
 * 
 */
package org.isuper.telegram.models;

import org.isuper.common.utils.Preconditions;

/**
 * @author Super Wang
 *
 */
public class Command {

	public final String type;
	public final String toBot;
	public final String argument;
	
	/**
	 * @param type
	 * 				The type of command
	 * @param toBot
	 * 				The robot which send command to
	 * @param argument
	 * 				argument for command
	 */
	public Command(String type, String toBot, String argument) {
		if (Preconditions.isEmptyString(type)) {
			throw new IllegalArgumentException("Command type cannot be null or empty string!");
		}
		this.type = type.toLowerCase();
		this.toBot = toBot;
		this.argument = argument;
	}

}
