/**
 * 
 */
package org.isuper.telegram;

/**
 * @author Super Wang
 *
 */
public class TooManyRequestsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8609417838403629018L;

	/**
	 * @param message
	 */
	public TooManyRequestsException(String message) {
		super(message);
	}

}
