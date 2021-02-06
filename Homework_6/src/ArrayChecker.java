import java.util.Arrays;

public class ArrayChecker {
    public int[] getArrayAfterFour(int[] array){
        if(array.length == 0) return array;
        int[] result;
        int indexOfFour = 0;

        for (int i = array.length - 1; i >= 0; i--){
            if (array[i] == 4) {
                indexOfFour = i + 1;
                break;
            }
        }

        if (indexOfFour != 0){
            if (indexOfFour != array.length)
                result = Arrays.copyOfRange(array, indexOfFour, array.length);

            else result = new int[0];
        } else {
            throw new RuntimeException();
        }

        return result;
    }

    public boolean checkArrayForOnesAndFours(int[] array){
        boolean containsOne = false;
        boolean containsFour = false;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) containsOne = true;
            else if (array[i] == 4) containsFour = true;
            else return false;
        }

        return containsOne && containsFour;
    }
}
