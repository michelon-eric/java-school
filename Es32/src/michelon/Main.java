package michelon;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
	private static final int NUM_CHAIRS = 4;
    private static final int MAX_WAIT_TIME = 5000;
    private static final int MAX_DRAWING_TIME = 3000;

    private static Semaphore chairsSemaphore;
    private static Semaphore artistSemaphore;

	public static void main(String[] args) {
		chairsSemaphore = new Semaphore(NUM_CHAIRS);
        artistSemaphore = new Semaphore(1);

		Thread artistThread = new Thread(new StreetArtist());
		artistThread.start();

		Random random = new Random();
        int customerCount = 0;

		while (true) {
            int waitTime = random.nextInt(MAX_WAIT_TIME);

            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Thread customerThread = new Thread(new Customer(customerCount));
            customerThread.start();

            customerCount++;
        }
	}
}
