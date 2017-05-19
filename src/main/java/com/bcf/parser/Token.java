package com.bcf.parser;

public class Token {

	public int line, col;
	public String text;
	public TokenType type;

	private Token(int line, int col, TokenType type, String text) {
		this.line = line;
		this.col = col;
		this.text = text;
		this.type = type;
	}

	public static Token Key(int line, int col, String key) {
		return new Token(line, col, TokenType.KEY, key);
	}

	public static Token Colon(int line, int col) {
		return new Token(line, col, TokenType.COLON, ":");
	}

	public static Token Value(int line, int col, String value) {
		return new Token(line, col, TokenType.VALUE, value);
	}

	public static Token WhiteSpace(int line, int col) {
		return new Token(line, col, TokenType.WHITE_SPACE, " ");
	}

	public static Token Tabulation(int line, int col) {
		return new Token(line, col, TokenType.TABULATION, "\\t");
	}

	public static Token LineFeed(int line, int col) {
		return new Token(line, col, TokenType.LINE_FEED, "\\n");
	}

	public static Token Invalid(int line, int col, String text) {
		return new Token(line, col, TokenType.INVALID, text);
	}

	public static Token EOF(int line, int col) {
		return new Token(line, col, TokenType.EOF, "");
	}

	public String toString() {
		return String.format("%d:%d: (%s, '%s')", line, col, type.name(), text);
	}

}
