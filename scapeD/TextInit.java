package scapeD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextInit {
	static ArrayList<int[]> map1;

	public static void readMap() throws IOException {

		map1 = new ArrayList<int[]>();

		BufferedReader inputStream = null;

		try {
			inputStream = new BufferedReader(new FileReader(
					"src/ScapeD/res/MapG.txt"));

			String l;
			while ((l = inputStream.readLine()) != null) {
				String delims = "[ ]+";
				String[] tokens = l.split(delims);
				int[] e = new int[tokens.length];
				for (int i = 0; i < tokens.length; i++) {
					e[i] = Integer.parseInt(tokens[i]);
				}
				map1.add(e);
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	public static ArrayList<int[]> getmap1() {
		return map1;
	}

}
