package io.github.kahar.ai.survivors;

import io.github.kahar.ai.survivors.grid.Grid;
import io.github.kahar.ai.survivors.grid.Vector;
import io.github.kahar.ai.survivors.grid.object.Creature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        int width = 50;
        int height = 50;
        int numberOfCreatures = 100;

        int generations = 50;
        int turnsInGeneration = 50;

        List<Creature> creatures = generateInitialPopulation(numberOfCreatures);
        Grid grid = null;
        for (int generation = 0; generation < generations; generation++) {
            System.out.println("Generation " + generation + " Start\n");
            grid = new Grid(width, height, creatures);
            for (int i = 0; i < turnsInGeneration; i++) {
                grid.turn();
            }
            List<Creature> survivors = new ArrayList<>();
            for (int x = 0; x < 25; x++) {
                for (int y = 0; y < 50; y++) {
                    Vector vector = new Vector(x, y);
                    Creature creature = grid.get(vector);
                    if (creature != null) {
                        survivors.add(creature);
                    }
                }
            }
            creatures = new ArrayList<>();
            creatures.addAll(survivors);
            if (generation + 1 < generations) {
                int numberToGenerate = numberOfCreatures - survivors.size();
                System.out.println("Number of new children " + numberToGenerate);
                for (int i = 0; i < numberToGenerate; i = i + 2) {
                    Random random = new Random();
                    Creature[] children = survivors.get(random.nextInt(survivors.size())).crossover(survivors.get(random.nextInt(survivors.size())));
                    children[0].mutate();
                    children[1].mutate();
                    creatures.addAll(Arrays.asList(children));
                }
            } else {
                System.out.println("Generation " + generation + " End\n");
            }
        }
        for (int x = 0; x < 50; x++) {
            for (int y = 0; y < 50; y++) {
                Vector vector = new Vector(x, y);
                Creature creature = grid.get(vector);
                if (creature != null) {
                    System.out.println(creature.getBrain());
                }
            }
        }

        System.out.println("All ended \n" + grid);

//
//
//        for(int i = 0; i<50 ; i++){
//            grid.turn();
//        }
//        List<Creature> survivors = new ArrayList<>();
//        for(int x = 0; x<50;x++){
//            for(int y = 0; y<50; y++){
//                Vector vector = new Vector(x,y);
//                Creature creature = grid.get(vector);
//                if(creature != null){
//                    survivors.add(creature);
//                }
//            }
//        }
//        creatures = new ArrayList<>();
//        creatures.addAll(survivors);
//        int numberToGenerate = numberOfCreatures - survivors.size();
//        for(int i = 0; i < numberToGenerate; i=i+2){
//            Random random = new Random();
//            Creature[] children = survivors.get(random.nextInt(survivors.size())).crossover(survivors.get(random.nextInt(survivors.size())));
//            children[0].mutate();
//            children[1].mutate();
//            creatures.addAll(Arrays.asList(children));
//        }
//        System.out.println("RESULT New Begin = \n" + grid.toString());
//        grid = new Grid(width,height,creatures);
//        for(int i = 0; i<50 ; i++){
//            grid.turn();
//        }
//
//        System.out.println("RESULT END = \n" + grid.toString());

    }

    private static List<Creature> generateInitialPopulation(int numberOfCreatures) {
        List result = new ArrayList();
        for (int i = 0; i < numberOfCreatures; i++) {
            result.add(new Creature());
        }
        return result;
    }
}