/**
 * 
 */
package org.isuper.telegram.models;

import java.io.Serializable;

import org.isuper.common.utils.Preconditions;

/**
 * @author Super Wang
 *
 */
public class Command implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1956005239265903337L;
	
	public final String type;
	public final String toBot;
	public final String argument;
	
	/**
	 * @param type
	 * @param toBot
	 * @param argument
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
