import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

public class Car implements Runnable {
    private static int carsCounter = 0;
    private Race race;
    private int speed;
    private String name;
    CountDownLatch countDownLatchStart;
    CountDownLatch countDownLatchFinish;
    private Lock lock;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CountDownLatch countDownLatchStart, CountDownLatch countDownLatchFinish,
               Lock lock) {
        this.race = race;
        this.speed = speed;
        carsCounter++;
        this.name = "Участник #" + carsCounter;
        this.countDownLatchStart = countDownLatchStart;
        this.countDownLatchFinish = countDownLatchFinish;
        this.lock = lock;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            countDownLatchStart.countDown();
            countDownLatchStart.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }

        countDownLatchFinish.countDown();
        try {
            if(lock.tryLock()) MainClass.winner = this.name;
            countDownLatchFinish.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}