package com.myfinal.exception;

public class ShipException extends Exception{
	public ShipException(String message)
	{
		super("ShipException-"+message);
	}
	
	public ShipException(String message, Throwable cause)
	{
		super("ShipException-"+message,cause);
	}
}
