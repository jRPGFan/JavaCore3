package Homework_1;

public class NumberArray<T extends Number> {
    protected T[] numbers;

    public NumberArray(T... numbers) {
        this.numbers = numbers;
    }

    //Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа);
    public void swapValues(int positionA, int positionB) {
        if (positionA < 0 || positionA >= numbers.length ||
                positionB < 1 || positionB >= numbers.length){
            System.out.println("Position out of bounds");
            return;
        }

        T tempNumber = numbers[positionA];
        numbers[positionA] = numbers[positionB];
        numbers[positionB] = tempNumber;
    }
}
