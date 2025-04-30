import org.example.CircleIntersection;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class CircleIntersectionTest {

    int MAX = 10000;
    int HALF = 5000;
    int MIN = 1;

    @DataProvider(name = "intersectionCases")
    public Object[][] intersectionCases() {


        return new Object[][] {
                {0, 5, 0, 5, "The circles coincide; there are infinitely many points of intersection"},
                {0, HALF, 0, HALF, "The circles coincide; there are infinitely many points of intersection"},
                {0, MAX, 0, MAX, "The circles coincide; there are infinitely many points of intersection"},

                {0, 5, 8, 5, "2 points"},
                {0, HALF, 1, HALF, "2 points"},
                {0, HALF, MAX - 2, HALF, "2 points"},

                {0, 5, 10, 5, "1 point"},
                {0, HALF, MAX, HALF, "1 point"},
                {MAX - 10, 10, MAX, 0, "1 point"},

                {0, 5, 20, 5, "0 points"},
                {0, HALF-1, MAX, HALF - 1, "0 points"},
                {MIN, 1, MAX, 1, "0 points"},

                {0, 5, 0, 2, "0 points"},
                {1000, HALF, 1000, 100, "0 points"},
                {MAX, MAX, MAX, 1, "0 points"},

                {0, 1_073_741_823, 2_147_483_647, 1_073_741_822, "0 points"},
                {Integer.MIN_VALUE, 1, Integer.MIN_VALUE + 2, 1, "1 point"},
                {Integer.MAX_VALUE - 2, 1, Integer.MAX_VALUE, 1, "1 point"},
        };
    }

    @Test(dataProvider = "intersectionCases")
    public void testIntersection(int x1, int r1, int x2, int r2, String expected) {
        String actual = CircleIntersection.intersectionDescription(x1, r1, x2, r2);
        assertEquals(actual, expected);
    }

    @DataProvider(name = "invalidInputs")
    public Object[][] invalidInputs() {
        return new Object[][] {
                {0, 0, 1, 1},     // r1 = 0
                {0, -1, 1, 1},    // r1 < 0
                {1, 1, 2, 0},     // r2 = 0
                {1, 1, 2, -5},    // r2 < 0
                {MAX + 1, 0, 1, 1},
                {0, MAX + 1, 1, 1},
                {1, 1, MAX + 1, 0}, 
                {1, 1, 2,MAX + 1},
        };
    }

    @Test(dataProvider = "invalidInputs", expectedExceptions = IllegalArgumentException.class)
    public void testInvalidInput(int x1, int r1, int x2, int r2) {
        CircleIntersection.validateInput(x1, r1, x2, r2);
    }
}
