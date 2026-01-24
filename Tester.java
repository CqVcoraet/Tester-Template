public class Tester extends AbstractTester {
    // Options
    protected boolean showConsoleColors;
    protected boolean printFailuresOnly;

    public Tester() {
        super();
    }

    public Tester(boolean showConsoleColors, boolean printFailuresOnly, int expectedTotalTests) {
        super(showConsoleColors, printFailuresOnly, expectedTotalTests);
    }

    public void runTests() {
        for (int i = 1; i <= EXPECTED_TOTAL_TESTS; i++) {
            if (Math.random() > 0.5) {
                printTest("Test", true);
            } else {
                printTest("Test", false);
            }
        }
        printFinalSummary();
    }

    public static void main(String[] args) {
        Tester tester = new Tester(true, false, 120);
        tester.runTests();
    }
    
}
