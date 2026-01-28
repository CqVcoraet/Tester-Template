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
        printTest("First Test", true);
        for (int i = 2; i <= EXPECTED_TOTAL_TESTS - 1; i++) {
            if (Math.random() > 0.5) {
                printTest("Test", true);
            } else {
                printTest("Test", false);
            }
        }
        assertEquals("Final Test", 43, 40 + 3);
        printFinalSummary();
    }

    public String mathTest1() {
        int val1 = (int)(Math.pow((1 + 1), 2));
        int val2 = (int)(Math.pow(3, 1));
        String result1 = String.valueOf(val1 >= val2);

        val1 = (int)(Math.pow((2 + 1), 2));
        val2 = (int)(Math.pow(3, 2));
        String result2 = String.valueOf(val1 >= val2);

        return result1 + ", " + result2;
    }

    public static void main(String[] args) {
        Tester tester = new Tester(true, false, 120);
        System.out.println("Math Test 1 Results: " + tester.mathTest1());
        tester.runTests();
    }
    
}
