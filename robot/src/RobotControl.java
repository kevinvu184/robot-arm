
/** @author Khoa Vu Duy Anh s3678490
 *  @version final
 *  @since 19-08-2018	
 */

class RobotControl {
	private Robot r;
	public static StringBuilder sb;

	private final int SOURCE_LOCATION = 10;
	private final int FIRST_BAR_POSITION = 3;
	private final int HEIGHT_OF_SECOND_ARM = 1;

	public RobotControl(Robot r) {
		this.r = r;
	}

	public void control(int barHeights[], int blockHeights[]) {
		run(barHeights, blockHeights);
	}

	public void run(int barHeights[], int blockHeights[]) {
		int h = 2; // Initial height of arm 1
		int w = 1; // Initial width of arm 2
		int d = 0; // Initial depth of arm 3

		int contractAmt = FIRST_BAR_POSITION;

		// Used to hold the value of block on column
		int currentColumn = 0;
		int[] targetColSize = { 0, 0 };

		// Find highest bar, highest obstacle
		int currentBar = 0;
		int highestObstacle = 0; // Highest Bar without block
		int highestBar = 0; // Highest Bar with block
		for (int a = 0; a < barHeights.length; a++) {
			if (highestObstacle < barHeights[currentBar]) {
				highestObstacle = barHeights[currentBar];
			}
			currentBar++;
		}
		highestBar = highestObstacle;
		currentBar = 0;

		// Assign the value that the current block hold
		int currentBlock = 0;
		int sourceHt = 0;
		for (int i = 0; i < blockHeights.length; i++) {
			currentBlock = i;
			sourceHt += blockHeights[currentBlock];
		}

		// Check the sourceHt to start picking the block
		while (sourceHt > 0) {
			// H Up mechanism
			while (h < highestBar + HEIGHT_OF_SECOND_ARM || h < sourceHt + d + HEIGHT_OF_SECOND_ARM) {
				r.up();
				h++;
			}

			// W Extend mechanism
			while (w < SOURCE_LOCATION) {
				r.extend();
				w++;
			}

			// D Lower mechanism
			while (d < h - sourceHt - HEIGHT_OF_SECOND_ARM) {
				r.lower();
				d++;
			}

			r.pick();

			// Decide block
			switch (blockHeights[currentBlock]) {
			case 3:
				// H Up mechanism
				while (h < highestObstacle + blockHeights[currentBlock] + HEIGHT_OF_SECOND_ARM) {
					r.up();
					h++;
				}

				// D Raise mechanism
				while (d > h - highestObstacle - blockHeights[currentBlock] - HEIGHT_OF_SECOND_ARM && d > 0) {
					r.raise();
					d--;
				}

				// W Contract mechanism
				while (w > contractAmt) {
					r.contract();
					w--;
				}

				// H Down mechanism
				while (h > d + barHeights[currentBar] + blockHeights[currentBlock] + HEIGHT_OF_SECOND_ARM
						&& h > d + highestBar + HEIGHT_OF_SECOND_ARM) {
					r.down();
					h--;
				}

				// D Lower mechanism
				while (d < h - barHeights[currentBar] - blockHeights[currentBlock] - HEIGHT_OF_SECOND_ARM) {
					r.lower();
					d++;
				}

				// Highest bar Update
				if (highestBar < barHeights[currentBar] + blockHeights[currentBlock]) // compare current highest bar
																						// with the new bar and block
				{
					highestBar = barHeights[currentBar] + blockHeights[currentBlock];
				}

				// Highest obstacle Update
				if (barHeights[currentBar] == highestObstacle) {
					int highestUpdatedObstacle = 0;
					int numberOftime = 0;
					while (currentBar < barHeights.length - 1) {
						if (highestUpdatedObstacle < barHeights[currentBar + 1]) {
							highestUpdatedObstacle = barHeights[currentBar + 1];
						}
						currentBar++;
						numberOftime++;
					}
					highestObstacle = highestUpdatedObstacle;
					currentBar = barHeights.length - numberOftime - 1;
				}

				r.drop();

				// D Raise mechanism
				while (d > h - highestObstacle - HEIGHT_OF_SECOND_ARM) {
					r.raise();
					d--;
				}

				// Update current bar and contractAmt value
				currentBar++;
				contractAmt++;
				break;

			default:
				// Determine currentColumn for Part C
				if (blockHeights[currentBlock] == 2) {
					currentColumn = 1; // currentColumn 1 will carry block 2
				} else if (blockHeights[currentBlock] == 1) {
					currentColumn = 0; // currentColumn 2 will carry block 1
				}

				// H Up mechanism
				while (h < highestBar + blockHeights[currentBlock] + HEIGHT_OF_SECOND_ARM
						|| h < targetColSize[currentColumn] + blockHeights[currentBlock] + HEIGHT_OF_SECOND_ARM) {
					r.up();
					h++;
				}

				// D Raise mechanism
				while (d > 0) {
					r.raise();
					d--;
				}

				// W Contract mechanism
				while (w != currentColumn + 1) // the real position of column 1,2 = index of the array targetColHt + 1
				{
					r.contract();
					w--;
				}

				// H Down mechanism
				if (currentColumn == 1 && targetColSize[currentColumn - 1] > blockHeights[currentBlock]
						+ targetColSize[currentColumn]) {
					while (h > targetColSize[currentColumn - 1] + HEIGHT_OF_SECOND_ARM) {
						r.down();
						h--;
					}
				} else {
					while (h > d + blockHeights[currentBlock] + targetColSize[currentColumn] + HEIGHT_OF_SECOND_ARM) {
						r.down();
						h--;
					}
				}

				// D Lower mechanism
				while (d < h - blockHeights[currentBlock] - targetColSize[currentColumn] - HEIGHT_OF_SECOND_ARM) {
					r.lower();
					d++;
				}

				r.drop();

				// D Raise mechanism
				while (d > 0) {
					r.raise();
					d--;
				}

				// Update the height of target column
				targetColSize[currentColumn] += blockHeights[currentBlock];
				break;
			}

			// Update source height and current block value
			sourceHt -= blockHeights[currentBlock];
			currentBlock--;
		}
	}
}