package com.javarush.island.parfenov.gameMechanics;

import com.javarush.island.parfenov.organisms.Organism;
import com.javarush.island.parfenov.statistics.Stat;

import java.util.*;

public class OrganismController implements Runnable {
    String organismName;
    Cell[][] field;
    GameController gameController;
    Queue<Task> tasks = new LinkedList<>();
    private Stat stat;
    public OrganismController(GameController gameController, String organismName, Stat stat) {
        this.gameController = gameController;
        this.organismName = organismName;
        field = gameController.getField();
        this.stat = stat;
    }

    @Override
    public void run() {
//        System.out.println("My thread is " + Thread.currentThread().getName());
        long n = System.currentTimeMillis();
//        int counter = 0;
        for (Cell[] cells : field) {
            for (Cell cell : cells) {
                //Set<Organism> organisms = cell.getResidents().get(organismName);
//                if(organisms.isEmpty()) {
//                    System.out.println(organismName + " is empty!");
//                }

                cell.getVaultLock(organismName).lock();
                try {
                    int amountOfAnimals;
                    Set<Organism> organisms = cell.getCertainResidents(organismName);
                    int oldAmountOfAnimals = organisms.size();
                    organisms.addAll(cell.getCertainMigrants(organismName));
                    cell.getCertainMigrants(organismName).clear();
                    cell.setAmountOfMigrants(organismName, 0);
//                    int amountOfOrganisms = cell.getAmountOfResidents(organismName);
                    Iterator<Organism> iterator = organisms.iterator();
                    while(iterator.hasNext()) {
                        Organism organism = iterator.next();
                        if(!organism.isAlive() || (organism.getNextDestination() != null && !organism.getNextDestination().equals(cell))) {
                            iterator.remove();
                            Organism.amountOfAnimals.decrementAndGet();
                        } else {
                            tasks.add(new Task(organism, cell));
                        }
                    }
//                    amountOfOrganisms.put(organismName, organisms.size());
                    cell.setAmountOfResidents(organismName, organisms.size());
                    amountOfAnimals = organisms.size();
                    int delta = amountOfAnimals - oldAmountOfAnimals;
                    stat.getAmountOfOrganisms().put(organismName, stat.getAmountOfOrganisms().get(organismName) + delta);
//                    stat.getFullSize().addAndGet(delta);
                    stat.setFullSize(stat.getFullSize().get() + delta);
                    Organism.amountOfAnimals.addAndGet(delta);
//                    counter = organisms.size();
//

//                    for (Organism organism : organisms) {
//                        tasks.add(new Task(organism, cell));
//                    }
                } finally {
                    cell.getVaultLock(organismName).unlock();
                }
            }
        }
//        System.out.println(Organism.getAmountOfAnimals().get() + "@@@@@");
        System.out.println("A time of the first part: " + (System.currentTimeMillis() - n));
        n = System.currentTimeMillis();
        tasks.forEach(Task::execute);
        tasks.clear();
        System.out.println("A time of the second part: " + (System.currentTimeMillis() - n));

//        for (Cell[] cells : field) {
//            for (Cell cell : cells) {
//                cell.getLock().lock();
//
//                try {
//                    int amountOfAnimals;
//                    Set<Organism> organisms = cell.getResidents().get(organismName);
//                    int oldAmountOfAnimals = organisms.size();
//                    organisms.addAll(cell.getMigrants().get(organismName));
//                    cell.getMigrants().get(organismName).clear();
//                    cell.getAmountOfMigrants().put(organismName, 0);
//                    Map<String, Integer> amountOfOrganisms = cell.getAmountOfOrganisms();
//                    Iterator<Organism> iterator = organisms.iterator();
//                    while(iterator.hasNext()) {
//                        Organism organism = iterator.next();
//                        if(!organism.isAlive() || (organism.getNextDestination() != null && !organism.getNextDestination().equals(cell))) {
//                            iterator.remove();
//                            Organism.amountOfAnimals.decrementAndGet();
//                        }
//                    }
//                    amountOfOrganisms.put(organismName, organisms.size());
//                    amountOfAnimals = organisms.size();
//                    int delta = amountOfAnimals - oldAmountOfAnimals;
//                    stat.getAmountOfOrganisms().put(organismName, stat.getAmountOfOrganisms().get(organismName) + delta);
//                    stat.getFullSize().addAndGet(delta);
//                } finally {
//                    cell.getLock().unlock();
//                }
//            }
//        }
//        System.out.println("Amount of organisms now: " + Organism.amountOfAnimals.get());
    }
}