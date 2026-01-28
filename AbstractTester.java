public abstract class AbstractTester {
    // Console Colors
    protected final String WARNPRINT;
    protected final String ERRORPRINT;
    protected final String BLUECYAN;
    protected final String PURPLE;
    protected final String GREEN;
    protected final String LIME;
    protected final String MAGENTA;
    protected final String LIGHTBLUE;
    protected final String RESET;

    // Options
    private boolean showConsoleColors;
    private boolean printFailuresOnly;

    // Test Tracking
    protected int passes;
    protected int fails;
    protected int totalRun;
    protected int secPasses;
    protected int secFails;
    protected int secTotal;
    protected long startTime;
    protected long endTime;
    protected final int EXPECTED_TOTAL_TESTS;

    public AbstractTester() {
        this(false, false, 0);
    }

    public AbstractTester(boolean showConsoleColors, boolean printFailuresOnly, int expectedTotalTests) {
        this.showConsoleColors = showConsoleColors;
        this.printFailuresOnly = printFailuresOnly;
        this.EXPECTED_TOTAL_TESTS = expectedTotalTests;

        if (showConsoleColors) {
            WARNPRINT  = "\u001B[38;2;255;201;0m";   // #FFC900
            ERRORPRINT = "\u001B[38;2;255;0;0m";     // #FF0000
            BLUECYAN   = "\u001B[38;2;5;219;252m";   // #05DBFC
            PURPLE     = "\u001B[38;2;128;0;128m";   // #800080
            GREEN      = "\u001B[38;2;0;255;0m";     // #00FF00
            LIME 	   = "\u001B[38;2;124;187;0m";   // #7CBB00
            MAGENTA    = "\u001B[38;2;255;0;255m";   // #FF00FF
            LIGHTBLUE  = "\u001B[38;2;5;185;250m";   // #05B9FA
            RESET      = "\u001B[0m";                // Reset to default
        } else {
            WARNPRINT = "";
            ERRORPRINT = "";
            BLUECYAN = "";
            PURPLE = "";
            GREEN = "";
            LIME = "";
            MAGENTA = "";
            LIGHTBLUE = "";
            RESET = "";
        }
        this.passes = 0;
        this.fails = 0;
        this.totalRun = 0;
        this.secPasses = 0;
        this.secFails = 0;
        this.secTotal = 0;
        this.startTime = System.nanoTime();
        this.endTime = 0;
    }

    protected void stopTimer() {
        this.endTime = System.nanoTime();
    }

    /** Print test results in a consistent format
	 * @param testDesc description of the test
	 * @param result indicates if the test passed or failed
	 */
	protected void printTest(String testDesc, boolean result) {
		totalRun++;
		if (result) { 
			passes++; 
		} else { 
			fails++; 
		}
		if (!result || !printFailuresOnly) {
			String prefix = "Test " + totalRun + ": ";
			String status = result ? "   PASS" : "***FAIL***";
			// Calculate exact spacing needed to align status at column 80
			int targetColumn = 80;
			int currentLength = prefix.length() + testDesc.length();
			int spacesNeeded = Math.max(1, targetColumn - currentLength);
			String spaces = " ".repeat(spacesNeeded);
			String line = prefix + testDesc + spaces + status;
			if (result) {
				System.out.println(GREEN + line + RESET);
			} else {
				System.out.println(WARNPRINT + line + RESET);
			}
		}
	}

	/** Print a final summary */
	protected void printFinalSummary() {
		endTime = System.nanoTime();
		long elapsedTime = endTime - startTime;
		String line = "";
		// compute percentages safely (avoid divide-by-zero)
		double percentRun = EXPECTED_TOTAL_TESTS == 0 ? 0.0 : ((double) totalRun) * 100.0 / ((double) EXPECTED_TOTAL_TESTS);
		double percentPassed = totalRun == 0 ? 0.0 : ((double) passes) * 100.0 / ((double) totalRun);
		double percentFailed = totalRun == 0 ? 0.0 : ((double) fails) * 100.0 / ((double) totalRun);
		String verdict = String.format("\n%sTotal Tests Run: %d out of %d (%.3f%%)%s,  %sPassed: %d (%.3f%%)%s,  %sFailed: %d (%.3f%%)%s\n",
			LIGHTBLUE, totalRun, EXPECTED_TOTAL_TESTS, percentRun, RESET, GREEN, passes, percentPassed, RESET, WARNPRINT, fails, percentFailed, RESET);
		for (var i = 0; i < verdict.length(); i++) {
			line += "-";
		}
		System.out.println(line);
		System.out.println(verdict);
		System.out.println();
		System.out.println();
		System.out.println(LIGHTBLUE + "Settings Selected:");
		System.out.println();
		System.out.println(LIME + "Total Computational Time: " + elapsedTime + " ns " + "(" + ((double)(elapsedTime) / 1000000000.0) + " s)" + RESET);
		System.out.println();
	}

	/** Print a section summary */
	protected void printSectionSummary() {
		secTotal = totalRun - secTotal;
		secPasses = passes - secPasses;
		secFails = fails - secFails;
		System.out.printf(BLUECYAN + "\nSection Tests: %d,  Passed: %d,  Failed: %d" + RESET + "\n", secTotal, secPasses, secFails);
		secTotal = totalRun; //reset for next section
		secPasses = passes;
		secFails = fails;		
		System.out.printf(BLUECYAN + "Tests Run So Far: %d,  Passed: %d (%.1f%%),  Failed: %d (%.1f%%)" + RESET + "\n",
				totalRun, passes, passes*100.0/totalRun, fails, fails*100.0/totalRun);
	}

    /**
     * Asserts that two objects are equal. If they are not, prints a failure message.
     * @param desc - Description of the test
     * @param expected - The expected value
     * @param actual - The actual value
     */
    protected void assertEquals(String desc, Object expected, Object actual) {
        boolean pass = (expected == null && actual == null) || (expected != null && expected.equals(actual));
        if (!pass) {
            printTest(desc, false);
            System.out.println(ERRORPRINT + "Expected: " + expected + ", but got: " + actual + RESET);
        } else {
            printTest(desc, true);
        }
    }
}