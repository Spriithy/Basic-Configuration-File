package com.bcf;

import com.bcf.parser.TokenType;
import com.bcf.parser.Tokenizer;

public class ParserTest {

	public static void main(String[] args) {
		Tokenizer tokenizer = new Tokenizer("test/config.bcf");
		
		while (tokenizer.read() != TokenType.EOF)
			System.out.println(tokenizer.get());
	}
	
}
