package Homework_1;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    private ArrayList<T> fruits;

    public Box(){
        fruits = new ArrayList<>();
    }

    public void addFruit(T fruit){
        fruits.add(fruit);
    }

    public float getWeight(){
        if(fruits.isEmpty()) return 0;
        return fruits.size() * fruits.get(0).weight;
    }

    public boolean compare(Box<?> anotherBox){
        return Math.abs(this.getWeight() - anotherBox.getWeight()) < 0.0001;
    }

    public void pourFruitsInto(Box<T> anotherBox){
        anotherBox.fruits.addAll(this.fruits);
        this.fruits.clear();
    }
}
