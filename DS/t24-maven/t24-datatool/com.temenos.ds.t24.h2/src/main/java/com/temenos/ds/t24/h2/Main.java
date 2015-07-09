package com.temenos.ds.t24.h2;


public class Main {

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("USAGE: " + Main.class.getName() + " {h2DirectoryName} {port}");
			return;
		}
		new DB(args[0], Integer.parseInt(args[1])).start();
	}
}
