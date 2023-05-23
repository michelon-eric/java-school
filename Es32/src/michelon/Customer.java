package michelon;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Customer implements Runnable {
    private final int id;
    private final Semaphore semaphore, artistSemaphore;

    public Customer(int id, Semaphore customerSemaphore, Semaphore artistSemaphore) {
        this.id = id;
        this.semaphore = customerSemaphore;
        this.artistSemaphore = artistSemaphore;
    }

    @Override
    public void run() {
        System.out.println("Customer " + id + " arrived");

        if (!semaphore.tryAcquire()) {
            System.out.println("Customer " + id + " failed to get a seat");
            return;
        }

        try {
            artistSemaphore.acquire();
            System.out.println("Customer " + id + " is getting their painting done");

            Thread.sleep(new Random().nextInt(Main.MAX_DRAWING_TIME));

            System.out.println("Customer " + id + " got the painting");

            artistSemaphore.release();
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
