/**
 * 
 */
package org.isuper.telegram;

import java.util.List;

import org.apache.http.NameValuePair;

/**
 * @author Super Wang
 *
 */
public class BadRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8609417838403629018L;
	
	private final List<NameValuePair> items;

	/**
	 * @param message
	 */
	public BadRequestException(String message, List<NameValuePair> items) {
		super(message);
		this.items = items;
	}

	/**
	 * @return the items
	 */
	public List<NameValuePair> getRequestFormItems() {
		return this.items;
	}
	
}
