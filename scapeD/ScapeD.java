package scapeD;

import javax.swing.JFrame;

public class ScapeD {
	public ScapeD() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Panel());
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setTitle("ScapeD");
	}

	public static void main(String[] args) {
		new ScapeD();
	}
}
