package problem1_static;

public class pc_static {
	private static final int NUM_END = 200000;
	private static final int NUM_THREAD = 32;

	public static void main(String[] args) {
		int counter = 0;
		int i;

		IntThread[] ts = new IntThread[NUM_THREAD];

		long startTime = System.currentTimeMillis();
		for (i = 0; i < NUM_THREAD; i++) {
			ts[i] = new IntThread(i, NUM_END, NUM_THREAD);
			ts[i].start();
		}
		try {
			for (i = 0; i < NUM_THREAD; i++) {
				ts[i].join();
				counter += ts[i].getSum();
			}
		} catch (InterruptedException e) {
		}

		long endTime = System.currentTimeMillis();
		long timeDiff = endTime - startTime;
		System.out.println("Execution Time : " + timeDiff + "ms");
		System.out.println("1..." + (NUM_END - 1) + " prime# counter=" + counter + "\n");
	}
}

class IntThread extends Thread {
	int my_id; // fields for communicating inputs
	int num_steps;
	int num_threads;
	int counter = 0;

	IntThread(int id, int numSteps, int numThreads) {
		my_id = id;
		num_steps = numSteps;
		num_threads = numThreads;
	}

	public void run() {
		long startTime = System.currentTimeMillis();
		int i;
		int i_start = my_id * (int) (num_steps / num_threads);
		int i_end = i_start + (int) (num_steps / num_threads);
		if (my_id != num_threads - 1) {
			for (i = i_start; i < i_end; i++) {
				if (isPrime(i))
					counter++;
			}
		} else {
			for (i = i_start; i < num_steps; i++) {
				if (isPrime(i))
					counter++;
			}
		}
		long endTime = System.currentTimeMillis();
		long timeDiff = endTime - startTime;
		System.out.println("Thread" + my_id + "'s Execution Time : " + timeDiff + "ms");
	}

	private static boolean isPrime(int x) {
		int i;
		if (x <= 1)
			return false;
		for (i = 2; i < x; i++) {
			if ((x % i == 0) && (i != x))
				return false;
		}
		return true;
	}

	public int getSum() {
		return counter;
	}
}