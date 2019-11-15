package system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// 
// Decompiled by Procyon v0.5.36
// 

public class Menu {
	final String[] STAGE_A_TEST_1_CONFIG;
	final String[] STAGE_A_TEST_1_CONFIG_MOVES;
	final String[] STAGE_B_TEST_1_CONFIG;
	final String[] STAGE_B_TEST_1_CONFIG_MOVES;
	final String[] STAGE_B_TEST_2_CONFIG;
	final String[] STAGE_B_TEST_2_CONFIG_MOVES;
	final String[] STAGE_B_TEST_3_CONFIG;
	final String[] STAGE_B_TEST_3_CONFIG_MOVES;
	final String[] STAGE_B_TEST_4_CONFIG;
	final String[] STAGE_B_TEST_4_CONFIG_MOVES;
	final String[] STAGE_C_TEST_1_CONFIG;
	final String[] STAGE_C_TEST_1_CONFIG_MOVES;
	final String[] STAGE_C_TEST_2_CONFIG;
	final String[] STAGE_C_TEST_2_CONFIG_MOVES;
	final String[] STAGE_C_TEST_3_CONFIG;
	final String[] STAGE_C_TEST_3_CONFIG_MOVES;
	final String[] STAGE_C_TEST_4_CONFIG;
	final String[] STAGE_C_TEST_4_CONFIG_MOVES;
	final String[] STAGE_C_TEST_5_CONFIG;
	final String[] STAGE_C_TEST_5_CONFIG_MOVES;
	final Scanner sc;
	private double score;
	private double numMoves;
	private int numberOfTestsPassed;
	StringBuilder sb;

	public Menu() {
		this.STAGE_A_TEST_1_CONFIG = new String[] { "777777", "3333" };
		this.STAGE_A_TEST_1_CONFIG_MOVES = new String[] { "94", "103", "113", "124" };
		this.STAGE_B_TEST_1_CONFIG = new String[] { "734561", "3333" };
		this.STAGE_B_TEST_1_CONFIG_MOVES = new String[] { "89", "98", "109", "120" };
		this.STAGE_B_TEST_2_CONFIG = new String[] { "137561", "3333" };
		this.STAGE_B_TEST_2_CONFIG_MOVES = new String[] { "105", "116" };
		this.STAGE_B_TEST_3_CONFIG = new String[] { "171717", "3333" };
		this.STAGE_B_TEST_3_CONFIG_MOVES = new String[] { "109", "120" };
		this.STAGE_B_TEST_4_CONFIG = new String[] { "137652", "3333" };
		this.STAGE_B_TEST_4_CONFIG_MOVES = new String[] { "106", "117" };
		this.STAGE_C_TEST_1_CONFIG = new String[] { "734561", "231231" };
		this.STAGE_C_TEST_1_CONFIG_MOVES = new String[] { "223", "245" };
		this.STAGE_C_TEST_2_CONFIG = new String[] { "222222", "321113" };
		this.STAGE_C_TEST_2_CONFIG_MOVES = new String[] { "170", "187" };
		this.STAGE_C_TEST_3_CONFIG = new String[] { "444444", "311123" };
		this.STAGE_C_TEST_3_CONFIG_MOVES = new String[] { "194", "213" };
		this.STAGE_C_TEST_4_CONFIG = new String[] { "777777", "222111111" };
		this.STAGE_C_TEST_4_CONFIG_MOVES = new String[] { "301", "331" };
		this.STAGE_C_TEST_5_CONFIG = new String[] { "676767", "1332" };
		this.STAGE_C_TEST_5_CONFIG_MOVES = new String[] { "132", "145" };
		this.sc = new Scanner(System.in);
		this.numberOfTestsPassed = 0;
		this.sb = new StringBuilder();
	}

	public void runMenu() {
		Robot.sb = this.sb;
		Robot.runningScore = String.valueOf(this.score);
		int response;
		do {
			this.printMenu();
			System.out.print("Enter selection: ");
			while (!this.sc.hasNextInt()) {
				this.printMenu();
				this.sc.next();
			}
			response = Integer.parseInt(this.sc.nextLine());
			System.out.println();
			switch (response) {
			case 1: {
				this.runStageATest(this.sb);
				System.out.println("Press any key to return to the menu");
				this.sc.nextLine();
				break;
			}
			case 2: {
				this.runStageBTest1(this.sb);
				System.out.println("Press any key to return to the menu");
				this.sc.nextLine();
				break;
			}
			case 3: {
				this.runStageBTest2(this.sb);
				System.out.println("Press any key to return to the menu");
				this.sc.nextLine();
				break;
			}
			case 4: {
				this.runStageBTest3(this.sb);
				System.out.println("Press any key to return to the menu");
				this.sc.nextLine();
				break;
			}
			case 5: {
				this.runStageBTest4(this.sb);
				System.out.println("Press any key to return to the menu");
				this.sc.nextLine();
				break;
			}
			case 6: {
				this.runStageCTest1(this.sb);
				System.out.println("Press any key to return to the menu");
				this.sc.nextLine();
				break;
			}
			case 7: {
				this.runStageCTest2(this.sb);
				System.out.println("Press any key to return to the menu");
				this.sc.nextLine();
				break;
			}
			case 8: {
				this.runStageCTest3(this.sb);
				System.out.println("Press any key to return to the menu");
				this.sc.nextLine();
				break;
			}
			case 9: {
				this.runStageCTest4(this.sb);
				System.out.println("Press any key to return to the menu");
				this.sc.nextLine();
				break;
			}
			case 10: {
				this.runCustomTest();
				System.out.println("Press any key to return to the menu");
				this.sc.nextLine();
				break;
			}
			case 11: {
				this.runAllTests();
				System.exit(0);
			}
			case 12: {
				break;
			}
			default: {
				System.out.println("Error - invalid selection!");
				break;
			}
			}
			System.out.println();
		} while (response != 0);
	}

	public void runStageATest(final StringBuilder sb) {
		final double maxMarks = 1.0;
		System.out.println("Running Stage A Test - bars = " + this.STAGE_A_TEST_1_CONFIG[0]
				+ ", blocks = 3333 (default bar / block config)");
		System.out.println("Minimum move count: " + this.STAGE_A_TEST_1_CONFIG_MOVES[0]);
		Robot.main(this.STAGE_A_TEST_1_CONFIG);
		System.out.println("You completed the task in: " + (int) (Robot.getNumberOfMoves() - 1.0) + " moves.");
		final double success = this.readFromFile("success.txt");
		final double[] marks = { 0.25, 0.5, 0.75, 1.0 };
		if (sb != null && success != 1.0) {
			this.updateScoreForTest(sb, "A", 1.5, this.STAGE_A_TEST_1_CONFIG_MOVES, Robot.numBlocksMoved, marks);
			Robot.numBlocksMoved = 0;
		}
	}

	public void runStageBTest1(final StringBuilder sb) {
		sb.append("*************************************************\n");
		sb.append("PART B - RESULTS\n");
		sb.append("*************************************************\n");
		final double maxMarks = 1.0;
		System.out.println("Running Stage B Test - bars = " + this.STAGE_B_TEST_1_CONFIG[0]
				+ ", blocks = 3333 (default bar / block config)");
		System.out.println("Minimum move count: " + this.STAGE_B_TEST_1_CONFIG_MOVES[0]);
		Robot.main(this.STAGE_B_TEST_1_CONFIG);
		System.out.println("You completed the task in: " + (int) (Robot.getNumberOfMoves() - 1.0) + " moves.");
		final double success = this.readFromFile("success.txt");
		final double[] marks = { 0.25, 0.5, 0.75, 1.0 };
		if (sb != null && success != 1.0) {
			this.updateScoreForOtherTests(sb, "B", 0.25, this.STAGE_B_TEST_1_CONFIG_MOVES, Robot.numBlocksMoved, marks,
					1);
			Robot.numBlocksMoved = 0;
		}
	}

	public void runStageBTest2(final StringBuilder sb) {
		System.out.println("Running Stage B Test - bars = " + this.STAGE_B_TEST_2_CONFIG[0]
				+ ", blocks = 3333 (default block config)");
		System.out.println("Minimum move count: " + this.STAGE_B_TEST_2_CONFIG_MOVES[0]);
		Robot.main(this.STAGE_B_TEST_2_CONFIG);
		System.out.println("You completed the task in: " + (int) (Robot.getNumberOfMoves() - 1.0) + " moves.");
		final double success = this.readFromFile("success.txt");
		final double[] marks = { 0.25, 0.5, 0.75, 1.0 };
		if (sb != null && success != 1.0) {
			this.updateScoreForOtherTests(sb, "B", 0.25, this.STAGE_B_TEST_2_CONFIG_MOVES, Robot.numBlocksMoved, marks,
					2);
			Robot.numBlocksMoved = 0;
		}
	}

	public void runStageBTest3(final StringBuilder sb) {
		System.out.println("Running Stage B Test - bars = " + this.STAGE_B_TEST_3_CONFIG[0]
				+ ", blocks = 3333 (default block config)");
		System.out.println("Minimum move count: " + this.STAGE_B_TEST_3_CONFIG_MOVES[0]);
		Robot.main(this.STAGE_B_TEST_3_CONFIG);
		System.out.println("You completed the task in: " + (int) (Robot.getNumberOfMoves() - 1.0) + " moves.");
		final double success = this.readFromFile("success.txt");
		final double[] marks = { 0.25, 0.5, 0.75, 1.0 };
		if (sb != null && success != 1.0) {
			this.updateScoreForOtherTests(sb, "B", 0.25, this.STAGE_B_TEST_3_CONFIG_MOVES, Robot.numBlocksMoved, marks,
					3);
			Robot.numBlocksMoved = 0;
		}
	}

	public void runStageBTest4(final StringBuilder sb) {
		System.out.println("Running Stage B Test - bars = " + this.STAGE_B_TEST_4_CONFIG[0]
				+ ", blocks = 3333 (default block config)");
		System.out.println("Minimum move count: " + this.STAGE_B_TEST_4_CONFIG_MOVES[0]);
		Robot.main(this.STAGE_B_TEST_4_CONFIG);
		System.out.println("You completed the task in: " + (int) (Robot.getNumberOfMoves() - 1.0) + " moves.");
		final double success = this.readFromFile("success.txt");
		final double[] marks = { 0.25, 0.5, 0.75, 1.0 };
		if (sb != null && success != 1.0) {
			this.updateScoreForOtherTests(sb, "B", 0.25, this.STAGE_B_TEST_4_CONFIG_MOVES, Robot.numBlocksMoved, marks,
					4);
			Robot.numBlocksMoved = 0;
		}
	}

	public void runStageCTest1(final StringBuilder sb) {
		sb.append("*************************************************\n");
		sb.append("PART C - RESULTS\n");
		sb.append("*************************************************\n");
		System.out.println("Running Stage C Test 1 - bars = " + this.STAGE_C_TEST_1_CONFIG[0] + ", blocks = "
				+ this.STAGE_C_TEST_1_CONFIG[0]);
		System.out.println("Minimum move count: " + this.STAGE_C_TEST_1_CONFIG_MOVES[0]);
		Robot.main(this.STAGE_C_TEST_1_CONFIG);
		System.out.println("You completed the task in: " + (int) (Robot.getNumberOfMoves() - 1.0) + " moves.");
		final double success = this.readFromFile("success.txt");
		final double[] marks = { 0.25, 0.5, 0.75, 1.0 };
		if (sb != null && success != 1.0) {
			this.updateScoreForOtherTests(sb, "B", 0.25, this.STAGE_C_TEST_1_CONFIG_MOVES, Robot.numBlocksMoved, marks,
					1);
			Robot.numBlocksMoved = 0;
		}
	}

	public void runStageCTest2(final StringBuilder sb) {
		System.out.println("Running Stage C Test 2 - bars = " + this.STAGE_C_TEST_2_CONFIG[0] + ", blocks = "
				+ this.STAGE_C_TEST_2_CONFIG[1]);
		System.out.println("Minimum move count: " + this.STAGE_C_TEST_2_CONFIG_MOVES[0]);
		Robot.main(this.STAGE_C_TEST_2_CONFIG);
		System.out.println("You completed the task in: " + (int) (Robot.getNumberOfMoves() - 1.0) + " moves.");
		final double success = this.readFromFile("success.txt");
		final double[] marks = { 0.25, 0.5, 0.75, 1.0 };
		if (sb != null && success != 1.0) {
			this.updateScoreForOtherTests(sb, "B", 0.25, this.STAGE_C_TEST_2_CONFIG_MOVES, Robot.numBlocksMoved, marks,
					2);
			Robot.numBlocksMoved = 0;
		}
	}

	public void runStageCTest3(final StringBuilder sb) {
		System.out.println("Running Stage C Test 3 - bars = " + this.STAGE_C_TEST_3_CONFIG[0] + ", blocks = "
				+ this.STAGE_C_TEST_3_CONFIG[1]);
		System.out.println("Minimum move count: " + this.STAGE_C_TEST_3_CONFIG_MOVES[0]);
		Robot.main(this.STAGE_C_TEST_3_CONFIG);
		System.out.println("You completed the task in: " + (int) (Robot.getNumberOfMoves() - 1.0) + " moves.");
		final double success = this.readFromFile("success.txt");
		final double[] marks = { 0.25, 0.5, 0.75, 1.0 };
		if (sb != null && success != 1.0) {
			this.updateScoreForOtherTests(sb, "B", 0.25, this.STAGE_C_TEST_3_CONFIG_MOVES, Robot.numBlocksMoved, marks,
					3);
			Robot.numBlocksMoved = 0;
		}
	}

	public void runStageCTest4(final StringBuilder sb) {
		System.out.println("Running Stage C Test 4 - bars = " + this.STAGE_C_TEST_4_CONFIG[0] + ", blocks = "
				+ this.STAGE_C_TEST_4_CONFIG[1]);
		System.out.println("Minimum move count: " + this.STAGE_C_TEST_4_CONFIG_MOVES[0]);
		Robot.main(this.STAGE_C_TEST_4_CONFIG);
		System.out.println("You completed the task in: " + (int) (Robot.getNumberOfMoves() - 1.0) + " moves.");
		final double success = this.readFromFile("success.txt");
		final double[] marks = { 0.25, 0.5, 0.75, 1.0 };
		if (sb != null && success != 1.0) {
			this.updateScoreForOtherTests(sb, "B", 0.25, this.STAGE_C_TEST_4_CONFIG_MOVES, Robot.numBlocksMoved, marks,
					4);
			Robot.numBlocksMoved = 0;
		}
	}

	public void runStageCTest5(final StringBuilder sb) {
		System.out.println("Running Stage C Test 5 - bars = 734561, blocks = 1332");
		System.out.println("Minimum move count: " + this.STAGE_C_TEST_5_CONFIG_MOVES[0]);
		Robot.main(this.STAGE_C_TEST_5_CONFIG);
		System.out.println("You completed the task in: " + (int) (Robot.getNumberOfMoves() - 1.0) + " moves.");
		final double success = this.readFromFile("success.txt");
		if (sb != null && success != 1.0) {
			sb.append("0.50 marks - You completed scenario 5 for part C\n");
			this.updateScore(sb, "C", 0.5, this.STAGE_C_TEST_5_CONFIG_MOVES);
		}
		Robot.numBlocksMoved = 0;
	}

	public void runCustomTest() {
		System.out.print("Running custom test - please enter bar and block heights below: ");
		final Scanner scCustom = new Scanner(System.in);
		final String configInput = scCustom.nextLine();
		System.out.println();
		final String[] configArgs = configInput.split(" ");
		System.out.println("Running robot with config bars = "
				+ ((configArgs[0].length() == 0) ? "{7,7,7,7,7,7} (default)" : configArgs[0]) + ", blocks: "
				+ ((configArgs.length == 1) ? "{3,3,3,3} (default)" : configArgs[1]));
		System.out.println();
		Robot.main(configArgs);
	}

	public void runAllTests() {
		Robot.assessment = true;
		this.writeToFile("0.0");
		System.out.println("Running all tests - hit enter after each test is complete to contine:");
		System.out.println();
		this.runStageATest(this.sb);
		System.out.println();
		this.runStageBTest1(this.sb);
		System.out.println();
		this.runStageBTest2(this.sb);
		System.out.println();
		this.runStageBTest3(this.sb);
		System.out.println();
		this.runStageBTest4(this.sb);
		System.out.println();
		this.runStageCTest1(this.sb);
		System.out.println();
		this.runStageCTest2(this.sb);
		System.out.println();
		this.runStageCTest3(this.sb);
		System.out.println();
		this.runStageCTest4(this.sb);
		System.out.println();
		System.out.println(this.sb.toString());
		System.out.println("*********************************************");
		System.out.println("PLEASE NOTE THESE MARKS ARE INDICATIVE ONLY");
		System.out.println("YOUR SUBMISSION WILL BE CHECKED MANUALLY");
		System.out.println("*********************************************");
		System.out.println("Your functional score is: " + this.readFromFile("score.txt") + " out of 10");
		System.out.println("Your submission will also be assessed for code quality and checked manually.");
	}

	public void printMenu() {
		System.out.println("************************** ROBOT TEST HARNESS MENU **************************");
		System.out.println();
		System.out.println("IMPORTANT: YOU MUST PASS EACH TEST BEFORE MOVING ON TO THE NEXT TEST");
		System.out
				.println("For example to be awarded marks in Part C, you must first pass all scenarios \nfor Part B.");
		System.out.println("IMPORTANT: ANY TEST AFTER A FAILED TEST WILL NOT BE ASSESSED.");
		System.out.println("******************************************************************************");
		System.out.println();
		System.out.println("1. Stage A Test 1 - bars = " + this.STAGE_A_TEST_1_CONFIG[0] + ", blocks = "
				+ this.STAGE_A_TEST_1_CONFIG[1] + "\n");
		System.out.println("2. Stage B Test 1 - bars = " + this.STAGE_B_TEST_1_CONFIG[0] + ", blocks = "
				+ this.STAGE_B_TEST_1_CONFIG[1] + "\n");
		System.out.println("3. Stage B Test 2 - bars = " + this.STAGE_B_TEST_2_CONFIG[0] + ", blocks = "
				+ this.STAGE_B_TEST_2_CONFIG[1] + "\n");
		System.out.println("4. Stage B Test 3 - bars = " + this.STAGE_B_TEST_3_CONFIG[0] + ", blocks = "
				+ this.STAGE_B_TEST_3_CONFIG[1] + "\n");
		System.out.println("5. Stage B Test 4 - bars = " + this.STAGE_B_TEST_4_CONFIG[0] + ", blocks = "
				+ this.STAGE_B_TEST_4_CONFIG[1] + "\n");
		System.out.println("6. Stage C Test 1 - bars = " + this.STAGE_C_TEST_1_CONFIG[0] + ", blocks = "
				+ this.STAGE_C_TEST_1_CONFIG[1] + "\n");
		System.out.println("7. Stage C Test 2 - bars = " + this.STAGE_C_TEST_2_CONFIG[0] + ", blocks = "
				+ this.STAGE_C_TEST_2_CONFIG[1] + "\n");
		System.out.println("8. Stage C Test 3 - bars = " + this.STAGE_C_TEST_3_CONFIG[0] + ", blocks = "
				+ this.STAGE_C_TEST_3_CONFIG[1] + "\n");
		System.out.println("9. Stage C Test 4 - bars = " + this.STAGE_C_TEST_4_CONFIG[0] + ", blocks = "
				+ this.STAGE_C_TEST_4_CONFIG[1] + "\n");
		System.out.println("10. Stage C Test Custom (user supplies bar / block config)\n");
		System.out.println("11. Run All Tests (1 - 9)\n");
		System.out.println("0. Exit Test Harness\n");
		System.out.println("IMPORTANT: YOU MUST PASS EACH TEST BEFORE MOVING ON TO THE NEXT TEST");
		System.out.println("IMPORTANT: ANY TEST AFTER A FAILED TEST WILL NOT BE ASSESSED.");
		System.out.println();
	}

	private Double readFromFile(final String filename) {
		final File f = new File(filename);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (f.exists()) {
			f.isDirectory();
		}
		FileReader r = null;
		try {
			r = new FileReader(f);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		final BufferedReader b = new BufferedReader(r);
		String out = "";
		String k = "";
		try {
			while ((out = b.readLine()) != null) {
				k = String.valueOf(k) + out;
			}
		} catch (IOException e3) {
			e3.printStackTrace();
		}
		try {
			b.close();
		} catch (IOException e3) {
			e3.printStackTrace();
		}
		if (k.equals("")) {
			return 0.0;
		}
		return Double.parseDouble(k);
	}

	private void writeToFile(final String score) {
		final File file = new File("score.txt");
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

	private boolean updateScore(final StringBuilder sb, final String part, final double testPassed,
			final String[] moves) {
		this.numMoves = Robot.getNumberOfMoves() - 1.0;
		this.score = this.readFromFile("score.txt");
		this.score += testPassed;
		Robot.runningScore = String.valueOf(this.score);
		this.writeToFile(String.valueOf(Math.round(this.score)));
		return true;
	}

	private void updateScoreForOtherTests(final StringBuilder sb, final String part, final double testPassed,
			final String[] moves, final double robotMoves, final double[] marks, final int testNumber) {
		this.numMoves = Robot.getNumberOfMoves() - 1.0;
		this.score = this.readFromFile("score.txt");
		this.score += testPassed;
		Robot.runningScore = String.valueOf(this.score);
		sb.append(String.valueOf(testPassed) + " marks - You successfully completed test number " + testNumber + ".\n");
		if (this.numMoves <= Integer.parseInt(moves[0])) {
			this.score += 0.25;
			sb.append("0.25 marks - You completed the task in " + (int) this.numMoves
					+ ".  \nYou achieved the minimum moves of " + moves[0] + " for part " + part + "\n");
		}
		sb.append("Running Total: " + this.score + " out of 10\n\n");
		Robot.runningScore = String.valueOf(this.score);
		this.writeToFile(String.valueOf(this.score));
	}

	private void updateScoreForTest(final StringBuilder sb, final String part, final double testPassed,
			final String[] moves, final double robotMoves, final double[] marks) {
		sb.append("*************************************************\n");
		sb.append("PART A - RESULTS\n");
		sb.append("*************************************************\n");
		this.numMoves = Robot.getNumberOfMoves() - 1.0;
		this.score = this.readFromFile("score.txt");
		double scoreBlocksMoved = 0.0;
		switch (Robot.numBlocksMoved) {
		case 1: {
			scoreBlocksMoved = marks[0];
			this.score += scoreBlocksMoved;
		}
		case 2: {
			scoreBlocksMoved = marks[1];
			this.score += scoreBlocksMoved;
		}
		case 3: {
			scoreBlocksMoved = marks[2];
			this.score += scoreBlocksMoved;
		}
		case 4: {
			scoreBlocksMoved = marks[3];
			this.score += scoreBlocksMoved;
			break;
		}
		}
		sb.append(String.valueOf(scoreBlocksMoved) + " marks - You successfully moved: " + Robot.numBlocksMoved
				+ " blocks.\n");
		double minimumMoves = 0.0;
		final int numberOfMoves = (int) Robot.getNumberOfMoves();
		final int targetMoves = Integer.parseInt(moves[0]);
		if (numberOfMoves <= Integer.parseInt(moves[0])) {
			minimumMoves = 1.0;
			this.score += minimumMoves;
			sb.append("1.0 marks - You completed the task in " + (int) this.numMoves
					+ ".  \nYou achieved the minimum moves of " + moves[0] + " for part " + part + "\n");
		} else if (numberOfMoves <= Integer.parseInt(moves[1])) {
			minimumMoves = 0.75;
			this.score += minimumMoves;
			sb.append("0.75 marks - You completed the task in " + (int) this.numMoves
					+ ".  \nYou achieved the minimum moves of " + moves[1] + " for part " + part + "\n");
		} else if (numberOfMoves <= Integer.parseInt(moves[2])) {
			minimumMoves = 0.5;
			this.score += minimumMoves;
			sb.append("0.5 marks - You completed the task in " + (int) this.numMoves
					+ ".  \nYou achieved the minimum moves of " + moves[2] + " for part " + part + "\n");
		} else if (numberOfMoves <= Integer.parseInt(moves[3])) {
			minimumMoves = 0.5;
			this.score += minimumMoves;
			sb.append("0.5 mark - You completed the task in " + (int) this.numMoves
					+ ".  \nYou achieved the minimum moves of " + moves[3] + " for part " + part + "\n");
		}
		sb.append("Running Total: " + this.score + " out of 10\n\n");
		Robot.runningScore = String.valueOf(this.score);
		this.writeToFile(String.valueOf(this.score));
	}
}
