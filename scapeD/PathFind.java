package scapeD;

import java.util.ArrayList;

public class PathFind {

	// Player loc
	int px = 0;
	int py = 0;

	// Target loc
	int tx = 12;
	int ty = 9;

	int id;
	ArrayList<int[]> open;
	ArrayList<int[]> closed;
	ArrayList<int[]> temp;

	Player play;

	public PathFind(int tx, int ty, int id, int px, int py, Player play) {
		this.tx = tx;
		this.ty = ty;
		this.id = id;
		this.px = px;
		this.py = py;
		this.play = play;

		closed = new ArrayList<int[]>();
		open = new ArrayList<int[]>();
		temp = new ArrayList<int[]>();
		closed.add(new int[] { px, py, -1, 0, -1, -1 });

		beginSearch();
	}

	void beginSearch() {
		getAdj(px, py, 0);
		while (!pathDone) {
			findLow();
		}
	}

	public void findLow() {
		int g = 0;
		for (int i = 1; i < open.size(); i++) {
			// i = 1 because it automatically assumes that 0 is the lowest 2+ 3.
			// g is lowest of temp.
			if (open.get(i)[2] + open.get(i)[3] < open.get(g)[2]
					+ open.get(g)[3]) {
				g = i;
			}
		}
		// g is lowest block
		addClosed(open.get(g));
		open.remove(open.get(g));
		getAdj(closed.get(closed.size() - 1)[0],
				closed.get(closed.size() - 1)[1],
				closed.get(closed.size() - 1)[3]);
	}

	// x, y, h, g, parentx, parenty
	public void getAdj(int x, int y, int f) {
		// Temp int array
		int[] d = { x, y, getMd(x, y), f, -1, -1 };

		// f should be taken from parent

		// add all to temp
		int xx = d[0] - 1;
		int yy = d[1] - 1;
		if (Panel.getMapVar(xx, yy) != 2 && Panel.getMapVar(xx, yy) != -1) {
			temp.add(new int[] { xx, yy, getMd(xx, yy), d[3] + 14, x, y });
		}
		xx = d[0];
		yy = d[1] - 1;
		if (Panel.getMapVar(xx, yy) != 2 && Panel.getMapVar(xx, yy) != -1) {
			temp.add(new int[] { xx, yy, getMd(xx, yy), d[3] + 10, x, y });
		}
		xx = d[0] + 1;
		yy = d[1] - 1;
		if (Panel.getMapVar(xx, yy) != 2 && Panel.getMapVar(xx, yy) != -1) {
			temp.add(new int[] { xx, yy, getMd(xx, yy), d[3] + 14, x, y });
		}
		xx = d[0] - 1;
		yy = d[1];
		if (Panel.getMapVar(xx, yy) != 2 && Panel.getMapVar(xx, yy) != -1) {
			temp.add(new int[] { xx, yy, getMd(xx, yy), d[3] + 10, x, y });
		}
		xx = d[0] + 1;
		yy = d[1];
		if (Panel.getMapVar(xx, yy) != 2 && Panel.getMapVar(xx, yy) != -1) {
			temp.add(new int[] { xx, yy, getMd(xx, yy), d[3] + 10, x, y });
		}
		xx = d[0] - 1;
		yy = d[1] + 1;
		if (Panel.getMapVar(xx, yy) != 2 && Panel.getMapVar(xx, yy) != -1) {
			temp.add(new int[] { xx, yy, getMd(xx, yy), d[3] + 14, x, y });
		}
		xx = d[0];
		yy = d[1] + 1;
		if (Panel.getMapVar(xx, yy) != 2 && Panel.getMapVar(xx, yy) != -1) {
			temp.add(new int[] { xx, yy, getMd(xx, yy), d[3] + 10, x, y });
		}
		xx = d[0] + 1;
		yy = d[1] + 1;
		if (Panel.getMapVar(xx, yy) != 2 && Panel.getMapVar(xx, yy) != -1) {
			temp.add(new int[] { xx, yy, getMd(xx, yy), d[3] + 14, x, y });
		}
		for (int i = 0; i < temp.size(); i++) {
			addOpen(temp.get(i));
		}
		temp.clear();
	}

	// make a method to check for overlap on open list.
	// make an adding method for closed like i did for open

	void addOpen(int[] d) {
		// Check open (and closed) list to see if the x, y value being added is
		// already on
		int a = 0;
		for (int i = 0; i < open.size(); i++) {
			if (d[0] == open.get(i)[0]) {
				if (d[1] == open.get(i)[1]) {
					// if what your adding's g is lower than what is there. then
					// change parent.
					if (d[3] < open.get(i)[3]) {
						open.set(i, d);
					}
					a++;
				}
			}
		}
		// If no collisions have happened so far.
		if (a == 0) {
			// Checks closed list now
			for (int p = 0; p < closed.size(); p++) {
				if (d[0] == closed.get(p)[0]) {
					if (d[1] == closed.get(p)[1]) {
						a++;
					}
				}
			}
		}
		if (a == 0) {
			open.add(d);
		}
	}

	ArrayList<int[]> path;

	public void addClosed(int[] d) {
		int a = 0;
		for (int p = 0; p < closed.size(); p++) {
			if (d[0] == closed.get(p)[0]) {
				if (d[1] == closed.get(p)[1]) {
					System.out.println("overlap");
					a++;
				}

			}
		}
		if (a == 0) {
			// sees if what is being added to closed is the target.
			closed.add(d);
			if (d[0] == tx) {
				if (d[1] == ty) {
					// If so it draws path and starts moving.
					// find the last added to closed list, should be on target
					path = new ArrayList<int[]>();
					path.add(closed.get(closed.size() - 1));
					while (!pathDone) {
						doStuff();
					}
				}
			}
		}
	}

	boolean pathDone = false;

	void doStuff() {
		int d = -1;
		// Searches all of closed for the parent of the node most recently
		// added to path.
		for (int i = 0; i < closed.size(); i++) {
			if (path.get(path.size() - 1)[4] == closed.get(i)[0]) {
				if (path.get(path.size() - 1)[5] == closed.get(i)[1]) {
					d = i;
				}
			}
		}
		if (d != -1) {
			path.add(closed.get(d));
		} else {
			// Cant find parent.
			// sice player loc's parent is -1, -1 (unacessable location) it
			// means that the path has reached the players block
			pathDone = true;
		}
	}

	int getMd(int x, int y) {
		return (Math.abs(x - tx) * 10) + (Math.abs(y - ty) * 10);
	}

	boolean pathComplete = false;

	// used for moving player through path.
	int ga = 0;
	int gi = 0;

	boolean runOnce = true;

	void giInit() {
		if (runOnce)
			gi = path.size() - 2;
		runOnce = false;
	}

	// Checks to see if players location is the last square of the path
	void conCheck() {
		// constant check that gets ran every tick.
		// when this happens, once every 20 ticks (once a second) the player
		// moves through the path until it hits the target at which it deletes
		// the ArrayList
		if (!pathComplete) {
			// use this to stop the loop from running once it doesnt needto.
			if (path != null) {
				// System.out.println("not null");
				for (int i = 0; i < path.size(); i++) {
					// System.out.println("pathSize: "+path.size());
					// if players spot is in path
					if (path.get(i)[0] == px && path.get(i)[1] == py) {
						// now what, path al has everything the player needs to
						// move to. Start moving player every second
						pathComplete = true;
					}
				}
			}
		} else {
			// if pathComplete == true;
			giInit();
			if (ga == 5) {
				if (gi >= 0) {
					px = path.get(gi)[0];
					py = path.get(gi)[1];
					gi -= 1;
					ga = 0;
					play.xx = px;
					play.yy = py;
				}
			} else {
				ga++;
			}
			if (px == tx && py == ty) {
				// Panel.deletePathFinding(id);
			}
		}
	}

	/**
	 * Optional labeling methods
	 */

	void drawPath() {
		try {
			for (int i = 0; i < path.size(); i++) {
				Panel.g1.drawImage(ImgLoad.small[0], path.get(i)[0] * 32,
						path.get(i)[1] * 32, null);
			}
		} catch (Exception ex) {

		}
	}

	// Draw player wont be necessary but i would still like do draw an
	// indication to where the player is going.

	public void printOut() {
		for (int i = 0; i < closed.size(); i++) {
			System.out.println("Closed " + i + " (" + closed.get(i)[0] + ", "
					+ closed.get(i)[1] + ")");
		}
	}

	// lable boxes
	void lableBoxes() {
		for (int i = 0; i < closed.size(); i++) {
			int[] b = Panel.converter(Integer.toString(closed.get(i)[3]));
			for (int c = 0; c < b.length; c++) {
				Panel.g1.drawImage(ImgLoad.txtMc[b[c]], (closed.get(i)[0] * 32)
						+ 7 + (c * 6), (closed.get(i)[1] * 32) + 16, null);
			}
		}
		// Draws for open list
		for (int i = 0; i < open.size(); i++) {
			int[] b = Panel.converter(Integer.toString(open.get(i)[3]));
			for (int c = 0; c < b.length; c++) {
				Panel.g1.drawImage(ImgLoad.txtMc[b[c]], (open.get(i)[0] * 32)
						+ 7 + (c * 6), (open.get(i)[1] * 32) + 16, null);
			}
		}
	}
}
