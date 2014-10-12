package scapeD;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable, KeyListener,
		MouseListener {

	// I should make a chat bar at the bottom, like a console.
	// first make it so you print to it, then have it so you can type to it.

	// Character can diagonally walk off top of screen.

	// If button above inventory pressed the drwGm for g3 stops drawing the invs
	// and draws stats instead because of an if-else-if statement.

	// Have a special in game. For one day, 24 hours. every time a boss is
	// killed
	// something happens. Or every time a mob is given spider tongues he
	// increases
	// in size by .05% or sumptin. The server with the largest mob has the mob
	// then go on a world wide rampage and drop epic loots

	// When moving daig. the player can walk into the corner of blocks.
	// But that wont matter when i install A* pathfinding.
	static int imgH = 32;

	static int width = 800;
	static int height = 600;

	// # of horizontal and vertical blocks, used for camera control.
	static int horBlocks;
	static int verBlocks;

	static int w1 = 640;
	static int h1 = 448;

	int w2 = 800;
	int h2 = 152;

	int w3 = 160;
	int h3 = 450;

	Thread thread;

	Image image1;
	static Graphics g1;

	Image image2;
	Graphics g2;

	Image image3;
	Graphics g3;

	// The camera is at the top of the screen
	static boolean yTop = false;
	static boolean xTop = false;
	// Bottom
	static boolean yBot = false;
	static boolean xBot = false;

	// Shtuff
	static int[] dee;
	static int drwX = 0;
	static int drwY = 0;

	// map arraylist
	static ArrayList<int[]> map;

	// Vars for gLoop Below
	public int tps = 20;
	public int milps = 1000 / tps;
	long lastTick = 0;
	int sleepTime = 0;
	long lastSec = 0;
	int ticks = 0;
	static long startTime;
	long runTime;
	private long nextTick = 0;
	private boolean running = false;

	// Vars for gLoop Above

	// Class declarations
	Player play;

	public Panel() {
		super();

		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public void run() {
		image1 = new BufferedImage(1744, 1168, BufferedImage.TYPE_INT_RGB);
		g1 = (Graphics2D) image1.getGraphics();
		this.setSize(new Dimension(w1, h1));

		image2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);
		g2 = (Graphics2D) image2.getGraphics();
		this.setSize(new Dimension(w2, h2));

		image3 = new BufferedImage(w3, h3, BufferedImage.TYPE_INT_RGB);
		g3 = (Graphics2D) image3.getGraphics();
		this.setSize(new Dimension(w3, h3));

		gStart();
	}

	/**
	 * Methods go below here.
	 * 
	 */

	public void gStart() {
		try {
			TextInit.readMap();
		} catch (IOException e) {
		}
		map = new ArrayList<int[]>();
		map = TextInit.getmap1();

		ImgLoad.imageInit();

		play = new Player();

		addKeyListener(this);
		addMouseListener(this);

		horBlocks = map.get(1).length;
		verBlocks = map.size();

		running = true;
		gLoop();
	}

	static int cmbI = 0;

	public void gLoop() {
		while (running) {
			// Do the think you want the gloop to do below here

			drawMap(g1);

			play.updatePlayer();

			play.drawPlayer(g1);

			Npc.updateNpc(g1);

			MineNode.updateNpc(g1);

			if (cmbI > 0) {
				for (int i = 0; i < dee.length; i++) {
					g1.drawImage(ImgLoad.txtMc[dee[i]], drwX + (32 / 2) + 200
							- 8 + (i * 8), drwY + (32 / 2) + 200, null);
				}
				cmbI -= 1;
			}

			cHair();

			botBar();

			int[] d;
			d = new int[3];
			d[0] = 0;

			// And above here.
			drwGm();

			ticks++;
			// Runs once a second and keeps track of ticks;
			// 1000 ms since last output
			if (timer() - lastSec > 1000) {
				if (ticks < 19 || ticks > 21) {
					if (timer() - startTime < 2000) {
						System.out.println("Ticks this second: " + ticks);

						System.out.println("timer(): " + timer());
						System.out.println("nextTick: " + nextTick);
					}
				}

				ticks = 0;
				lastSec = (System.currentTimeMillis() - startTime);
			}

			// Used to protect the game from falling beind.
			if (nextTick < timer()) {
				nextTick = timer() + milps;
			}

			// Limits the ticks per second
			if (timer() - nextTick < 0) {

				sleepTime = (int) (nextTick - timer());

				if (sleepTime > 0) {
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
					}
				}

				nextTick += milps;
			}
		}
	}

	public void drawMap(Graphics g) {
		for (int z = 0; z < map.size(); z++) {
			for (int x = 0; x < map.get(z).length; x++) {
				for (int hg = 0; hg < ImgLoad.mapAr.length; hg++) {
					if (map.get(z)[x] == hg + 1) {
						if (hg + 1 == 7 || hg + 1 == 6) {
							g.drawImage(ImgLoad.mapAr[0], (x * imgH),
									(z * imgH), null);
						}
						g.drawImage(ImgLoad.mapAr[hg], (x * imgH), (z * imgH),
								null);
					}
				}
			}
		}
	}

	public static int getMapVar(int y, int x) {
		// System.out.println("lay: " + lan.get(0).length);
		// System.out.println("lan: " + lan.size());
		try {
			return map.get(y)[x];
		} catch (Exception ex) {
			return -1;
		}
	}

	public static void changeMap(int y, int x, int z) {
		map.get(y)[x] = z;
	}

	public static void npCircle() {
		g1.drawOval((int) Npc.xx + 16 - 112 + 200,
				(int) Npc.yy + 16 - 112 + 200, 224, 224);
	}

	public void cHair() {
		for (int i = 0; i < 1; i++)
			if (play.tar == Npc.num) {
				g1.drawImage(ImgLoad.small[0], (int) Npc.xx, (int) Npc.yy, null);
			}
	}

	public static void drwNum(int num) {
		// Find middle of char to draw numbers centered around that
		int[] a = converter(Integer.toString(num));

		int b = (a.length * 6) / 2;
		int c = 16 - b;

		for (int i = 0; i < a.length; i++) {
			g1.drawImage(ImgLoad.txtMc[a[i]], (int) Npc.xx + c + (i * 6) + 200,
					(int) Npc.yy - 8 + 200, null);
		}
	}

	ArrayList<String> textBox;

	public void botBar() {

		textBox = new ArrayList<String>();
		textBox.add("first line");
		textBox.add("second line");
		textBox.add("third line");
		textBox.add("fourth line");
		textBox.add("fifth line");

		/*
		 * Graphics 1 This is text box graphics
		 */

		g2.setColor(Color.CYAN);
		g2.fillRect(0, 0, w1, h1);
		/*
		 * // System.out.println("Text: " + textBox.get(textBox.size() - 1));
		 * for (int d = 0; d < textBox.size(); d++) { int yl = 50 - (d * 10);
		 * System.out.println("yl: " + yl + "\td: " + d); txtBox(g1, 120, 60, 1,
		 * 10, yl, textBox.get(textBox.size() - d - 1)); }
		 */
		// The text boxes are overlapping eachother? so that is why they are not
		// working?

		txtBox(g2, 120, 60, 1, 10, 50, textBox.get(textBox.size() - 1));
		txtBox(g2, 120, 60, 1, 10, 40, textBox.get(textBox.size() - 2));
		// need to draw bottom up to stop overlapping.
		// What i really need to do is stop the text boxes from having a
		// backdrop that isn't alpha.

		/*
		 * Graphics 3
		 */

		// Inventory drawing
		g3.setColor(Color.GRAY);
		g3.fillRect(10, 10, 100, 100);
		g3.drawImage(ImgLoad.small[1], 0, 0, null);

		// draws items

		for (int i = 0; i < play.inv1.length; i++) {
			if (play.inv1[i][0] != 0) {
				int xVal = 7 + (i % 5 * 31);
				int yVal = 233 + ((((i - (i % 5))) / 5) * 31);
				g3.drawImage(ImgLoad.small[2], xVal, yVal, null);
				// Draws numbers for things.
				if (play.inv1[i][1] > 1) {
					int[] b = converter(Integer.toString(play.inv1[i][1]));
					for (int c = 0; c < b.length; c++) {
						g3.drawImage(ImgLoad.txtMc[b[c]], xVal - 2, yVal - 4,
								null);
					}
				}
			}
		}

		// Draws text
		int[] gi = converter(Integer.toString(play.tar));
		// Draws st
		for (int i = 0; i < gi.length; i++) {
			g3.drawImage(ImgLoad.txtMc[gi[i]], 16 + (i * 6), 8, null);
		}

		gi = converter(Boolean.toString(play.qP));
		// Draws st
		for (int i = 0; i < gi.length; i++) {
			g3.drawImage(ImgLoad.txtMc[gi[i]], 16 + (i * 6), 16, null);
		}

	}

	public static void cmbt(int x, int y, int w, int h, int dmg, int deviation) {

		drwX = x;
		drwY = y;
		dee = converter(Integer.toString(dmg));
		for (int i = 0; i < dee.length; i++) {
			g1.drawImage(ImgLoad.txtMc[dee[i]], x + (w / 2), y + (height / 2),
					null);
		}
		cmbI = 20;
	}

	int nesRows;
	boolean ovr = false;

	public void txtBox(Graphics g, int wi, int hi, int font, int xl, int yl,
			String st) {
		int twi = 0, thi = 0;
		if (font == 0) {
			twi = 12;
			thi = 16;
		}
		if (font == 1) {
			twi = 6;
			thi = 8;
		}
		int zz = 0;
		// Draws the outline of the text box
		g.setColor(Color.CYAN);
		g.fillRect(xl, yl, wi, hi);

		int[] gh = converter(st);
		// How many letters can go in a row.
		int aa = (wi - (wi % twi)) / twi;
		// System.out.println("cols: " + aa);
		// finds how many rows there CAN be.
		int ab = (hi - (hi % thi)) / thi;
		// System.out.println("rows: " + ab);

		if (gh.length % aa != 0) {
			nesRows = ((gh.length - (gh.length % aa)) / aa) + 1;
		} else {
			nesRows = (gh.length - (gh.length % aa)) / aa;
		}

		// If it can be drawn in one line
		if (gh.length <= aa) {
			if (font == 0) {
				for (int i = 0; i < gh.length; i++) {
					// g.drawImage(ImgLoad.txtAr[gh[i]], xl + (i * twi), yl,
					// null);
				}
			} else if (font == 1) {
				for (int i = 0; i < gh.length; i++) {
					g.drawImage(ImgLoad.txtMc[gh[i]], xl + (i * twi), yl, null);
				}
			}
		} else {
			// To long for first row
			// Draws the possiable text.

			// Instead of ab it should be rows there NEEDS to be
			for (int ia = 0; ia < nesRows - 1; ia++) {
				// Makes text isnt displayed passed the bottom of the box
				if (!(ia >= ab)) {
					for (int ib = 0; ib < aa; ib++) {
						if (font == 0) {
							// g.drawImage(ImgLoad.txtAr[gh[ib + (ia * aa)]], xl
							// + (ib * twi), yl + (ia * thi), null);
						} else if (font == 1) {
							g.drawImage(ImgLoad.txtMc[gh[ib + (ia * aa)]], xl
									+ (ib * twi), yl + (ia * thi), null);
						}
					}
					zz += 1;
					ovr = false;
				} else {
					ovr = true;
				}
			}

			// Stops the box from drawing past its height
			if (!ovr) {
				// Draws LAST line
				// Number of digits in last line
				int ac = (gh.length - (zz * aa));
				// Limits the last row
				if (ac > aa) {
					ac = aa;
				}
				if (font == 0) {
					for (int i = 0; i < ac; i++) {
						// g.drawImage(ImgLoad.txtAr[gh[i + (zz * aa)]], xl
						// + (i * twi), yl + (zz * thi), null);
					}
				} else if (font == 1) {
					for (int i = 0; i < ac; i++) {
						g.drawImage(ImgLoad.txtMc[gh[i + (zz * aa)]], xl
								+ (i * twi), yl + (zz * thi), null);
					}
				}
			}
		}
	}

	/**
	 * Methods go above here.
	 * 
	 */

	public static long timer() {
		return System.currentTimeMillis() - startTime;

	}

	int i1x = 0, i1y = 0;

	public void drwGm() {
		Graphics gspec = this.getGraphics();

		// Centers the camera around the player
		i1x = (int) ((w1 / 2) - (play.xx + 16));
		i1y = (int) ((h1 / 2) - (play.yy + 16));
		// Adjusts the cameras for when the player at the edge of a map.
		if ((play.xx + 16) < (w1 / 2)) {
			i1x = 0;
			xTop = true;
		} else {
			xTop = false;
		}
		if ((play.xx + 16) > (horBlocks * imgH) - (w1 / 2)) {
			i1x = -(((horBlocks) * imgH) - w1);
			xBot = true;
		} else {
			xBot = false;
		}
		if ((play.yy + 16) < (h1 / 2)) {
			i1y = 0;
			yTop = true;
		} else {
			yTop = false;
		}
		if ((play.yy + 16) > (verBlocks * imgH) - (h1 / 2)) {
			yBot = true;
		} else {
			yBot = false;
		}
		gspec.drawImage(image1, (i1x), i1y, null);

		gspec.dispose();

		gspec = this.getGraphics();
		gspec.drawImage(image2, 0, h1, null);
		gspec.dispose();

		gspec = this.getGraphics();
		gspec.drawImage(image3, w1, 0, null);
		gspec.dispose();
	}

	public static int[] converter(String st) {
		int a = st.length();
		int[] nw = new int[a];
		for (int b = 0; b < a; b++) {
			if (st.charAt(b) == 'a') {
				nw[b] = 0;
			} else if (st.charAt(b) == 'b') {
				nw[b] = 1;
			} else if (st.charAt(b) == 'c') {
				nw[b] = 2;
			} else if (st.charAt(b) == 'd') {
				nw[b] = 3;
			} else if (st.charAt(b) == 'e') {
				nw[b] = 4;
			} else if (st.charAt(b) == 'f') {
				nw[b] = 5;
			} else if (st.charAt(b) == 'g') {
				nw[b] = 6;
			} else if (st.charAt(b) == 'h') {
				nw[b] = 7;
			} else if (st.charAt(b) == 'i') {
				nw[b] = 8;
			} else if (st.charAt(b) == 'j') {
				nw[b] = 9;
			} else if (st.charAt(b) == 'k') {
				nw[b] = 10;
			} else if (st.charAt(b) == 'l') {
				nw[b] = 11;
			} else if (st.charAt(b) == 'm') {
				nw[b] = 12;
			} else if (st.charAt(b) == 'n') {
				nw[b] = 13;
			} else if (st.charAt(b) == 'o') {
				nw[b] = 14;
			} else if (st.charAt(b) == 'p') {
				nw[b] = 15;
			} else if (st.charAt(b) == 'q') {
				nw[b] = 16;
			} else if (st.charAt(b) == 'r') {
				nw[b] = 17;
			} else if (st.charAt(b) == 's') {
				nw[b] = 18;
			} else if (st.charAt(b) == 't') {
				nw[b] = 19;
			} else if (st.charAt(b) == 'u') {
				nw[b] = 20;
			} else if (st.charAt(b) == 'v') {
				nw[b] = 21;
			} else if (st.charAt(b) == 'w') {
				nw[b] = 22;
			} else if (st.charAt(b) == 'x') {
				nw[b] = 23;
			} else if (st.charAt(b) == 'y') {
				nw[b] = 24;
			} else if (st.charAt(b) == 'z') {
				nw[b] = 25;
			} else if (st.charAt(b) == ' ') {
				nw[b] = 26;
			} else if (st.charAt(b) == '0') {
				nw[b] = 27;
			} else if (st.charAt(b) == '1') {
				nw[b] = 28;
			} else if (st.charAt(b) == '2') {
				nw[b] = 29;
			} else if (st.charAt(b) == '3') {
				nw[b] = 30;
			} else if (st.charAt(b) == '4') {
				nw[b] = 31;
			} else if (st.charAt(b) == '5') {
				nw[b] = 32;
			} else if (st.charAt(b) == '6') {
				nw[b] = 33;
			} else if (st.charAt(b) == '7') {
				nw[b] = 34;
			} else if (st.charAt(b) == '8') {
				nw[b] = 35;
			} else if (st.charAt(b) == '9') {
				nw[b] = 36;
			} else if (st.charAt(b) == '.') {
				nw[b] = 37;
			}
		}
		return nw;
	}

	/**
	 * Listeners
	 */

	@Override
	public void keyPressed(KeyEvent ke) {
		play.keyPressed(ke);
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		play.keyReleased(ke);
	}

	@Override
	public void keyTyped(KeyEvent ke) {

	}

	@Override
	public void mouseClicked(MouseEvent me) {

	}

	@Override
	public void mousePressed(MouseEvent me) {
		play.mousePressed(me);
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
