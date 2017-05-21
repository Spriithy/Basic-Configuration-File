package com.bcf;

import com.bcf.data.Group;

public class ParserTest {

	public static void main(String[] args) {
		Group root = new Group("root");
		root.setAtom("item1", 10);
		root.addGroup("item2").setAtom("item3", true);
		System.out.println(root);
		System.out.println(root.getAtom("item2.item3"));
	}

}
