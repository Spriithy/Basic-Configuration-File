package com.bcf.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tokenizer {

	private List<String> lines;

	private List<Token> tokens;

	private int lcount;

	public Tokenizer(String file) {
		try (Stream<String> stream = Files.lines(Paths.get(file))) {
			lines = stream
					.filter(line -> !line.trim().startsWith("#"))
					.collect(Collectors.toList());
		} catch (IOException e) {
			error(0, "Couldn't read file");
		}
		tokens = new ArrayList<>();
	}

	public void tokenize() {
		lines.forEach(this::split);
		tokens.add(Token.EOF(lcount));
		tokens.forEach(System.out::println);
	}

	private void split(String line) {
		lcount++;

		if (line.trim().startsWith("#"))
			return;

		while (line.startsWith("\t")) {
			line = line.substring(1);
			tokens.add(Token.Tabulation(lcount));
		}

		String[] parts = line.split("\\s*:\\s*");
		if (parts.length == 1 && line.indexOf(':') > 0)
			tokens.add(Token.GroupKey(lcount, parts[0]));

		if (parts.length > 1) {
			tokens.add(Token.AtomKey(lcount, parts[0]));
			tokens.add(Token.Value(lcount, line.substring(1 + line.indexOf(':')).trim()));
		}
	}

	private static void error(int code, String message) {
		System.err.printf("Error T%03d: %s\n", code, message);
		System.exit(1);
	}

}
