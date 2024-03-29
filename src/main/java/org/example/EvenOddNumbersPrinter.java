package org.example;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Tag(name = "EvenOddNumbersPrinter", description = "Выводит в консоль четные и нечетные числа двумя разными потоками последовательно")
public class EvenOddNumbersPrinter {

    @Schema(description = "Количество выводимых чисел", example = "100")
    private final int count;

    private final Thread t1;
    private final Thread t2;

    public EvenOddNumbersPrinter(int count) {
        this.count = count;
        AtomicInteger num = new AtomicInteger(0);
        Semaphore sem1 = new Semaphore(0);
        Semaphore sem2 = new Semaphore(1);
        t1 = new Thread(task(num, sem2, sem1), "Even:");
        t2 = new Thread(task(num, sem1, sem2), "Odd :");
    }

    @Operation(summary = "Запускает потоки для вывода чисел")
    public void print(){
        t1.start();
        t2.start();

        try{
            t1.join();
            t2.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Runnable task(AtomicInteger num, Semaphore sem1, Semaphore sem2) {
        return () -> {
            boolean running = true;
            while (running) {
                try {
                    sem1.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (num.get() < count) System.out.println(Thread.currentThread().getName() + " " + num.getAndIncrement());
                else running = false;
                sem2.release();
            }
        };
    }
}
