/**
 * 
 */
package org.isuper.telegram;

/**
 * @author Super Wang
 *
 */
public class BadRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8609417838403629018L;

	/**
	 * @param message
	 */
	public BadRequestException(String message) {
		super(message);
	}

}
