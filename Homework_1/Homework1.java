package Homework_1;

import java.util.ArrayList;
import java.util.Arrays;

public class Homework1 {
    public static void main (String[] args)
    {
        NumberArray<Integer> iNumArr = new NumberArray<>(1, 2, 3, 4, 5);
        iNumArr.swapValues(0, 3);
        System.out.println(Arrays.toString(iNumArr.numbers));

        NumberArray<Float> fNumArr = new NumberArray<>(1f, 2f, 3f, 4f, 5f);
        fNumArr.swapValues(1, 2);
        System.out.println(Arrays.toString(fNumArr.numbers));

        NumberArray<Double> dNumArr = new NumberArray<>(1.0, 2.0, 3.0, 4.0, 5.0);
        dNumArr.swapValues(5, 1);
        dNumArr.swapValues(3, 2);
        System.out.println(Arrays.toString(dNumArr.numbers));

        ArrayList<Double> dList = convertToList(dNumArr.numbers);
        System.out.println(dList.toString());

        Box<Apple> appleBox = new Box<>();
        Box<Orange> orangeBox = new Box<>();

        appleBox.addFruit(new Apple());
        appleBox.addFruit(new Apple());
        appleBox.addFruit(new Apple());
        System.out.println(appleBox.getWeight());

        orangeBox.addFruit(new Orange());
        orangeBox.addFruit(new Orange());
        System.out.println(orangeBox.getWeight());

        System.out.println(appleBox.compare(orangeBox));

        orangeBox.addFruit(new Orange());
        System.out.println(appleBox.compare(orangeBox));

        Box<Orange> orangeBox2 = new Box<>();
        orangeBox2.addFruit(new Orange());
        orangeBox.pourFruitsInto(orangeBox2);

        System.out.println(orangeBox.getWeight());
        System.out.println(orangeBox2.getWeight());

        //hmm
        Box<Fruit> fruitBox = new Box<>();
        fruitBox.addFruit(new Apple());
        fruitBox.addFruit(new Orange());
    }

    //Написать метод, который преобразует массив в ArrayList;
    public static <T> ArrayList<T> convertToList(T... arrayItems){
        ArrayList<T> outList = new ArrayList<T>(Arrays.asList(arrayItems));
        return outList;
    }
}
