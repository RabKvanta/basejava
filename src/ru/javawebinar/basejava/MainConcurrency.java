package ru.javawebinar.basejava;


public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private int counter;
    private static final Object LOCK = new Object();
    private static final String LOCK1 = "LOCK1";
    private static final String LOCK2 = "LOCK2";

    public static void main(String[] args) {

        deadLock(LOCK1, LOCK2);
        deadLock(LOCK2, LOCK1);

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


    private static void deadLock(String lock1, String lock2) {

        new Thread(() -> {
            String name = Thread.currentThread().getName();

            synchronized (lock1) {
                System.out.println(name + " " + lock1 + " BUSY ");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(name + " " + lock2 + " WAIT... ");
                synchronized (lock2) {
                    System.out.println(name + " " + lock2 + " BUSY");
                }
            }
        }).start();
    }
}