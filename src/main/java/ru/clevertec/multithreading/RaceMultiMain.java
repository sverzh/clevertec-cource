package ru.clevertec.multithreading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

public class RaceMultiMain {
    private static long distance;
    private static int carsAmount;
    private static CountDownLatch countDownLatch = new CountDownLatch(carsAmount);

    static {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Cars Amount");
        try {
            carsAmount = Integer.parseInt(reader.readLine());
            System.out.println("Enter Distance (m)");
            distance = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < carsAmount; i++) {
            Car car = new Car(i);
            new Thread(car).start();
            Thread.sleep(10);
        }
    }
    public static class Car implements Runnable {
        private int speed;
        private int carNamber;

        public Car(int carNumber) {
            this.speed = (int) (Math.random() * 50) + 1;
            this.carNamber = carNumber;
        }

        @Override
        public void run() {
            countDownLatch.countDown();
            try {
                countDownLatch.await();
                long carDistance = 0;
                long startTime = System.currentTimeMillis();
                while (carDistance < distance) {
                    Thread.sleep(1000);
                    carDistance = (System.currentTimeMillis() - startTime) / 1000 * this.speed;

                    if (carDistance < distance) {
                        System.out.println("car distance of Car-" + carNamber + " =  " + carDistance);
                    }
                    else break;
                }
                System.out.println("Car-" + carNamber + " Finished!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}



