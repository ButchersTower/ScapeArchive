package luggage;

import javax.swing.JFrame;

public class Luggage extends JFrame {
	public Luggage() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Panel());
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Luggage");
	}

	public static void main(String[] args) {
		new Luggage();
	}

}
