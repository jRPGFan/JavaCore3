import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static Random random = new Random();

    public static void main(String[] args) {
        ArrayChecker arrayChecker = new ArrayChecker();
        System.out.println("Returned array: "
                + Arrays.toString(arrayChecker.getArrayAfterFour(generateArray(random.nextInt(10)))));
    }

    public static int[] generateArray(int length){
        ArrayList<Integer> list = new ArrayList<Integer>(length);

        for (int i = 0; i < length; i++)
        {
            list.add(random.nextInt(10));
        }
        System.out.println("Generated array: " + list.toString());
        return list.stream().mapToInt(i -> i).toArray();
    }
}
