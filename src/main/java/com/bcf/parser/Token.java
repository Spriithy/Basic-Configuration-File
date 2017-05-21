package com.bcf.parser;

public class Token {

	public int line;
	public String text;
	public TokenType type;

	private Token(int line, TokenType type, String text) {
		this.line = line;
		this.text = text;
		this.type = type;
	}

	public static Token AtomKey(int line, String key) {
		return new Token(line, TokenType.ATOM_KEY, key);
	}
	
	public static Token GroupKey(int line, String key) {
		return new Token(line, TokenType.GROUP_KEY, key);
	}

	public static Token Value(int line, String value) {
		return new Token(line, TokenType.VALUE, value);
	}

	public static Token Tabulation(int line) {
		return new Token(line, TokenType.TABULATION, "\\t");
	}

	public static Token LineFeed(int line) {
		return new Token(line, TokenType.LINE_FEED, "\\n");
	}

	public static Token EOF(int line) {
		return new Token(line, TokenType.EOF, "");
	}

	public String toString() {
		return String.format("%d: (%s, '%s')", line, type.name(), text);
	}

}
