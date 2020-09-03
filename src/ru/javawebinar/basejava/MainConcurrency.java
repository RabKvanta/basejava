package ru.javawebinar.basejava;


public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private int counter;
    private static final Object LOCK = new Object();
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {

        deadLock();

/*
        System.out.println(Thread.currentThread().getName());


        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }

            private void inc() {
                synchronized (this) {
//                    counter++;
                }
            }

        }).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(mainConcurrency.counter);
*/
    }

    private synchronized void inc() {
//        synchronized (this) {
//        synchronized (MainConcurrency.class) {
        counter++;
//                wait();
//                readFile
//                ...
//        }
    }


    private static void deadLock() {
        System.out.println("Example DeadLock:");

        new Thread(() -> {
            String name = Thread.currentThread().getName();
            //  System.out.println(name + " LOCK1 WAIT ...");
            synchronized (LOCK1) {
                System.out.println(name + " LOCK1 BUSY ");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(name + " LOCK2 WAIT... ");
                synchronized (LOCK2) {
                    System.out.println(name + " LOCK2 BUSY");
                }
            }
        }).start();

        new Thread(() -> {
            String name = Thread.currentThread().getName();
            //    System.out.println(name + " LOCK2 WAIT");
            synchronized (LOCK2) {
                System.out.println(name + " LOCK2 BUSY");
                try {
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(name + " LOCK1 WAIT...");
                synchronized (LOCK1) {
                    System.out.println(name + " LOCK1 BUSY");
                }
            }
        }).start();

    }
}