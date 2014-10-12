package luggage;

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

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable, KeyListener,
		MouseListener {

	// With and height of each tile image.
	int imgH = 32;

	// Width and Height of JFrame
	int width = 640;
	int height = 640;

	// width and height for the different graphics boxes
	// 1 is the play area.
	int w1 = 640;
	int h1 = 448;

	// map arraylist
	static ArrayList<int[]> map;

	Image image1;
	public static Graphics g1;

	// Essential thread variable
	Thread thread;

	// Vars for gLoop Below
	public int tps = 20;
	public int milps = 1000 / tps;
	long lastTick = 0;
	int sleepTime = 0;
	long lastSec = 0;
	int ticks = 0;
	long startTime;
	long runTime;
	private long nextTick = 0;
	private boolean running = false;

	// Vars for gLoop Above

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
		image1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g1 = (Graphics2D) image1.getGraphics();
		this.setSize(new Dimension(width, height));

		addKeyListener(this);
		addMouseListener(this);

		startTime = System.currentTimeMillis();

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
		paths = new ArrayList<PathFind>();

		running = true;
		gLoop();
	}

	public void gLoop() {
		while (running) {
			// Do the things you want the gLoop to do below here
			drawMap(g1);

			drawPlayer();
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
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
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

	public static int getMapVar(int x, int y) {
		// System.out.println("lay: " + lan.get(0).length);
		// System.out.println("lan: " + lan.size());
		try {
			return map.get(y)[x];
		} catch (Exception ex) {
			return -1;
		}
	}

	public void changeMap(int y, int x, int z) {
		map.get(y)[x] = z;
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
	 * START OF PATHFINDING
	 */

	// Constructors gets called then conCheck() gets called every tick to
	// completion
	static ArrayList<PathFind> paths;
	int id = 0;

	public void makePathFinding(int x, int y) {
		if (paths.size() != 0) {
			// Panel.setPlayLoc(paths.get(0).px, paths.get(0).py);
			deletePathFinding(paths.get(0).id);
		}
		paths.add(new PathFind(x, y, id, px, py));
		id++;
	}

	public static void deletePathFinding(int id) {
		for (int i = 0; i < paths.size(); i++) {
			if (paths.get(i).id == id) {
				paths.remove(i);
			}
		}
	}

	public void drawPlayer() {
		g1.drawImage(ImgLoad.play[0], px * 32, py * 32, null);
	}

	// Player x and y
	static int px = 0;
	static int py = 0;

	public static void setPlayLoc(int x, int y) {
		px = x;
		py = y;
	}

	/**
	 * Methods go above here.
	 * 
	 */

	public long timer() {
		return System.currentTimeMillis() - startTime;
	}

	public void drwGm() {
		Graphics g2 = this.getGraphics();
		g2.drawImage(image1, 0, 0, null);
		g2.dispose();
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			// doStuff();
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			// findLow();

		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			// printOut();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent me) {
		// Find block that i click on
		int x = (me.getX() - (me.getX() % 32)) / 32;
		int y = (me.getY() - (me.getY() % 32)) / 32;
		if (me.getButton() == MouseEvent.BUTTON1) {
			makePathFinding(x, y);
		} else if (me.getButton() == MouseEvent.BUTTON3) {
			if (getMapVar(x, y) != 2) {
				changeMap(y, x, 2);
			} else if (getMapVar(x, y) == 2) {
				changeMap(y, x, 1);
			}

		}
	}
}
