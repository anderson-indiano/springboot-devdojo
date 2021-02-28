/**
 * 
 */
package com.devdojo.springboot.error;

/**
 * @author Anderson Indiano on 3 de fev. de 2021.
 *
 */
public class PropertyReferenceException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public PropertyReferenceException(String message) {
		super(message);
	}
}
