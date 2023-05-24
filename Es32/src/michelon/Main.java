package michelon;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {

    private final static Semaphore chairsSemaphore = new Semaphore(Data.NUM_CHAIRS);
    private final static Semaphore artistSemaphore = new Semaphore(1);

    public static void main(String[] args) {
        Random rng = new Random();
        int numCustomers = 10 + rng.nextInt(90);

        for (int i = 0; i < numCustomers; i++) {
            new Thread(new Customer(i, chairsSemaphore, artistSemaphore)).start();
            try {
                Thread.sleep(rng.nextInt(1000));
            } catch (InterruptedException e) {
            }
        }
    }
}
