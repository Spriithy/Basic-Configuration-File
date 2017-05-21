package com.bcf.data;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import com.bcf.errors.NodeTypeError;

public class Group extends Node {

	private List<Atom>	atoms;
	private List<Group>	groups;
	
	public Group(String key) {
		super(key);
		atoms = new ArrayList<>();
		groups = new ArrayList<>();
	}

	public Atom setAtom(String key, Object value) {
		if (containsAtom(key))
			return getAtom(key).set(value);

		if (containsGroup(key))
			throw new NodeTypeError("key is already attributed to a Group (key='" + key + "')");

		if (key.indexOf('.') > 0)
			return setAtom(key.split("."), value);

		Atom atom = new Atom(key, value);
		atoms.add(atom);
		return atom;
	}

	private Atom setAtom(String[] key, Object value) {
		Group parent = this;
		for (int i = 0; i < key.length - 1; i++)
			parent = parent.getGroup(key[i]);
		return parent.setAtom(key[key.length - 1], value);
	}

	public Atom getAtom(String key) {
		if (key.indexOf('.') > 0)
			return getAtom(key.split("."));

		if (!containsAtom(key))
			return null;

		for (Atom a : atoms)
			if (a.getKey().equals(key))
				return a;

		throw new NoSuchElementException("Atom key not found '" + key + "'");
	}

	private Atom getAtom(String[] key) {
		Group parent = this;
		for (int i = 0; i < key.length - 1; i++)
			parent = parent.getGroup(key[i]);
		return parent.getAtom(key[key.length - 1]);
	}

	public Group addGroup(String key) {
		if (containsGroup(key))
			return getGroup(key);

		if (containsAtom(key))
			throw new NodeTypeError("key is already used for an Atom (key='" + key + "')");

		Group group = new Group(key);
		groups.add(group);
		return group;
	}

	public Group getGroup(String key) {
		if (key.indexOf('.') > 0) {
			String parent = key.split(".")[0];
			if (containsGroup(parent)) return getGroup(parent).getGroup(key.substring(1 + key.indexOf('.')));
			else throw new NodeTypeError("parent key is not a Group (key='" + parent + "')");
		}

		if (!containsGroup(key))
			return null;

		for (Group g : groups)
			if (g.getKey().equals(key))
				return g;

		throw new NoSuchElementException("Group key not found '" + key + "'");
	}

	public Node remove(String key) {
		Node node;
		
		if (containsAtom(key)) {
			node = getAtom(key);
			atoms.remove(node);
			return node;
		}
		
		if (containsGroup(key)) {
			node = getGroup(key);
			groups.remove(node);
			return node;
		}
		
		return null;
	}
	
	public Stream<String> keys() {
		List<String> keys = new ArrayList<>();
		atoms.forEach((a) -> keys.add(a.getKey()));
		groups.forEach((g) -> keys.add(g.getKey()));
		return keys.stream();
	}

	public boolean contains(String key) {
		return containsGroup(key) || containsAtom(key);
	}

	public boolean containsAtom(String key) {
		return atoms.stream().anyMatch((a) -> a.getKey().equals(key));
	}

	public boolean containsGroup(String key) {
		return groups.stream().anyMatch((g) -> g.getKey().equals(key));
	}

	public boolean isSet(String key) {
		return contains(key);
	}

	public String toString(String prefix) {
		String string = getKey() + ":\n";
		for (Atom a : atoms)
			string += prefix + "\t" + a.toString() + "\n";
		for (Group g : groups)
			string += prefix + "\t" + g.toString(prefix + "\t") + "\n";
		return string;
	}

	@Override
	public String toString() {
		String string = getKey() + ":\n";
		for (Atom a : atoms)
			string += "\t" + a.toString() + "\n";
		for (Group g : groups)
			string += "\t" + g.toString("\t") + "\n";
		return string;
	}
}
