
public class Multithreading {

    // Multithreading mt = new Multithreading();

    public static Thread createThread2() {
        T2 thread2 = new T2();
        Thread t2;
        return t2 = new Thread(thread2);
    }

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

        public T1() {
            start();
        }

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

                    System.out.println("T1  iteration " + i);
                    for (int j = 0; j <= 4; j++) {

                        System.out.println("Tråd 1: " + (j + 1));

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

    public static class T2 implements Runnable {

        public T2(){
            Thread t2 = new Thread(this);
            t2.start();
        }

        public void run() {
            try {
                System.out.println("t2 skapad");

                for (int i = 0; i <= 1; i++) {
                    // if(i == 1){

                    // }
                    System.out.println("T2  iteration" + i);
                    for (int j = 0; j <= 4; j++) {

                        System.out.println("    Tråd 2: " + (j +1));

                        Thread.sleep(1000);
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

    }

}
