package com.myfinal.exception;

public class PayInfoException extends Exception{
	public PayInfoException(String message)
	{
		super("PayInfoException-"+message);
	}
	
	public PayInfoException(String message, Throwable cause)
	{
		super("PayInfoException-"+message,cause);
	}

}
