/**
 * 
 */
package org.isuper.telegram;

/**
 * @author Super Wang
 *
 */
public class MessageNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8609417838403629018L;

	/**
	 * @param message
	 */
	public MessageNotFoundException(String message) {
		super(message);
	}

}
