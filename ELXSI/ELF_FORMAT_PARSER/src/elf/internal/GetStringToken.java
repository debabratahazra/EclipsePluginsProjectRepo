/*******************************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: GetStringToken
 * @version: 1.0
 * Date: June 2009 
 * @author Santanu Guchhait
 * Description: This file defines the function and class
 * required to take the exact string value on the basis of a token 
 *******************************************************************************/


package elf.internal;


public class GetStringToken {

	char char_str[][];
	String temp_str = new String();
	int position = 0;

	
	/********************************************************************	
	 * Author         - Santanu Guchhait
	 * Description    - Constructor for the class GetStringToken.
	 *********************************************************************/
	public GetStringToken(String str)
	{
		temp_str = str;
	}

	
	/********************************************************************	
	 * Parameter      - Start position and End position
	 * Return type    - String type 
	 * Description    - Taking the string from start position to end position
	 *********************************************************************/
	private String myNextToken(int start_pos, int end_pos)
	{
		String temp = new String();
		int i = 0;
		char_str = new char [1][(end_pos-start_pos)+1];
		for (i = start_pos; i <= (end_pos-2); i++)
		{
			temp = temp + temp_str.charAt(i);
		}
		return (temp);
	}
	
	
	/********************************************************************	
	 * Parameter      - No parameter
	 * Return type    - String type 
	 * Description    - Taking the string one by one on the basis of a 
	 * 					particular token                  
	 *********************************************************************/
	public String myNextStringToken()
	{
		int i = position;
		int j = position;
		String temp_string = new String();
		for (; i < temp_str.length() && temp_str.charAt(i) != '\0' && temp_str.charAt(i) != '$'; i++)
		{}

		temp_string = myNextToken(j, i+1);
		if (position > temp_str.length())
		{
			position = i+1;
			return null;
		}
		else
		{
			position = i+1;
			return temp_string;
		}
	}
}
