package luggage;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImgLoad {
	public static Image[] play;
	public static Image[] txtMc;
	public static Image[] health;
	public static Image[] mapAr;
	public static Image[] small;
	public static Image[] arrows;

	public static void imageInit() {
		int a = 0;

		play = new Image[9];
		ImageIcon ii = new ImageIcon(
				ImgLoad.class.getResource("res/Cylinder/Cylinder.png"));
		play[0] = ii.getImage();
		ii = new ImageIcon(
				ImgLoad.class.getResource("res/Cylinder/CylinderN.png"));
		play[1] = ii.getImage();
		ii = new ImageIcon(
				ImgLoad.class.getResource("res/Cylinder/CylinderE.png"));
		play[2] = ii.getImage();
		ii = new ImageIcon(
				ImgLoad.class.getResource("res/Cylinder/CylinderS.png"));
		play[3] = ii.getImage();
		ii = new ImageIcon(
				ImgLoad.class.getResource("res/Cylinder/CylinderW.png"));
		play[4] = ii.getImage();
		ii = new ImageIcon(
				ImgLoad.class.getResource("res/Cylinder/CylinderNW.png"));
		play[5] = ii.getImage();
		ii = new ImageIcon(
				ImgLoad.class.getResource("res/Cylinder/CylinderNE.png"));
		play[6] = ii.getImage();
		ii = new ImageIcon(
				ImgLoad.class.getResource("res/Cylinder/CylinderSE.png"));
		play[7] = ii.getImage();
		ii = new ImageIcon(
				ImgLoad.class.getResource("res/Cylinder/CylinderSW.png"));
		play[8] = ii.getImage();

		mapAr = new Image[7];
		ii = new ImageIcon(ImgLoad.class.getResource("res/Tile/TGrassL.png"));
		mapAr[0] = ii.getImage();
		ii = new ImageIcon(ImgLoad.class.getResource("res/Tile/TBush1.png"));
		mapAr[1] = ii.getImage();
		ii = new ImageIcon(ImgLoad.class.getResource("res/Tile/Wall/Wall2.png"));
		mapAr[2] = ii.getImage();
		ii = new ImageIcon(ImgLoad.class.getResource("res/Tile/Water1.png"));
		mapAr[3] = ii.getImage();
		ii = new ImageIcon(ImgLoad.class.getResource("res/Tile/Water2.png"));
		mapAr[4] = ii.getImage();
		ii = new ImageIcon(ImgLoad.class.getResource("res/Tile/Fire.png"));
		mapAr[5] = ii.getImage();
		ii = new ImageIcon(ImgLoad.class.getResource("res/Tile/WallFlat.png"));
		mapAr[6] = ii.getImage();

		txtMc = new Image[38];
		a = 0;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cA.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cB.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cC.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cD.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cE.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cF.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cG.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cH.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cI.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cJ.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cK.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cL.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cM.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cN.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cO.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cP.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cQ.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cR.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cS.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cT.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cU.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cV.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cW.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cX.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cY.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cZ.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/cSpace.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/n0.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/n1.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/n2.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/n3.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/n4.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/n5.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/n6.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/n7.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/n8.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/n9.png"));
		txtMc[a] = ii.getImage();
		a += 1;
		ii = new ImageIcon(ImgLoad.class.getResource("res/Text/mc/period.png"));
		txtMc[a] = ii.getImage();
		a += 1;

		small = new Image[1];
		ii = new ImageIcon(
				ImgLoad.class.getResource("res/Cylinder/Outline.png"));
		small[0] = ii.getImage();

		arrows = new Image[8];
		ii = new ImageIcon(ImgLoad.class.getResource("res/arrows/nw1.png"));
		arrows[0] = ii.getImage();
		ii = new ImageIcon(ImgLoad.class.getResource("res/arrows/n1.png"));
		arrows[1] = ii.getImage();
		ii = new ImageIcon(ImgLoad.class.getResource("res/arrows/ne1.png"));
		arrows[2] = ii.getImage();
		ii = new ImageIcon(ImgLoad.class.getResource("res/arrows/w1.png"));
		arrows[3] = ii.getImage();
		ii = new ImageIcon(ImgLoad.class.getResource("res/arrows/e1.png"));
		arrows[4] = ii.getImage();
		ii = new ImageIcon(ImgLoad.class.getResource("res/arrows/sw1.png"));
		arrows[5] = ii.getImage();
		ii = new ImageIcon(ImgLoad.class.getResource("res/arrows/s1.png"));
		arrows[6] = ii.getImage();
		ii = new ImageIcon(ImgLoad.class.getResource("res/arrows/se1.png"));
		arrows[7] = ii.getImage();

		/*
		 * health = new Image[25]; ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h0.png")); health[0]
		 * = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h1.png")); health[1]
		 * = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h2.png")); health[2]
		 * = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h3.png")); health[3]
		 * = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h4.png")); health[4]
		 * = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h5.png")); health[5]
		 * = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h6.png")); health[6]
		 * = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h7.png")); health[7]
		 * = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h8.png")); health[8]
		 * = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h9.png")); health[9]
		 * = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h10.png"));
		 * health[10] = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h11.png"));
		 * health[11] = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h12.png"));
		 * health[12] = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h13.png"));
		 * health[13] = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h14.png"));
		 * health[14] = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h15.png"));
		 * health[15] = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h16.png"));
		 * health[16] = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h17.png"));
		 * health[17] = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h18.png"));
		 * health[18] = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h19.png"));
		 * health[19] = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h20.png"));
		 * health[20] = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h21.png"));
		 * health[21] = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h22.png"));
		 * health[22] = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h23.png"));
		 * health[23] = ii.getImage(); ii = new
		 * ImageIcon(ImgLoad.class.getResource("res/Health/h24.png"));
		 * health[24] = ii.getImage();
		 */

	}
}
