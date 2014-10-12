package scapeD;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Player {
	int tar;

	// # of ticks per 1 attack.
	int atkSpd = 20;

	int atk = 0;

	int[] loc;
	float xx = 0;
	float yy = 0;

	// Used for drawing the circle for attacking
	long qpTim = 0;

	Image curPlay = ImgLoad.play[0];

	// Blocks the player can not walk through
	int[] impBs = { 0, 3, 5, 6 };

	// Players invintory
	// inv[1] = 2, you have 2 of the item one.
	int[] inv = { 0, 0 };

	// mining stat
	int statMine = 0;

	// Keys booleans
	boolean qP = false;
	boolean wP = false;
	boolean eP = false;
	boolean dP = false;
	boolean sP = false;
	boolean aP = false;
	boolean shiftP = false;
	boolean spaceP = false;

	boolean attacking;

	// Counts down for mining.
	static int mineNum = -1;

	public Player() {
		invInit();
		itemInit();
		// used for path finding
		paths = new ArrayList<PathFind>();
	}

	public void updatePlayer() {
		System.out.println("paths: " + paths.size());
		// moves for pathfinding
		for (int i = 0; i < paths.size(); i++) {
			paths.get(i).drawPath();
			paths.get(i).conCheck();
		}
		/**
		 * This is the shit for mining
		 */
		if (mineNum > -1) {
			// If player moves it cancels the channel
			if (!wP && !aP && !sP && !dP) {
				if (mineNum == 0) {
					// give item
					System.out.println("GIVE ITEM");
					// inv[0] += 1;
					statMine += 1;
					addInv(1, 1);
				}
			} else {
				mineNum = -1;
			}
			mineNum -= 1;
		}
		if (!shiftP) {
			if (wP && !dP && !sP && !aP || wP && dP && aP && !sP) {
				// Stupidly simple if loop to stop player from walking north off
				// screen.
				if (yy < 8) {
					yy = 0;
				} else {
					if (wallDet(0)) {
						if (Npc.overlap(xx, yy, 31, 15, 8, 0) == 1) {
							yy -= 8;
							curPlay = ImgLoad.play[1];
						} else if (Npc.overlap(xx, yy, 31, 15, 8, 0) == 0) {
							if (Npc.getdy() > 0) {
								if (Npc.getdy() < 8) {
									yy -= (Npc.getdy());
									curPlay = ImgLoad.play[1];
								}
							}
						}
					} else {
						if (yy % 32 != 0) {
							yy -= yy % 32;
							curPlay = ImgLoad.play[1];
						}
					}
				}
			}
			if (aP && !wP && !dP && !sP || aP && wP && sP && !dP) {
				if (xx < 8) {
					xx = 0;
				} else {
					if (wallDet(3)) {
						if (Npc.overlap(xx, yy, 31, 31, 8, 3) == 1) {
							xx -= 8;
							curPlay = ImgLoad.play[4];
						} else if (Npc.overlap(xx, yy, 31, 31, 8, 3) == 0) {
							if (Npc.getdx() > 0) {
								if (Npc.getdx() < 8) {
									xx -= Npc.getdx();
									curPlay = ImgLoad.play[4];
								}
							}
						}
					} else {
						if (xx % 32 <= 8) {
							if (xx % 32 != 0) {
								xx -= xx % 32;
								curPlay = ImgLoad.play[4];
							}
						}
					}
				}
			}
			if (sP && !aP && !wP && !dP || sP && aP && dP && !wP) {
				if (wallDet(2)) {
					if (Npc.overlap(xx, yy, 31, 31, 8, 2) == 1) {
						yy += 8;
						curPlay = ImgLoad.play[3];
					} else if (Npc.overlap(xx, yy, 31, 31, 8, 2) == 0) {
						if (Npc.getdy() > -8) {
							if (Npc.getdy() < 0) {
								yy -= Npc.getdy();
								curPlay = ImgLoad.play[3];
							}
						}
					}
				} else {
					if (32 - (yy % 32) <= 8) {
						yy += 32 - (yy % 32);
						curPlay = ImgLoad.play[3];
					}
				}
			}
			if (dP && !sP && !aP && !wP || dP && sP && wP && !aP) {
				if (wallDet(1)) {
					if (Npc.overlap(xx, yy, 31, 31, 8, 1) == 1) {
						xx += 8;
						curPlay = ImgLoad.play[2];
					} else if (Npc.overlap(xx, yy, 31, 31, 8, 1) == 0) {
						if (Npc.getdx() > -8) {
							if (Npc.getdx() < 0) {
								xx -= Npc.getdx();
								curPlay = ImgLoad.play[2];
							}
						}
					}
				} else {
					if (32 - (xx % 32) <= 8) {
						xx += 32 - (xx % 32);
						curPlay = ImgLoad.play[2];
					}
				}
			}
			// NW
			if (wP && aP && !sP && !dP) {
				if (wallDet(4)) {
					if (Npc.overlap(xx, yy, 31, 31, 5.6, 0) == 1
							&& Npc.overlap(xx, yy, 31, 31, 5.6, 3) == 1) {
						// Needs to do a corner check.
						if (Npc.cornDet(xx, yy, 31, 31, 5.6, 4) == 1) {
							// If this == 0 then it should havea random chance
							// of
							// going north or west
							yy -= 5.6;
							xx -= 5.6;
							curPlay = ImgLoad.play[5];
						}

					} else if (Npc.overlap(xx, yy, 31, 31, 5.6, 0) == 0) {
						if (Npc.overlap(xx, yy, 31, 31, 8, 3) == 1) {
							xx -= 8;
							curPlay = ImgLoad.play[4];
						}
						if (Npc.getdy() > 0) {
							if (Npc.getdy() <= 8) {
								yy -= (Npc.getdy());
								curPlay = ImgLoad.play[1];
							}
						}
					} else if (Npc.overlap(xx, yy, 31, 31, 5.6, 3) == 0) {
						if (Npc.overlap(xx, yy, 31, 31, 8, 0) == 1) {
							// If this returns 0 b/c another being it should get
							// closer.
							yy -= 8;
							curPlay = ImgLoad.play[1];
						}
						if (Npc.getdx() > 0) {
							if (Npc.getdx() <= 8) {
								xx -= Npc.getdx();
								curPlay = ImgLoad.play[4];
							}
						}
					}
				} else {
					if (wallDet(0)) {
						yy -= 8;
						curPlay = ImgLoad.play[1];
					} else {
						if (yy % 32 <= 8 && yy % 32 > 0) {
							yy -= yy % 32;
							curPlay = ImgLoad.play[1];
						}
					}
					if (wallDet(3)) {
						xx -= 8;
						curPlay = ImgLoad.play[4];
					} else {
						if (xx % 32 <= 8 && xx % 32 > 0) {
							xx -= xx % 32;
							curPlay = ImgLoad.play[4];
						}
					}
					// System.out.println(yy % 32);
				}
			}
			if (wP && dP && !sP && !aP) {
				if (wallDet(5)) {
					if (Npc.overlap(xx, yy, 31, 31, 5.6, 0) == 1
							&& Npc.overlap(xx, yy, 31, 31, 5.6, 1) == 1) {
						if (Npc.cornDet(xx, yy, 31, 31, 5.6, 5) == 1) {
							// Needs to do a corner check.
							yy -= 5.6;
							xx += 5.6;
							curPlay = ImgLoad.play[6];
						}
					} else if (Npc.overlap(xx, yy, 31, 31, 5.6, 0) == 0) {
						if (Npc.overlap(xx, yy, 31, 31, 8, 1) == 1) {
							xx += 8;
							curPlay = ImgLoad.play[2];
						}
						if (Npc.getdy() > 0) {
							if (Npc.getdy() < 8) {
								yy -= (Npc.getdy());
								curPlay = ImgLoad.play[1];
							}
						}
					} else if (Npc.overlap(xx, yy, 31, 31, 5.6, 1) == 0) {
						if (Npc.overlap(xx, yy, 31, 31, 8, 0) == 1) {
							yy -= 8;
							curPlay = ImgLoad.play[1];
						}

						if (Npc.getdx() > -8) {
							if (Npc.getdx() < 0) {
								xx -= Npc.getdx();
								curPlay = ImgLoad.play[2];
							}
						}
					}
				} else {
					if (wallDet(0)) {
						yy -= 8;
						curPlay = ImgLoad.play[1];
					} else {
						if (yy % 32 > 0) {
							if (yy % 32 <= 8) {
								yy -= yy % 32;
								curPlay = ImgLoad.play[1];
							}
						}
					}
					if (wallDet(1)) {
						xx += 8;
						curPlay = ImgLoad.play[2];
					} else {
						// System.out.println("xx dit: " + (32 - (xx % 32)));
						if (32 - (xx % 32) <= 8) {
							xx += 32 - (xx % 32);
							curPlay = ImgLoad.play[2];
						}
					}
				}
			}
			if (sP && dP && !wP && !aP) {
				if (wallDet(6)) {
					if (Npc.overlap(xx, yy, 31, 31, 5.6, 2) == 1
							&& Npc.overlap(xx, yy, 31, 31, 5.6, 1) == 1) {
						if (Npc.cornDet(xx, yy, 31, 31, 5.6, 6) == 1) {
							xx += 5.6;
							yy += 5.6;
							curPlay = ImgLoad.play[7];
						}
					} else if (Npc.overlap(xx, yy, 31, 31, 5.6, 2) == 0) {
						if (Npc.overlap(xx, yy, 31, 31, 8, 1) == 1) {
							xx += 8;
							curPlay = ImgLoad.play[2];
						}
						if (Npc.getdy() > -8) {
							if (Npc.getdy() < 0) {
								yy -= Npc.getdy();
								curPlay = ImgLoad.play[3];
							}
						}
					} else if (Npc.overlap(xx, yy, 31, 31, 5.6, 1) == 0) {
						if (Npc.overlap(xx, yy, 31, 31, 8, 2) == 1) {
							yy += 8;
							curPlay = ImgLoad.play[3];
						}

						if (Npc.getdx() > -8) {
							if (Npc.getdx() < 0) {
								xx -= Npc.getdx();
								curPlay = ImgLoad.play[2];
							}
						}
					}
				} else {
					if (wallDet(2)) {
						yy += 8;
						curPlay = ImgLoad.play[3];
					} else {
						if (32 - (yy % 32) <= 8) {
							yy += 32 - (yy % 32);
							curPlay = ImgLoad.play[3];
						}
					}
					if (wallDet(1)) {
						xx += 8;
						curPlay = ImgLoad.play[2];
					} else {
						if (32 - (xx % 32) <= 8) {
							xx += 32 - (xx % 32);
							curPlay = ImgLoad.play[2];
						}
					}
				}
			}
			if (sP && aP && !wP && !dP) {
				if (wallDet(7)) {
					if (Npc.overlap(xx, yy, 31, 31, 5.6, 2) == 1
							&& Npc.overlap(xx, yy, 31, 31, 5.6, 3) == 1) {
						if (Npc.cornDet(xx, yy, 31, 31, 5.6, 7) == 1) {
							xx -= 5.6;
							yy += 5.6;
							curPlay = ImgLoad.play[8];
						}
					} else if (Npc.overlap(xx, yy, 31, 31, 5.6, 2) == 0) {
						if (Npc.overlap(xx, yy, 31, 31, 8, 3) == 1) {
							xx -= 8;
							curPlay = ImgLoad.play[4];
						}
						if (Npc.getdy() > -8) {
							if (Npc.getdy() < 0) {
								yy -= Npc.getdy();
								curPlay = ImgLoad.play[3];
							}
						}
					} else if (Npc.overlap(xx, yy, 31, 31, 5.6, 3) == 0) {
						if (Npc.overlap(xx, yy, 31, 31, 8, 2) == 1) {
							// If this returns 0 b/c another being it should get
							// closer.
							yy += 8;
							curPlay = ImgLoad.play[3];
						}
						if (Npc.getdx() > 0) {
							if (Npc.getdx() <= 8) {
								xx -= Npc.getdx();
								curPlay = ImgLoad.play[4];
							}
						}
					}
				} else {
					if (wallDet(2)) {
						yy += 8;
						curPlay = ImgLoad.play[3];
					} else {
						if (32 - (yy % 32) <= 8 && 32 - (yy % 32) > 0) {
							yy += 32 - (yy % 32);
							curPlay = ImgLoad.play[3];
						}
					}
					if (wallDet(3)) {
						xx -= 8;
						curPlay = ImgLoad.play[4];
					} else {
						if (xx % 32 <= 8 && xx % 32 > 0) {
							xx -= xx % 32;
							curPlay = ImgLoad.play[4];
						}
					}
				}
			}

			if (eP) {
				System.out.println(Npc.dir(xx + 16, yy + 16));
				System.out.println("playLoc: (" + xx + "," + yy + ")");
				System.out.println("len.s " + Panel.map.get(1).length);
			}
			if (spaceP) {
				if (tar == MineNode.num) {
					// if char doesnt move and 20 ticks is passed an item is
					// given to the player.

					// Have a health bar fill up to show progress. should be 12
					// ticks to make it easy then.
					double d = Math.hypot(Math.abs(xx - MineNode.xx),
							Math.abs(yy - MineNode.yy));
					if (d < 64) {
						mineNum = 24 - statMine;
						if (mineNum < 1) {
							mineNum = 1;
						}
						// make bar beneath nodes that fills.
					}
				}
				spaceP = false;
			}
		} else {
			if (wP && !dP && !sP && !aP) {
				curPlay = ImgLoad.play[1];
			}
			if (aP && !wP && !dP && !sP) {
				curPlay = ImgLoad.play[4];
			}
			if (sP && !aP && !wP && !dP) {
				curPlay = ImgLoad.play[3];
			}
			if (dP && !sP && !aP && !wP) {
				curPlay = ImgLoad.play[2];
			}
			if (wP && aP && !sP && !dP) {
				curPlay = ImgLoad.play[5];
			}
			if (wP && dP && !sP && !aP) {
				curPlay = ImgLoad.play[6];
			}
			if (sP && dP && !wP && !aP) {
				curPlay = ImgLoad.play[7];
			}
			if (sP && aP && !wP && !dP) {
				curPlay = ImgLoad.play[8];
			}
		}
		if (attacking) {
			Panel.npCircle();
			attack();
		}
		// De targeting system.

		if (tar != 0) {
			// I do the for loop b/c n the future the 1 would be replaces with
			// the array list of entities.size.
			for (int i = 0; i < 1; i++) {
				if (tar == Npc.num) {
					if (Math.hypot(Math.abs(xx - Npc.xx), Math.abs(yy - Npc.yy)) > 112) {
						tar = 0;
						attacking = false;
					}
				}
			}
		}
		// if (tar != 0) {
		// for (int i = 0; i < 1; i++) {
		// if (Npc.num == tar) {
		// if (Math.abs((xx + 16) - (Npc.xx + 16)) > Panel.width / 2 + 16) {
		// tar = 0;
		// }
		// if (Math.abs((yy + 16) - (Npc.yy + 16)) > Panel.height / 2 + 16) {
		// tar = 0;
		// } } } }
	}

	public void drawPlayer(Graphics g) {
		g.drawImage(curPlay, (int) xx, (int) yy, null);
	}

	/**
	 * Methods
	 */

	String[] itemNames;
	int[] itemMax;

	// items[x] = # of items in a stack

	public void itemInit() {
		itemNames = new String[4];
		itemNames[0] = "No Item";
		itemNames[1] = "wood Sword";
		itemNames[2] = "item2";
		itemNames[3] = "item3";

		// Maximum number of times that item can stack
		itemMax = new int[4];
		itemMax[0] = 0;
		itemMax[1] = 3;
	}

	int bagSlots = 20;
	int[][] inv1;

	public void invInit() {
		inv1 = new int[bagSlots][2];
	}

	public void addInv(int itemNum, int numItems) {
		// d allows me to distribue the correct number of items.
		int d = numItems;

		if (itemMax[itemNum] > 1) {
			for (int a = 0; a < bagSlots; a++) {
				if (inv1[a][0] == itemNum) {
					// same item
					// check to see is max stack.
					if (inv1[a][1] < itemMax[itemNum]) {
						// Im only adding 1 so it works. But for when it is
						// adding more than 1 item this will be buggy.
						d -= 1;
						inv1[a][1] += 1;
					}
				}
			}
		}
		// Should first search through bags for unmaxed stack of same item.
		// Makes a new item stack.
		for (int i = 0; i < bagSlots; i++) {
			// Checks to open slots
			if (inv1[i][0] == 0) {
				// System.out.println("Slot " + i + " open");
				while (d > 0) {
					d -= 1;
					// System.out.println("add Item: "+i);
					// System.out.println("inv: "+inv1[i][0]);
					inv1[i][0] = itemNum;
					inv1[i][1] = 1;
				}
			}
		}
		if (d > 0) {
			System.out.println("Full Invintory");
		}
	}

	public void attack() {
		if (tar == Npc.num) {
			if (Npc.distance(xx + 16, yy + 16) < 112) {
				if (Npc.dir(xx, yy) == 0) {
					if (chaDir() == 2 || chaDir() == 6 || chaDir() == 7) {
						hit();
					}
				}
				if (Npc.dir(xx, yy) == 1) {
					if (chaDir() == 3 || chaDir() == 4 || chaDir() == 7) {
						hit();
					}
				}
				if (Npc.dir(xx, yy) == 2) {
					if (chaDir() == 0 || chaDir() == 4 || chaDir() == 5) {
						hit();
					}
				}
				if (Npc.dir(xx, yy) == 3) {
					if (chaDir() == 1 || chaDir() == 5 || chaDir() == 6) {
						hit();
					}
				}
				if (Npc.dir(xx, yy) == 4) {
					if (chaDir() == 1 || chaDir() == 2 || chaDir() == 6) {
						hit();
					}
				}
				if (Npc.dir(xx, yy) == 5) {
					if (chaDir() == 2 || chaDir() == 3 || chaDir() == 7) {
						hit();
					}
				}
				if (Npc.dir(xx, yy) == 6) {
					if (chaDir() == 0 || chaDir() == 3 || chaDir() == 4) {
						hit();
					}
				}
				if (Npc.dir(xx, yy) == 7) {
					if (chaDir() == 0 || chaDir() == 1 || chaDir() == 5) {
						hit();
					}
				}
			}
		}
	}

	public void hit() {
		if (atk == 0) {
			Npc.health -= 10;
			Panel.cmbt((int) Npc.xx, (int) Npc.yy, 32, 32, 10, 0);
			atk = atkSpd;
		} else {
			atk -= 1;
		}
	}

	public int chaDir() {
		if (curPlay == ImgLoad.play[1]) {
			return 0;
		} else if (curPlay == ImgLoad.play[2]) {
			return 1;
		} else if (curPlay == ImgLoad.play[3]) {
			return 2;
		} else if (curPlay == ImgLoad.play[4]) {
			return 3;
		} else if (curPlay == ImgLoad.play[5]) {
			return 4;
		} else if (curPlay == ImgLoad.play[6]) {
			return 5;
		} else if (curPlay == ImgLoad.play[7]) {
			return 6;
		} else if (curPlay == ImgLoad.play[8]) {
			return 7;
		} else {
			return 0;
		}
	}

	float ben;
	float bes;

	float baes;

	public boolean wallDet(int d) {
		// Block Adjacent Var
		float baw = ((xx - 1) - ((xx - 1) % 32)) / 32;
		float bae = ((xx + 32) - ((xx + 32) % 32)) / 32;

		float ban = ((yy - 1) - ((yy - 1) % 32)) / 32;
		float bas = ((yy + 32) - ((yy + 32) % 32)) / 32;

		// Block Edge Var
		float bee = ((xx + 31) - ((xx + 31) % 32)) / 32;
		float bew = ((xx) - ((xx) % 32)) / 32;

		ben = ((yy) - ((yy) % 32)) / 32;
		bes = ((yy + 31) - ((yy + 31) % 32)) / 32;

		// pixel adjacent + speed
		float bans = ((yy - 1 - 8) - ((yy - 1 - 8) % 32)) / 32;
		baes = ((xx + 32 + 8) - ((xx + 32 + 8) % 32)) / 32;
		float bass = ((yy + 32 + 8) - ((yy + 32 + 8) % 32)) / 32;
		float baws = ((xx - 1 - 8) - ((xx - 1 - 8) % 32)) / 32;

		if (d == 0) {
			for (int i = 0; i < impBs.length; i++) {
				if (Panel.getMapVar((int) bans, (int) bee) == impBs[i]) {
					return false;
				}
				if (Panel.getMapVar((int) bans, (int) bew) == impBs[i]) {
					return false;
				}
			}

		}
		if (d == 1) {
			for (int i = 0; i < impBs.length; i++) {
				// System.out.println("baes: " + baes);
				if (Panel.getMapVar((int) ben, (int) baes) == impBs[i]) {
					return false;
				}
				if (Panel.getMapVar((int) bes, (int) baes) == impBs[i]) {
					return false;
				}
			}
			// return eastS();
		}
		if (d == 2) {
			for (int i = 0; i < impBs.length; i++) {
				// System.out.println("baes: " + baes);
				if (Panel.getMapVar((int) bass, (int) bee) == impBs[i]) {
					return false;
				}
				if (Panel.getMapVar((int) bass, (int) bew) == impBs[i]) {
					return false;
				}
			}
		}
		if (d == 3) {
			int exception = 0;
			for (int i = 0; i < impBs.length; i++) {
				if ((xx - 8) % 32 > 0 && (xx - 8) % 32 < .5) {
					exception = 1;
				}
				// System.out.println("int: " + (baws - 8) % 32);

				if (Panel.getMapVar((int) ben, (int) baws) == impBs[i]) {
					if (exception == 1) {
						return true;
					}
					return false;
				}
				if (Panel.getMapVar((int) bes, (int) baws) == impBs[i]) {
					if (exception == 1) {
						return true;
					}
					return false;
				}
			}
		}
		/**
		 * DIAGONAL DETECIONT
		 */

		if (d == 4) {
			for (int i = 0; i < impBs.length; i++) {
				if (Panel.getMapVar((int) bans, (int) bee) == impBs[i]) {
					return false;
				}
				if (Panel.getMapVar((int) bans, (int) bew) == impBs[i]) {
					return false;
				}
				if (Panel.getMapVar((int) ben, (int) baws) == impBs[i]) {
					return false;
				}
				if (Panel.getMapVar((int) bes, (int) baws) == impBs[i]) {
					return false;
				}

			}
		}
		if (d == 5) {
			for (int i = 0; i < impBs.length; i++) {
				if (Panel.getMapVar((int) bans, (int) bee) == impBs[i]) {
					return false;
				}
				if (Panel.getMapVar((int) bans, (int) bew) == impBs[i]) {
					return false;
				}
				if (Panel.getMapVar((int) ben, (int) baes) == impBs[i]) {
					return false;
				}
				if (Panel.getMapVar((int) bes, (int) baes) == impBs[i]) {
					return false;
				}
			}
		}
		if (d == 6) {
			for (int i = 0; i < impBs.length; i++) {
				if (Panel.getMapVar((int) bass, (int) bee) == impBs[i]) {
					return false;
				}
				if (Panel.getMapVar((int) bass, (int) bew) == impBs[i]) {
					return false;
				}
				if (Panel.getMapVar((int) ben, (int) baes) == impBs[i]) {
					return false;
				}
				if (Panel.getMapVar((int) bes, (int) baes) == impBs[i]) {
					return false;
				}
			}
		}
		if (d == 7) {
			for (int i = 0; i < impBs.length; i++) {
				if (Panel.getMapVar((int) bass, (int) bee) == impBs[i]) {
					return false;
				}
				if (Panel.getMapVar((int) bass, (int) bew) == impBs[i]) {
					return false;
				}
				if (Panel.getMapVar((int) ben, (int) baws) == impBs[i]) {
					return false;
				}
				if (Panel.getMapVar((int) bes, (int) baws) == impBs[i]) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean eastS() {
		if (Panel.getMapVar((int) ben, (int) baes) == 7) {
			return false;
		}
		if (Panel.getMapVar((int) bes, (int) baes) == 7) {
			return false;
		}
		return true;
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
		paths.add(new PathFind(x, y, id, (int) xx, (int) yy, this));
		id++;
	}

	public void deletePathFinding(int id) {
		for (int i = 0; i < paths.size(); i++) {
			if (paths.get(i).id == id) {
				paths.remove(i);
			}
		}
	}

	/**
	 * End of path finding
	 */

	/**
	 * Listeners
	 */

	public void keyPressed(KeyEvent ke) {
		int e = ke.getKeyCode();

		if (e == KeyEvent.VK_SHIFT) {
			shiftP = true;
		}
		if (e == KeyEvent.VK_W) {
			wP = true;
		}
		if (e == KeyEvent.VK_D) {
			dP = true;
		}
		if (e == KeyEvent.VK_S) {
			sP = true;
		}
		if (e == KeyEvent.VK_A) {
			aP = true;
		}
		if (e == KeyEvent.VK_Q) {
			if (!qP) {
				if (qpTim + 20 < Panel.timer()) {
					qpTim = Panel.timer();
					qP = true;
				}
			} else {
				if (qpTim + 20 < Panel.timer()) {
					qpTim = Panel.timer();
					qP = false;
				}
			}
		}
		if (e == KeyEvent.VK_E) {
			eP = true;
		}
		if (e == KeyEvent.VK_SPACE) {
			spaceP = true;
		}
	}

	public void keyReleased(KeyEvent ke) {
		int e = ke.getKeyCode();

		if (e == KeyEvent.VK_SHIFT) {
			shiftP = false;
		}
		if (e == KeyEvent.VK_W) {
			wP = false;
		}
		if (e == KeyEvent.VK_D) {
			dP = false;
		}
		if (e == KeyEvent.VK_S) {
			sP = false;
		}
		if (e == KeyEvent.VK_A) {
			aP = false;
		}
		if (e == KeyEvent.VK_Q) {
			// qP = false;
		}
		if (e == KeyEvent.VK_E) {
			eP = false;
		}
	}

	public void mousePressed(MouseEvent me) {
		/*
		 * int xmouse = (int) (me.getX() + xx - (Panel.w1 / 2) + 16); int ymouse
		 * = (int) (me.getY() + yy - (Panel.h1 / 2) + 16); if (Panel.yTop) {
		 * ymouse = me.getY(); } else if (Panel.yBot) { ymouse = me.getY() +
		 * ((Panel.verBlocks * Panel.imgH) - Panel.h1); } if (Panel.xTop) {
		 * xmouse = me.getX(); } else if (Panel.xBot) { xmouse = me.getX() +
		 * ((Panel.horBlocks * Panel.imgH) - Panel.w1); }
		 * 
		 * System.out.println("xMouse: " + ((xmouse - (xmouse / 32)) / 32));
		 * 
		 * // Left click if (me.getButton() == MouseEvent.BUTTON1) { if
		 * (Npc.clickCheck(xmouse, ymouse)) { tar = Npc.getnum(); } else { tar =
		 * 0; attacking = false; } if (MineNode.clickCheck(xmouse, ymouse)) {
		 * tar = MineNode.getnum(); attacking = true; } else { // tar = 0; } }
		 * // Right click if (me.getButton() == MouseEvent.BUTTON3) { if
		 * (Npc.clickCheck(xmouse, ymouse)) { tar = Npc.getnum(); attacking =
		 * true; } if (MineNode.clickCheck(xmouse, ymouse)) { tar =
		 * MineNode.getnum(); } }
		 */
	}

	public void mouseReleased(MouseEvent me) {
		System.out.println("release");
		// Find block that i click on
		int x = (me.getX() - (me.getX() % 32)) / 32;
		int y = (me.getY() - (me.getY() % 32)) / 32;
		if (me.getButton() == MouseEvent.BUTTON1) {
			makePathFinding(x, y);
		} else if (me.getButton() == MouseEvent.BUTTON3) {
			if (Panel.getMapVar(x, y) != 2) {
				Panel.changeMap(y, x, 2);
			} else if (Panel.getMapVar(x, y) == 2) {
				Panel.changeMap(y, x, 1);
			}

		}
	}
}
