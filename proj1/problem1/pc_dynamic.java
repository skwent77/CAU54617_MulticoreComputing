package problem1_dynamic;


public class pc_dynamic{
		private static final int NUM_END = 200000;
		private static final int NUM_THREAD =32;
		  
		public static void main(String[] args) {
		    int counter=0;
		    int i=0;
		    int cnt = 0;
		    Num n = new Num();
		    
		    IntThread[] ts = new IntThread[NUM_THREAD];
		    
		    long startTime = System.currentTimeMillis();
		    for (i=0;i<NUM_THREAD;i++) {
		    	ts[i] = new IntThread(i, cnt, n);
			    ts[i].start();
		    }
		    try {
		    	for(i=0;i<NUM_THREAD;i++) {
		    		ts[i].join();
		    		counter += ts[i].getSum();
		    	}
		    }catch(InterruptedException e) {}
		    
		    long endTime = System.currentTimeMillis();
		    long timeDiff = endTime - startTime;
		    System.out.println("\nTotal Execution Time : "+timeDiff+"ms");
		    System.out.println("1..."+(NUM_END-1)+" prime# counter=" + counter +"\n");
		}
}

	// shared Num class
class Num{
		private static final int SEQUENTIAL_CUTOFF = 5000;
		int i = -1;
		synchronized int ranges() {
			i++;
			return i*SEQUENTIAL_CUTOFF;
		}
		public int getCutoff() { return SEQUENTIAL_CUTOFF;}
}

class IntThread extends Thread {
		int my_id;
		int num_start;
		int num_end;
		int counter=0;
		int c_thread;
		
		Num n = new Num();
		
		IntThread(int id, int cnt, Num n) {
			my_id = id; c_thread = cnt;
			this.n =n;
		}
		
		public void run() { 
			long startTime = System.currentTimeMillis();
			int i;
			while((num_start = n.ranges()) < 200000 ) {
				num_end = num_start + n.getCutoff();
				
				for (i=num_start;i<num_end;i++) {
					if (isPrime(i)) counter++;
				}
			}
			long endTime = System.currentTimeMillis();
			long timeDiff = endTime - startTime;
			System.out.println("Thread"+my_id+"'s Execution Time : "+timeDiff+"ms");
		}

		private static boolean isPrime(int x) {
			int i;
		    if (x<=1) return false;
		    for (i=2;i<x;i++) {
		      if ((x%i == 0) && (i!=x)) return false;
		    }
		    return true;			
		}
		public int getSum() {return counter;}
	}

