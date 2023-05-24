package michelon;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Customer implements Runnable {
    private final int id;
    private final Semaphore chairsSemaphore;
    private final Semaphore artistSemaphore;

    private final Color outputColor;

    public Customer(int id, Semaphore chairsSemaphore, Semaphore artistSemaphore) {
        this.id = id;

        this.chairsSemaphore = chairsSemaphore;
        this.artistSemaphore = artistSemaphore;

        outputColor = Color.random();
    }

    @Override
    public void run() {
        print("Client arrived");
        try {
            if (!chairsSemaphore.tryAcquire(Data.MIN_WAIT_TIME + new Random().nextInt(Data.MAX_WAIT_TIME),
                    TimeUnit.MILLISECONDS)) {
                print("Client is leaving");
                return;
            }

            while (true) {
                if (!artistSemaphore.tryAcquire())
                    continue;

                Thread.sleep(Data.MIN_DRAWING_TIME + new Random().nextInt(Data.MAX_DRAWING_TIME));
                print("Customer got the painting");
                chairsSemaphore.release();
                artistSemaphore.release();
                break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void print(String text) {
        System.out.printf("%s [%d] %s\n", outputColor, id, text);
    }
}
