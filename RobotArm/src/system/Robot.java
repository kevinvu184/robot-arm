package system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import robotcontrol.RobotControl;

// 
// Decompiled by Procyon v0.5.36
// 

public class Robot extends JPanel {
	private int[] obstacles;
	private int[] blocksDepth;
	private int[] newBlocksDepth;
	private int[] newBlocksDepth2;
	private int[] obBlocks;
	private int rH;
	private int rW;
	private int rD;
	private boolean picked;
	private boolean odd;
	private int topDepth;
	private int itemsHt;
	private int itemsHt2;
	private int itemsHt3;
	private int numItems;
	private int numItems2;
	private int numItems3;
	private static JFrame frame;
	private JPanel robotPanel;
	private int htItems1;
	private int htItems2;
	private int htItems3;
	private int counter;
	private static int numOps;
	private int currentHeight;
	private int numDroped;
	public boolean succcess;
	private static final Color BLOCK_HEIGHT_1;
	private static final Color BLOCK_HEIGHT_2;
	private static final Color BLOCK_HEIGHT_3;
	private static final Color BARS;
	private static final Color CRANE;
	public static boolean assessment;
	public static int numBlocksMoved;
	public static StringBuilder sb;
	public static RobotControl localRobotControl;
	public static String runningScore;

	static {
		Robot.numOps = 0;
		BLOCK_HEIGHT_1 = new Color(240, 141, 76);
		BLOCK_HEIGHT_2 = new Color(94, 129, 181);
		BLOCK_HEIGHT_3 = new Color(49, 153, 133);
		BARS = new Color(206, 235, 253);
		CRANE = new Color(135, 206, 250);
		Robot.numBlocksMoved = 0;
	}

	public void init() {
		Robot.numOps = 0;
	}

	public static double getNumberOfMoves() {
		return Robot.numOps;
	}

	public static void main(final String[] paramArrayOfString) {
		writeToFile("0");
		Robot.numOps = 0;
		Robot.frame = new JFrame("Robot");
		String str;
		if (paramArrayOfString.length == 0) {
			System.out.println("As you did not enter the heights of 6 bars, it is assumed to be 777777");
			str = "777777";
		} else {
			str = paramArrayOfString[0];
		}
		int i = 6;
		int j = 0;
		final int[] arrayOfInt1 = new int[i];
		for (j = 0; j < i; ++j) {
			if (str.length() > j) {
				arrayOfInt1[j] = str.charAt(j) - '0';
			} else {
				arrayOfInt1[j] = 7;
			}
			if (arrayOfInt1[j] < 1 || arrayOfInt1[j] >= 8) {
				System.out.println("Bars must be of height 1 to 7");
				writeToFile("1");
				System.exit(0);
			}
		}
		if (str.length() < 6) {
			System.out.println("Additional bars of height 7 are added as the number of bars entered are less than 6 ");
		}
		if (paramArrayOfString.length < 2) {
			System.out.println("As you did not enter the heights of blocks, it is assumed to be 3333");
			str = "3333";
		} else {
			str = paramArrayOfString[1];
		}
		i = str.length();
		j = 0;
		final int[] arrayOfInt2 = new int[i];
		for (int k = 0; k < i; ++k) {
			arrayOfInt2[k] = str.charAt(k) - '0';
			j += arrayOfInt2[k];
			if (j > 12) {
				System.out.println("Blocks height must not exceed 12");
				writeToFile("1");
				System.exit(0);
			}
			if (arrayOfInt2[k] <= 0 || arrayOfInt2[k] > 3) {
				System.out.println("Blocks must be of height 1 to 3");
				writeToFile("1");
				System.exit(0);
			}
		}
		final Robot localRobot = new Robot(arrayOfInt1, arrayOfInt2);
		final Container localContainer = Robot.frame.getContentPane();
		localContainer.setLayout(new BorderLayout());
		final JPanel topPanel = new JPanel();
		final JPanel bottomPanel = new JPanel();
		final JLabel txt = new JLabel("Robot Assignment (2018 - Semester 1)");
		final Font font = new Font("Verdana", 1, 16);
		txt.setFont(font);
		txt.setForeground(Color.RED);
		topPanel.add(txt);
		final JButton localJButton1 = new JButton("SpeedUp");
		final SomeListener localSomeListener = new SomeListener(localRobot);
		localJButton1.addActionListener(localSomeListener);
		bottomPanel.add(localJButton1);
		Robot.frame.getContentPane().add(topPanel, "North");
		final JButton localJButton2 = new JButton("SlowDown");
		localJButton2.addActionListener(localSomeListener);
		bottomPanel.add(localJButton2);
		Robot.frame.getContentPane().add(bottomPanel, "South");
		Robot.frame.getContentPane().add(localRobot, "Center");
		Robot.frame.setDefaultCloseOperation(2);
		Robot.frame.setSize(640, 550);
		Robot.frame.setVisible(true);
		(Robot.localRobotControl = new RobotControl(localRobot)).control(arrayOfInt1, arrayOfInt2);
	}

	public Robot(final int[] paramArrayOfInt1, final int[] paramArrayOfInt2) {
		this.rH = 2;
		this.rW = 1;
		this.rD = 0;
		this.picked = false;
		this.odd = false;
		this.topDepth = 2;
		this.counter = 64000;
		this.currentHeight = 0;
		this.numDroped = 0;
		this.succcess = false;
		this.obstacles = new int[paramArrayOfInt1.length];
		this.blocksDepth = new int[paramArrayOfInt2.length];
		for (int i = 0; i < paramArrayOfInt2.length; ++i) {
			this.blocksDepth[i] = paramArrayOfInt2[i];
		}
		this.obstacles = new int[paramArrayOfInt1.length];
		this.obBlocks = new int[paramArrayOfInt1.length];
		for (int i = 0; i < paramArrayOfInt1.length; ++i) {
			this.obstacles[i] = paramArrayOfInt1[i];
			this.obBlocks[i] = 0;
		}
		this.numItems = this.blocksDepth.length;
		this.newBlocksDepth = new int[this.numItems];
		this.newBlocksDepth2 = new int[this.numItems];
		this.numItems2 = 0;
	}

	public void pick() {
		++Robot.numOps;
		if (!this.picked && 400 - this.rH * 30 + 30 + this.rD * 30 == this.itemsHt && this.rW == 10
				&& this.numItems > 0) {
			this.topDepth = this.blocksDepth[this.numItems - 1] * 30;
			this.picked = true;
			this.currentHeight = this.blocksDepth[this.numItems - 1];
			if (this.blocksDepth[this.numItems - 1] % 2 == 0) {
				this.odd = false;
			} else {
				this.odd = true;
			}
			--this.numItems;
			Robot.frame.repaint();
		} else if (this.picked) {
			Robot.sb.append(Robot.runningScore);
			System.out.println(Robot.sb.toString());
			this.message("must drop before picking again", "Error in call to pick()");
		} else {
			Robot.sb.append(Robot.runningScore);
			System.out.println(Robot.sb.toString());
			this.message("Can pick only from top of column 10 ", "Error in call to pick()");
		}
	}

	public void message(final String paramString1, final String paramString2) {
		JOptionPane.showMessageDialog(null, paramString1, paramString2, 1);
		writeToFile("1");
		System.exit(0);
	}

	public void drop() {
		writeToFile("1");
		++Robot.numOps;
		if (this.picked && this.currentHeight == 1
				&& 400 - this.rH * 30 + 30 + this.rD * 30 + this.topDepth == this.itemsHt2 && this.rW == 1) {
			this.picked = false;
			this.newBlocksDepth[this.numItems2++] = this.blocksDepth[this.numItems];
		} else if (this.picked && this.currentHeight == 2
				&& 400 - this.rH * 30 + 30 + this.rD * 30 + this.topDepth == this.itemsHt3 && this.rW == 2) {
			this.picked = false;
			this.newBlocksDepth2[this.numItems3++] = this.blocksDepth[this.numItems];
		} else if (this.picked && this.currentHeight == 3 && this.rW > 2 && this.rW < 9
				&& 400 - this.rH * 30 + 30 + this.rD * 30 + this.topDepth == 400
						- (this.obstacles[this.rW - 3] + this.obBlocks[this.rW - 3] * 3) * 30
				&& this.obBlocks[this.rW - 3] == 0) {
			this.picked = false;
			this.obBlocks[this.rW - 3] = 1;
		} else if (!this.picked) {
			Robot.sb.append(Robot.runningScore);
			System.out.println(Robot.sb.toString());
			this.message("must pick item before dropping", "Error in call to drop()");
		} else if (this.currentHeight == 1) {
			Robot.sb.append(Robot.runningScore);
			System.out.println(Robot.sb.toString());
			this.message("Block size 1 can be dropped only on top of col 1 ", "Error in call to drop()");
		} else if (this.currentHeight == 2) {
			Robot.sb.append(Robot.runningScore);
			System.out.println(Robot.sb.toString());
			this.message("Block size 2 can be dropped only on top of col 2 ", "Error in call to drop()");
		} else if (this.currentHeight == 3) {
			Robot.sb.append(Robot.runningScore);
			System.out.println(Robot.sb.toString());
			this.message("Block size 3 can be dropped only on top of cols 3 - 8 only", "Error in call to drop()");
		}
		++this.numDroped;
		if (this.blocksDepth.length == this.numDroped) {
			writeToFile("0");
		} else {
			writeToFile("1");
		}
		++Robot.numBlocksMoved;
	}

	public void delay(final int paramInt) {
		final double d = 15.45;
		try {
			Thread.sleep(paramInt * 10 * this.counter / 64000);
		} catch (Exception ex) {
		}
	}

	public void speedUp(final int paramInt) {
		this.counter /= paramInt;
	}

	public void slowDown(final int paramInt) {
		this.counter *= paramInt;
	}

	public void up() {
		++Robot.numOps;
		this.delay(10);
		if (this.rH >= 14) {
			Robot.sb.append(Robot.runningScore);
			System.out.println(Robot.sb.toString());
			this.message("Robot height cannot be greater than 14", "Error in call to up()");
		} else {
			++this.rH;
			Robot.frame.repaint();
		}
	}

	public void down() {
		RobotControl.sb = Robot.sb;
		++Robot.numOps;
		this.delay(10);
		int i = this.rH - this.rD;
		if (this.picked) {
			i -= this.blocksDepth[this.numItems];
		}
		if (this.rH <= 2) {
			Robot.sb.append(Robot.runningScore);
			System.out.println(Robot.sb.toString());
			this.message("Beyond limit: h must be greater than 1", "Error in call to down()");
		} else if (!this.checkValid(this.rW, i - 1)) {
			Robot.sb.append(Robot.runningScore);
			System.out.println(Robot.sb.toString());
			this.message("Obstacle found", "Error in call to down()");
		} else {
			for (int j = 1; j < this.rW; ++j) {
				if (!this.checkValid(j, this.rH - 1)) {
					Robot.sb.append(Robot.runningScore);
					System.out.println(Robot.sb.toString());
					this.message("Obstacle found", "Error in call to down()");
				}
			}
		}
		--this.rH;
		Robot.frame.repaint();
	}

	public void contract() {
		++Robot.numOps;
		this.delay(10);
		int i = this.rH - this.rD;
		if (this.picked) {
			i -= this.blocksDepth[this.numItems];
		}
		if (this.rW <= 1) {
			Robot.sb.append(Robot.runningScore);
			System.out.println(Robot.sb.toString());
			this.message("Robot width cannot be less than 1", "Error in call to contract()");
		} else if (!this.checkValid(this.rW - 1, i)) {
			Robot.sb.append(Robot.runningScore);
			System.out.println(Robot.sb.toString());
			this.message("hit obstacle", "Error in call to contract()");
		} else {
			--this.rW;
			Robot.frame.repaint();
		}
	}

	public void extend() {
		++Robot.numOps;
		this.delay(10);
		int i = this.rH - this.rD;
		if (this.picked) {
			i -= this.blocksDepth[this.numItems];
		}
		if (this.rW >= 10) {
			Robot.sb.append(Robot.runningScore);
			System.out.println(Robot.sb.toString());
			this.message("Robot width cannot be greater than 10", "Error in call to extend()");
		} else if (!this.checkValid(this.rW + 1, i)) {
			Robot.sb.append(Robot.runningScore);
			System.out.println(Robot.sb.toString());
			this.message("hit obstacle", "Error in call to extend()");
		} else {
			++this.rW;
			Robot.frame.repaint();
		}
	}

	public boolean checkValid(final int paramInt1, final int paramInt2) {
		return this.getHeight(paramInt1) < paramInt2;
	}

	public int getHeight(final int paramInt) {
		if (paramInt > 2 && paramInt < 9 && paramInt - 3 < this.obstacles.length) {
			return this.obstacles[paramInt - 3] + this.obBlocks[paramInt - 3] * 3;
		}
		if (paramInt == 10) {
			return this.htItems1;
		}
		if (paramInt == 1) {
			return this.htItems2;
		}
		if (paramInt == 2) {
			return this.htItems3;
		}
		return 0;
	}

	public void lower() {
		++Robot.numOps;
		this.delay(10);
		int i = this.rH - this.rD;
		if (this.picked) {
			i -= this.blocksDepth[this.numItems];
		}
		if (this.rD >= this.rH - 1) {
			Robot.sb.append(Robot.runningScore);
			System.out.println(Robot.sb.toString());
			this.message("Beyond limit: d must be less than h", "Error in call to lower()");
		} else if (!this.checkValid(this.rW, i - 1)) {
			Robot.sb.append(Robot.runningScore);
			System.out.println(Robot.sb.toString());
			this.message("Obstacle found", "Error in call to lower()");
		}
		++this.rD;
		Robot.frame.repaint();
	}

	public void raise() {
		++Robot.numOps;
		this.delay(10);
		if (this.rD <= 0) {
			Robot.sb.append(Robot.runningScore);
			System.out.println(Robot.sb.toString());
			this.message("Beyond limit: d cannot be lessthan 0", "Error in call to raise()");
		} else {
			--this.rD;
			Robot.frame.repaint();
		}
	}

	public void paintComponent(final Graphics paramGraphics) {
		super.paintComponent(paramGraphics);
		paramGraphics.drawLine(0, 400, 600, 400);
		paramGraphics.setColor(Robot.BLOCK_HEIGHT_1);
		paramGraphics.drawLine(100, 401, 150, 401);
		paramGraphics.setColor(Robot.BLOCK_HEIGHT_2);
		paramGraphics.drawLine(150, 401, 200, 401);
		paramGraphics.setColor(Color.black);
		for (int i = 0; i < this.obstacles.length; ++i) {
			this.drawObject(paramGraphics, i, this.obstacles[i], this.obBlocks[i] * 3);
		}
		this.drawRobot(paramGraphics);
		this.drawItems(paramGraphics);
		paramGraphics.setColor(Color.black);
		paramGraphics.drawString("Target", 105, 421);
		paramGraphics.drawString("Size 1", 105, 432);
		paramGraphics.drawString("Target", 155, 421);
		paramGraphics.drawString("Size 2", 155, 432);
		paramGraphics.drawString("Source", 555, 425);
		paramGraphics.drawString("Column", 50, 412);
		for (int i = 1; i <= 10; ++i) {
			paramGraphics.drawString(new StringBuilder().append(i).toString(), 65 + i * 50, 412);
		}
		paramGraphics.drawString("Total Robot Moves = " + Robot.numOps, 280, 425);
	}

	public void drawObject(final Graphics paramGraphics, final int paramInt1, final int paramInt2,
			final int paramInt3) {
		if (paramInt2 > 0) {
			paramGraphics.setColor(Color.black);
			paramGraphics.drawRoundRect(200 + paramInt1 * 50 + 1, 400 - paramInt2 * 30, 48, paramInt2 * 30, 10, 10);
			paramGraphics.setColor(Robot.BARS);
			paramGraphics.fillRoundRect(200 + paramInt1 * 50 + 1, 400 - paramInt2 * 30, 48, paramInt2 * 30, 10, 10);
			paramGraphics.setColor(Color.black);
			paramGraphics.drawString("Bar", 210 + paramInt1 * 50, 415 - paramInt2 * 30);
			paramGraphics.drawString("Ht=" + paramInt2, 205 + paramInt1 * 50, 425 - paramInt2 * 30);
		}
		if (paramInt3 > 0) {
			paramGraphics.setColor(Color.black);
			paramGraphics.drawRect(200 + paramInt1 * 50 + 1, 400 - (paramInt2 + paramInt3) * 30, 48, paramInt3 * 30);
			paramGraphics.setColor(Robot.BLOCK_HEIGHT_3);
			paramGraphics.fillRect(200 + paramInt1 * 50 + 1, 400 - (paramInt2 + paramInt3) * 30, 48, paramInt3 * 30);
			paramGraphics.setColor(Color.black);
			paramGraphics.drawString(new StringBuilder().append(paramInt3).toString(), 220 + paramInt1 * 50,
					420 - (paramInt2 + paramInt3) * 30);
		}
	}

	public void drawItems(final Graphics paramGraphics) {
		this.itemsHt = 400;
		this.htItems1 = 0;
		for (int i = 0; i < this.numItems; ++i) {
			this.itemsHt -= this.blocksDepth[i] * 30;
			this.htItems1 += this.blocksDepth[i];
			if (this.blocksDepth[i] == 2) {
				paramGraphics.setColor(Robot.BLOCK_HEIGHT_2);
			} else if (this.blocksDepth[i] == 1) {
				paramGraphics.setColor(Robot.BLOCK_HEIGHT_1);
			} else {
				paramGraphics.setColor(Robot.BLOCK_HEIGHT_3);
			}
			paramGraphics.fillRect(550, this.itemsHt, 50, this.blocksDepth[i] * 30);
			paramGraphics.setColor(Color.black);
			paramGraphics.drawRect(550, this.itemsHt, 50, this.blocksDepth[i] * 30);
		}
		paramGraphics.setColor(Color.black);
		this.itemsHt = 400;
		this.htItems1 = 0;
		for (int i = 0; i < this.numItems; ++i) {
			this.itemsHt -= this.blocksDepth[i] * 30;
			this.htItems1 += this.blocksDepth[i];
			paramGraphics.drawString(new StringBuilder().append(this.blocksDepth[i]).toString(), 570,
					this.itemsHt + this.blocksDepth[i] * 15);
		}
		this.htItems2 = 0;
		this.itemsHt2 = 400;
		for (int i = 0; i < this.numItems2; ++i) {
			this.itemsHt2 -= this.newBlocksDepth[i] * 30;
			this.htItems2 += this.newBlocksDepth[i];
			if (this.newBlocksDepth[i] == 2) {
				paramGraphics.setColor(Robot.BLOCK_HEIGHT_2);
			} else if (this.newBlocksDepth[i] == 1) {
				paramGraphics.setColor(Robot.BLOCK_HEIGHT_1);
			} else {
				paramGraphics.setColor(Robot.BLOCK_HEIGHT_3);
			}
			paramGraphics.fillRect(100, this.itemsHt2, 50, this.newBlocksDepth[i] * 30);
			paramGraphics.setColor(Color.black);
			paramGraphics.drawRect(100, this.itemsHt2, 50, this.newBlocksDepth[i] * 30);
			paramGraphics.drawString(new StringBuilder().append(this.newBlocksDepth[i]).toString(), 120,
					this.itemsHt2 + this.newBlocksDepth[i] * 15);
		}
		this.htItems3 = 0;
		this.itemsHt3 = 400;
		for (int i = 0; i < this.numItems3; ++i) {
			this.itemsHt3 -= this.newBlocksDepth2[i] * 30;
			this.htItems3 += this.newBlocksDepth2[i];
			if (this.newBlocksDepth2[i] == 2) {
				paramGraphics.setColor(Robot.BLOCK_HEIGHT_2);
			} else if (this.newBlocksDepth2[i] == 1) {
				paramGraphics.setColor(Robot.BLOCK_HEIGHT_1);
			} else {
				paramGraphics.setColor(Robot.BLOCK_HEIGHT_3);
			}
			paramGraphics.fillRect(150, this.itemsHt3, 50, this.newBlocksDepth2[i] * 30);
			paramGraphics.setColor(Color.black);
			paramGraphics.drawRect(150, this.itemsHt3, 50, this.newBlocksDepth2[i] * 30);
			paramGraphics.drawString(new StringBuilder().append(this.newBlocksDepth2[i]).toString(), 170,
					this.itemsHt3 + this.newBlocksDepth2[i] * 15);
		}
	}

	public void drawRobot(final Graphics paramGraphics) {
		paramGraphics.setColor(Robot.CRANE);
		paramGraphics.fillRect(50, 400 - this.rH * 30, 50, this.rH * 30);
		paramGraphics.setColor(Color.black);
		paramGraphics.drawRect(52, 400 - this.rH * 30 + 2, 46, this.rH * 30 - 4);
		paramGraphics.drawString("h=" + this.rH, 60, 400 - this.rH * 15 - 10);
		paramGraphics.setColor(Robot.CRANE);
		paramGraphics.fillRect(100, 400 - this.rH * 30, this.rW * 50, 30);
		paramGraphics.setColor(Color.black);
		paramGraphics.drawString("w=" + this.rW, 100 + this.rW * 25 - 8, 400 - this.rH * 30 + 18);
		paramGraphics.drawRect(102, 400 - this.rH * 30 + 2, this.rW * 50 - 4, 26);
		if (this.rD > 0) {
			paramGraphics.setColor(Robot.CRANE);
			paramGraphics.fillRect(50 + this.rW * 50, 400 - this.rH * 30 + 30, 50, this.rD * 30);
			paramGraphics.setColor(Color.black);
			paramGraphics.drawRect(50 + this.rW * 50 + 2, 400 - this.rH * 30 + 30 + 2, 46, this.rD * 30 - 4);
			paramGraphics.drawString("d=" + this.rD, 60 + this.rW * 50, 400 - this.rH * 30 + 30 + this.rD * 15 + 10);
		}
		paramGraphics.setColor(Robot.CRANE);
		if (this.picked) {
			if (this.currentHeight == 2) {
				paramGraphics.setColor(Robot.BLOCK_HEIGHT_2);
			} else if (this.currentHeight == 1) {
				paramGraphics.setColor(Robot.BLOCK_HEIGHT_1);
			} else {
				paramGraphics.setColor(Robot.BLOCK_HEIGHT_3);
			}
			paramGraphics.fillRect(50 + this.rW * 50, 400 - this.rH * 30 + this.rD * 30 + 30, 50, this.topDepth);
			paramGraphics.setColor(Color.black);
			paramGraphics.fillRect(50 + this.rW * 50, 400 - this.rH * 30 + this.rD * 30 + 30 - 6, 4, 12);
			paramGraphics.fillRect(50 + this.rW * 50 + 50 - 4, 400 - this.rH * 30 + this.rD * 30 + 30 - 6, 4, 12);
			paramGraphics.setColor(Color.black);
			paramGraphics.drawRect(50 + this.rW * 50, 400 - this.rH * 30 + this.rD * 30 + 30, 50, this.topDepth);
			paramGraphics.drawString(new StringBuilder().append(this.currentHeight).toString(), 70 + this.rW * 50,
					400 - this.rH * 30 + this.rD * 30 + 30 + this.currentHeight * 15);
		}
	}

	private static void writeToFile(final String score) {
		final File file = new File("success.txt");
		FileWriter fw = null;
		try {
			fw = new FileWriter(file, false);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		final BufferedWriter bw = new BufferedWriter(fw);
		try {
			bw.write(score);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		try {
			bw.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
}
