/**
 * <h1>Multithreading</h1> Multithreading creates two threads that count to 5.
 * <br>
 * The first thread creates the second thread after it has counted to 5. Then it
 * counts to five again and dies. the second thread counts to 5 two times.
 * <br>
 * @author Sebastian Åkerlund
 */
public class Multithreading {

    /**
     * createThread2 Creates and return a Thread of class T2
     * 
     * @return Thread: T2
     * 
     */
    public static Thread createThread2() {
        T2 thread2 = new T2();
        Thread t2;
        return t2 = new Thread(thread2);
    }

    /**
     * Main method Creates the first thread form class T1 main thread join T1
     * thread.
     */
    public static void main(String[] args) {

        Thread t1 = new T1();

        try {
            t1.join();

        } catch (InterruptedException e) {
            System.out.println(e);
        }


    }

    /**
     * Class T1
     * <br> 
     * extends Thread
     * @see Thread
     */
    public static class T1 extends Thread {
        /**
         * Class constructor constructor starts the thread
         */
        public T1() {
            start();
        }

        /**
         * 
         * The run Method for T1 class thread loops through a nested for loop that
         * counts to five, two times. It creates a second thread T2 after the first time
         * it has counted to 5. then it counts to 5 again and dies.
         * <br> the thread sleeps for 1000 milli seconds between each count
         */
        @Override
        public void run() {
            try {
                System.out.println("t1 skapad");

                for (int i = 0; i <= 1; i++) {
                    if (i == 1) {

                        new T2();

                    } else if (i == 2) {
                        return;
                    }

                    for (int j = 0; j <= 4; j++) {

                        System.out.println("Thread 1: " + (j + 1));

                        Thread.sleep(1000);
                    }
                }
       
                
            } catch (InterruptedException e) {
                System.out.println(e);
            }

        }

    }

    /**
     * Class T2 <br>
     * implements Runnable
     * @see Runnable
     */
    public static class T2 implements Runnable {
        /**
         * Constructs a thread and start it
         * 
         */
        public T2() {
            Thread t2 = new Thread(this);
            t2.start();
        }

        /**
         * Nested for loop that counts to 5 two times then dies.
         * <br> the thread sleeps for 1000 milli seconds between each count
         */
        public void run() {
            try {
                System.out.println("t2 skapad");

                for (int i = 0; i <= 1; i++) {

                    for (int j = 0; j <= 4; j++) {

                        System.out.println("    Thread 2: " + (j + 1));

                        Thread.sleep(1000);
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

    }

}
