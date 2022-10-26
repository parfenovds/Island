package com.javarush.island.parfenov.gameMechanics;

import com.javarush.island.parfenov.statistics.Stat;
import com.javarush.island.parfenov.view.ViewInitializer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PoolController {
    GameController gameController;
    ViewInitializer viewInitializer;

    private Stat stat;
    public PoolController(GameController gameController, ViewInitializer viewInitializer, Stat stat) {
        this.gameController = gameController;
        this.viewInitializer = viewInitializer;
        this.stat = stat;
    }

    public void begin() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);//1 + gameController.getPrototypes().size());
        executorService.execute(viewInitializer);
        List<OrganismController> workers = gameController
                .getPrototypes()
                .keySet()
                .stream()
                .map(o -> new OrganismController(gameController, o, stat))
                .toList();
        executorService.scheduleAtFixedRate(() -> {
//            ExecutorService servicePool = Executors.newFixedThreadPool(16);
            ExecutorService servicePool = Executors.newWorkStealingPool();
//            ExecutorService servicePool = Executors.newCachedThreadPool();
            workers.forEach(servicePool::submit);
            servicePool.shutdown();
            try {
                long m = System.currentTimeMillis();
                if(servicePool.awaitTermination(100, TimeUnit.SECONDS)) {
                    System.out.println("Hello from " + Thread.currentThread().getName() + " with time " + (System.currentTimeMillis() - m));
//                    Cell[][] field = gameController.getField();
//                    for (Cell[] cells : field) {
//                        for (Cell cell : cells) {
//
//                        }
//                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 100, 100, TimeUnit.MILLISECONDS);
    }
}
