package com.example.ecommerce.books.utility;

import org.slf4j.LoggerFactory;

public class Tracer 
{	
	public static void trace(Class<?> clazz, String m, String t)
	{
		LoggerFactory.getLogger(clazz).info( m + "-> " + t );
	}
	
	public static void debug(Class<?> clazz, String m, String t)
	{
		LoggerFactory.getLogger(clazz).debug( m + "-> " + t );
	}
	
	public static void error(Class<?> clazz, String m, String t)
	{
		LoggerFactory.getLogger(clazz).error( m + "-> " + t );
	}
	
	public static void warn(Class<?> clazz, String m, String t)
	{
		LoggerFactory.getLogger(clazz).warn( m + "-> " + t );
	}
}
