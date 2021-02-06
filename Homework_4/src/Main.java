public class Main {
    private final Object monitor = new Object();
    private volatile char currentLetter = 'A';

    public static void main(String[] args) {
        Main m = new Main();
        Thread threadA = new Thread(() -> {
            m.printA();

        });

        Thread threadB = new Thread(() -> {
            m.printB();
        });

        Thread threadC = new Thread(() -> {
            m.printC();
        });

        threadA.start();
        threadB.start();
        threadC.start();
    }

    public void printA(){
        synchronized (monitor){
            try{
                for (int i = 0; i < 5; i++){
                    while (currentLetter != 'A') monitor.wait();
                    System.out.print('A');
                    currentLetter = 'B';
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB(){
        synchronized (monitor){
            try{
                for (int i = 0; i < 5; i++){
                    while (currentLetter != 'B') monitor.wait();
                    System.out.print('B');
                    currentLetter = 'C';
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printC(){
        synchronized (monitor){
            try{
                for (int i = 0; i < 5; i++){
                    while (currentLetter != 'C') monitor.wait();
                    System.out.print("C ");
                    currentLetter = 'A';
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
