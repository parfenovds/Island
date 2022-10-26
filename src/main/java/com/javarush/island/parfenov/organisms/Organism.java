package com.javarush.island.parfenov.organisms;

import com.javarush.island.parfenov.gameMechanics.Cell;
import com.javarush.island.parfenov.gameMechanics.LifeStatus;
import com.javarush.island.parfenov.gameMechanics.Phase;
import com.javarush.island.parfenov.gameMechanics.Vault;
import com.javarush.island.parfenov.organisms.animals.Caterpillar;
import com.javarush.island.parfenov.organisms.animals.Mouse;
import com.javarush.island.parfenov.settings.Characteristics;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public abstract class Organism implements Cloneable {
    public static AtomicLong idGenerator = new AtomicLong(0L);
    public static AtomicInteger amountOfAnimals = new AtomicInteger(0);
    private long id;
    private AtomicReference<Phase> phase = new AtomicReference<>(Phase.FREE);
    private AtomicReference<LifeStatus> liveStatus = new AtomicReference<>(LifeStatus.ALIVE);
    private boolean alive = true;
    private Map<String, Integer> chances;
    private Double weight;
    private final Double maxWeight;
    private final Double perc;
    private AtomicLong weight2;
    private int maxWeight2;
    private int perc2;


    private Integer cellLimit;
    private Integer maxCellByMove;
    private Integer movingPoints;
    private final String nameOfOrganism = this.getClass().getSimpleName();
    private final Double feedPortion;
    private Cell nextDestination = null;
    private Integer pointsOfEating;
    private String pathToImg = "/animal_sprites/Default.png";
    private BufferedImage img;
    private final Map<Characteristics, Number> characteristics;

    public Organism(Map<Characteristics, Number> characteristics, Map<String, Integer> chances) {
        this.characteristics = characteristics;
        this.id = idGenerator.incrementAndGet();
        this.weight = (Double) characteristics.get(Characteristics.WEIGHT);
        this.weight2 = new AtomicLong(Math.round((Double) characteristics.get(Characteristics.WEIGHT) * 1000.0));
        perc = weight / 100 * 5;
        maxWeight = weight * 2;
        this.cellLimit = (Integer) characteristics.get(Characteristics.CELL_LIMIT);
        this.maxCellByMove = (Integer) characteristics.get(Characteristics.MAX_CELL_BY_MOVE);
        movingPoints = maxCellByMove;
        this.feedPortion = (Double) characteristics.get(Characteristics.FEED_PORTION);
        this.chances = chances;
        this.pointsOfEating = (Integer) characteristics.get(Characteristics.POINTS_OF_EATING);
    }

    public void multiply(Cell cell) {
        boolean multiplyOrNot = ThreadLocalRandom.current().nextBoolean();
        if (multiplyOrNot &&
            cell.getCertainResidents(nameOfOrganism).size() + cell.getCertainMigrants(nameOfOrganism).size() < cellLimit) {
            cell.setAmountOfMigrants(nameOfOrganism, cell.getAmountOfMigrants(nameOfOrganism) + 1);
            cell.getCertainMigrants(nameOfOrganism).add(this.clone());
            this.weight /= 2;
        }
    }

    public void move(Cell srcCell) {
        boolean moveOrNot = ThreadLocalRandom.current().nextBoolean();
        if (moveOrNot && isAlive() && movingPoints > 0) {
            Cell destCell = findNextCell(srcCell);
            if (destCell != null && destCell != srcCell) {
//                if(this instanceof Mouse) System.out.println("MOUSE!!!");
//                if(this instanceof Mouse) {
//                    System.out.println("I moved!");
//                }
                destCell.setAmountOfMigrants(nameOfOrganism, destCell.getAmountOfMigrants(nameOfOrganism) + 1);
                destCell.getCertainMigrants(nameOfOrganism).add(this);
                nextDestination = destCell;
//                System.out.println("old cell: " + srcCell + ", new cell: " + destCell);
            }
            movingPoints = maxCellByMove;
        }
    }

    private Cell findNextCell(Cell srcCell) {
        if (movingPoints > 0) {
            List<Cell> cells = srcCell.getCellsNearby();
            List<Integer> order = new ArrayList<>(IntStream.rangeClosed(0, cells.size() - 1).boxed().toList());
            Collections.shuffle(order);
            for (Integer indexOfCell : order) {
                Cell destCell = cells.get(indexOfCell);
                Set<Organism> organisms = destCell.getCertainResidents(nameOfOrganism);
                if (organisms.size() + destCell.getAmountOfMigrants(nameOfOrganism) < cellLimit) {
                    movingPoints--;
                    Cell res = findNextCell(destCell);
                    return Objects.requireNonNullElse(res, destCell);
                }
            }
        }
        return null;
    }

    public void changeWeightByTime() {
        weight -= perc;
        if (weight <= 0) {
            this.alive = false;
        }
    }

    public void eat(Cell cell) {//TODO check if no points
        int points = pointsOfEating;
        if (weight < maxWeight / 2 /*|| this instanceof Herbivore*/) {
            boolean unexpectedDeath = false;
            for (Map.Entry<String, Vault> entry : cell.getPersons().entrySet()) {//TODO change to more random (or more smart) searching of victim
                if (unexpectedDeath || (pointsOfEating == 0 /*&& this instanceof Carnivore*/) || phase.get() == Phase.BUSY || weight > maxWeight)
                    break;//TODO change points compare to <= (make less brittle)
                String name = entry.getKey();
                if (cell.getCertainResidents(name).isEmpty()) continue;
                Integer chance = chances.get(name);
                if (chance != null && chance > 0) {
                    Set<Organism> residents = entry.getValue().getResidents();
                    for (int i = 0; i < pointsOfEating && points > 0; i++) {
                        Organism victim = residents.stream().skip(ThreadLocalRandom.current().nextInt(0, residents.size())).findFirst().orElse(null);
//                    }
//                    for (Organism victim : entry.getValue().getResidents()) {
                        if (unexpectedDeath || (pointsOfEating == 0 /*&& this instanceof Carnivore*/) || phase.get() == Phase.BUSY || weight > maxWeight) {
                            break;
                        }
                        if (victim.isAlive() && victim.phase.compareAndSet(Phase.FREE, Phase.BUSY)) {
                            points--;
                            if (phase.compareAndSet(Phase.FREE, Phase.BUSY)) {
                                if (isAlive()) {
                                    if (victim.isAlive()) {
                                        int res = ThreadLocalRandom.current().nextInt(1, 101);
                                        if (res <= chance) {
                                            double atePortion = Math.min(Math.min(victim.getWeight(), this.feedPortion), maxWeight - weight);
                                            victim.setWeight(victim.getWeight() - atePortion);
                                            if (victim.getWeight() <= 0) {
                                                victim.setAlive(false);
                                            }
                                            victim.phase.compareAndSet(Phase.BUSY, Phase.FREE);
                                            this.weight += atePortion;
                                            phase.compareAndSet(Phase.BUSY, Phase.FREE);
                                        } else {
                                            victim.phase.compareAndSet(Phase.BUSY, Phase.FREE);
                                            phase.compareAndSet(Phase.BUSY, Phase.FREE);
                                        }
                                    } else {
                                        victim.phase.compareAndSet(Phase.BUSY, Phase.FREE);
                                        phase.compareAndSet(Phase.BUSY, Phase.FREE);
                                        break;
                                    }
                                } else {
                                    victim.phase.compareAndSet(Phase.BUSY, Phase.FREE);
                                    phase.compareAndSet(Phase.BUSY, Phase.FREE);
                                    unexpectedDeath = true;
                                    break;
                                }
                            } else {
                                victim.phase.compareAndSet(Phase.BUSY, Phase.FREE);
                            }
                        }
                    }

                }
            }
//            System.out.println(counter + "!");
//            pointsOfEating = (Integer) characteristics.get(Characteristics.POINTS_OF_EATING);
        }
    }

    public static AtomicLong getIdGenerator() {
        return idGenerator;
    }

    public static void setIdGenerator(AtomicLong idGenerator) {
        Organism.idGenerator = idGenerator;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getPerc() {
        return perc;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getCellLimit() {
        return cellLimit;
    }

    public void setCellLimit(int cellLimit) {
        this.cellLimit = cellLimit;
    }

    public int getMaxCellByMove() {
        return maxCellByMove;
    }

    public void setMaxCellByMove(int maxCellByMove) {
        this.maxCellByMove = maxCellByMove;
    }

    public double getFeedPortion() {
        return feedPortion;
    }


    public String getPathToImg() {
        return pathToImg;
    }

    @Override
    public String toString() {
        return "Organism{" +
                alive
                +
                "id=" + id +
                ", weight=" + weight +
                //", cellLimit=" + cellLimit +
                //", maxCellByMove=" + maxCellByMove +
                //", feedPortion=" + feedPortion +
                '}';
    }

    @Override
    public Organism clone() {
        try {
            Organism clone = (Organism) super.clone();
            clone.setId(idGenerator.incrementAndGet());
            clone.setWeight(this.weight);//TODO CHANGE THE WEIGHT!!!
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public AtomicReference<Phase> getPhase() {
        return phase;
    }

    public void setPhase(AtomicReference<Phase> phase) {
        this.phase = phase;
    }

    public AtomicReference<LifeStatus> getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(AtomicReference<LifeStatus> liveStatus) {
        this.liveStatus = liveStatus;
    }

    public Map<String, Integer> getChances() {
        return chances;
    }

    public void setChances(Map<String, Integer> chances) {
        this.chances = chances;
    }


    public void setCellLimit(Integer cellLimit) {
        this.cellLimit = cellLimit;
    }

    public void setMaxCellByMove(Integer maxCellByMove) {
        this.maxCellByMove = maxCellByMove;
    }

    public void setPathToImg(String pathToImg) {
        this.pathToImg = pathToImg;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Cell getNextDestination() {
        return nextDestination;
    }

    public static AtomicInteger getAmountOfAnimals() {
        return amountOfAnimals;
    }

    public Double getMaxWeight() {
        return maxWeight;
    }

    public AtomicLong getWeight2() {
        return weight2;
    }

    public int getMaxWeight2() {
        return maxWeight2;
    }

    public int getPerc2() {
        return perc2;
    }

    public Integer getMovingPoints() {
        return movingPoints;
    }

    public String getNameOfOrganism() {
        return nameOfOrganism;
    }

    public Integer getPointsOfEating() {
        return pointsOfEating;
    }

    public Map<Characteristics, Number> getCharacteristics() {
        return characteristics;
    }
}
