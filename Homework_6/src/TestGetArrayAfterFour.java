import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestGetArrayAfterFour {
    private final ArrayChecker arrayChecker = new ArrayChecker();

    @ParameterizedTest
    @MethodSource("someArraysForLastFour")
    public void getArrayAfterFour(int[] array, int[] expected){
        int[] arr = arrayChecker.getArrayAfterFour(array);
        Assertions.assertArrayEquals(expected, arr);
    }

    public static Stream<Arguments> someArraysForLastFour(){
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[]{1, 2, 4}, new int[]{}));
        out.add(Arguments.arguments(new int[]{1, 4, 4, 3, 5}, new int[]{3, 5}));
        out.add(Arguments.arguments(new int[]{1, 2, 4, 1, 6, 4, 1}, new int[]{1}));
        return out.stream();
    }

    @Test
    public void getArrayAfterFourException(){
        Assertions.assertThrows(RuntimeException.class,
                () -> arrayChecker.getArrayAfterFour(new int[] {1, 3, 5}));
    }

    @ParameterizedTest
    @MethodSource("someArraysForOnesAndFours")
    void checkOnesAndFours(int[] array, boolean expected){
        boolean result = arrayChecker.checkArrayForOnesAndFours(array);
        Assertions.assertEquals(expected, result);
    }

    public static Stream<Arguments> someArraysForOnesAndFours(){
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[]{1, 1, 1, 4, 4, 1, 4, 4}, true));
        out.add(Arguments.arguments(new int[]{1, 1, 1, 1, 1, 1}, false));
        out.add(Arguments.arguments(new int[]{4, 4, 4, 4}, false));
        out.add(Arguments.arguments(new int[]{1, 4, 4, 1, 1, 4, 3}, false));
        return out.stream();
    }
}
