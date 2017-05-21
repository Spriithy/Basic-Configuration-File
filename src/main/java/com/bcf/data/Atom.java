package com.bcf.data;

import java.util.Objects;

public class Atom extends Node {

	private String data;

	public Atom(String key, Object value) {
		super(key);
		Objects.requireNonNull(value);
		requireValidKey(key);
		this.data = value.toString();
	}

	public Atom set(Object value) {
		Objects.requireNonNull(value);
		data = value.toString();
		return this;
	}

	public String asString() {
		return new String(data);
	}

	public int asInt() {
		return Integer.valueOf(data);
	}

	public long asLong() {
		return Long.valueOf(data);
	}

	public float asFloat() {
		return Float.valueOf(data);
	}

	public double asDouble() {
		return Double.valueOf(data);
	}

	public boolean asBoolean() {
		return Boolean.valueOf(data);
	}

	public static Atom valueOf(String key) {
		return null;
	}

	@Override
	public String toString() {
		return getKey() + ": " + data;
	}

	public static void requireValidKey(String key) {
		if (key.indexOf('.') > 0)
			throw new IllegalArgumentException();

		if (!key.chars().allMatch((c) -> Character.isJavaIdentifierPart(c)))
			throw new IllegalArgumentException();
	}

}
