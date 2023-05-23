package michelon;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class StreetArtist implements Runnable {
    private final Semaphore semaphore, customerSemaphore;

    public StreetArtist(Semaphore semaphore, Semaphore customerSemaphore) {
        this.semaphore = semaphore;
        this.customerSemaphore = customerSemaphore;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);

                semaphore.acquire();

                if (customerSemaphore.availablePermits() == Main.NUM_CHAIRS)
                    System.out.println("The artist is resting");
                else {
                    System.out.println("Artist is ready to paint again");
                    semaphore.acquire();

                    if (customerSemaphore.availablePermits() == Main.NUM_CHAIRS)
                        System.out.println("The artist is resting");
                    else {
                        System.out.println("L'artista è pronto per fare un nuovo ritratto.");
                        Thread.sleep(new Random().nextInt(Main.MAX_DRAWING_TIME));
                        System.out.println("L'artista ha terminato un ritratto.");
                        customerSemaphore.release();
                    }    
                }
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
