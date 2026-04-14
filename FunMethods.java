public class FunMethods extends AbstractTester {
    // Options
    protected boolean showConsoleColors;
    protected boolean printFailuresOnly;

    public FunMethods() {
        this(true, false, 100);
    }

    public FunMethods(boolean showConsoleColors, boolean printFailuresOnly, int expectedTotalTests) {
        super(showConsoleColors, printFailuresOnly, expectedTotalTests);
    }

    private void runTests() {
        printTest("test10toBase2", test10toBase2());
        printFinalSummary();
    }

    public static void main(String[] args) {
        FunMethods tester = new FunMethods(true, false, 50);
        tester.runTests();
    }

    private boolean test10toBase2() {
        String expectedValue = "1010";
        return numberToBase(10, 2).equals(expectedValue);
    }

    public static String numberToBase(int base10Num, int base) {
        if (base < 1 || base > 62) {
            throw new IllegalArgumentException("Base must be between 1 and 62 (inclusive).");
        }

        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                         'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                         'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                         'U', 'V', 'W', 'X', 'Y', 'Z',
                         'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                         'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                         'u', 'v', 'w', 'x', 'y', 'z'};

        char[] allowedDigits = new char[base];
        
        for (int i = 0; i < base; i++) {
            allowedDigits[i] = digits[i];
        }

        StringBuilder result = new StringBuilder();
        int num = base10Num;

        while (num > 0) {
            int remainder = num % base;
            result.insert(0, allowedDigits[remainder]);
            num /= base;
        }

        return result.toString();

    }

}
