package com.bcf.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tokenizer {

	private String file;
	private int line, col;

	private InputStreamReader in;

	private int ch, prev;

	public Token token;

	private boolean isKey;

	public Tokenizer(String file) {
		try {
			in = new FileReader(file);
		} catch (FileNotFoundException e) {
			error(1, "Couldn't open file '" + file + "'");
		}

		this.file = file;
		line = 1;
		col = 1;
		prev = '\n';
	}

	public TokenType read() {
		isKey = prev == '\n';

		int start = col;
		switch (ch) {
		case -1:
			token = Token.EOF(line, start);
			break;
		case ':':
			token = Token.Colon(line, start);
			break;
		case ' ':
			token = Token.WhiteSpace(line, start);
			break;
		case '\t':
			token = Token.Tabulation(line, start);
			break;
		case '\n':
		case '\r':
			token = Token.LineFeed(line, start);
			line++;
			col = 0;
			break;
		case '#':
			while (ch != '\n')
				next();
			prev = '\n';
			return read();
		default:
			String text = "";
			if (isKey && Character.isJavaIdentifierPart((char) ch)) {
				while (Character.isJavaIdentifierPart((char) ch)) {
					text += (char) ch;
					next();
				}

				if (ch != ':' && !Character.isWhitespace((char) ch))
					error(3, "error while reading '" + file + "' (line=" + line + ", col=" + (col - 1)
							+ ")\n\tUnexpected character after text : '" + ((char) ch) + "'");

				token = Token.Key(line, start, text);
			} else if (!isKey) {
				while (ch != '\n') {
					text += (char) ch;
					next();
				}

				token = Token.Value(line, start, text);
			} else {
				return read();
			}
			break; 
		}

		next();
		return token.type;

	}

	public Token get() {
		return token;
	}

	private boolean next() {
		col++;
		try {
			prev = ch;
			return ((ch = in.read()) == -1 ? false : true);
		} catch (IOException e) {
			error(2, "error while reading '" + file + "' (line=" + line + ", col=" + (col - 1) + ")");
		}
		return false;
	}

	private static void error(int code, String message) {
		System.err.printf("Error T%03d: %s\n", code, message);
		System.exit(1);
	}

}
