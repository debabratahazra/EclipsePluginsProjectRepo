package tar.access;

import java.net.URL;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hello!");
		
		URL resource = Main.class.getResource("/R13_FT_ModelBank_1_WIN_13.0.tar");
		System.out.println(resource);
	}

}
