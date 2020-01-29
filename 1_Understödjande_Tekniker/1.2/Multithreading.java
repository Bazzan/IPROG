/**
 * <h1>Multithreading</h1> Multithreading creates two threads that count to 5
 * The first thread creates the second thread after it has counted to 5. Then it
 * counts to five again and dies. the second thread counts to 5 two times.
 * @author Sebastian Åkerlund
 */
public class Multithreading {

    /**
     * createThread2 Creates and return a Thread of class T2
     * 
     * @return Thread
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
        // t1.start();

        try {
            t1.join();

        } catch (InterruptedException e) {
            System.out.println(e);
        }
        // for (int i = 0; i < 10; i++) {
        // System.out.println("main");
        // }

    }

    public static class T1 extends Thread {
        /**
         * Class constructor constructor starts the thread
         */
        public T1() {
            start();
        }

        /**
         * Run The run Method for T1 class thread loops through a nested for loop that
         * counts to five, two times. It creates a second thread T2 after the first time
         * it has counted to 5. then it counts to 5 again and dies.
         * 
         */
        @Override
        public void run() {
            try {
                // boolean alive = true;
                // boolean active = true;
                System.out.println("t1 skapad");

                // while (alive) {
                // while (active) {
                for (int i = 0; i <= 1; i++) {
                    if (i == 1) {
                        // Thread t2 = createThread2();
                        // t2.start();
                        new T2();

                    } else if (i == 2) {
                        return;
                    }

                    // System.out.println("T1 iteration " + i);
                    for (int j = 0; j <= 4; j++) {

                        System.out.println("Thread 1: " + (j + 1));

                        Thread.sleep(1000);
                    }
                }
                // }
                // }
            } catch (InterruptedException e) {
                System.out.println(e);
            }

        }

    }

    /**
     * Class T2 implements Runnable
     * 
     */
    public static class T2 implements Runnable {
        /**
         * Constructor T2 constructs a thread and start it
         * 
         */
        public T2() {
            Thread t2 = new Thread(this);
            t2.start();
        }

        /**
         * run nested for loop that counts to 5 two time
         */
        public void run() {
            try {
                System.out.println("t2 skapad");

                for (int i = 0; i <= 1; i++) {
                    // if(i == 1){

                    // }
                    // System.out.println("T2 iteration" + i);
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
