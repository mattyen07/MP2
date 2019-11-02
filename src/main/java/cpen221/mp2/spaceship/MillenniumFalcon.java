package cpen221.mp2.spaceship;

import cpen221.mp2.controllers.GathererStage;
import cpen221.mp2.controllers.HunterStage;
import cpen221.mp2.controllers.Spaceship;
import cpen221.mp2.graph.ImGraph;
import cpen221.mp2.models.Link;
import cpen221.mp2.models.Planet;
import cpen221.mp2.models.PlanetStatus;
import cpen221.mp2.util.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An instance implements the methods needed to complete the mission.
 */
public class MillenniumFalcon implements Spaceship {
    long startTime = System.nanoTime(); // start time of rescue phase

    @Override
    public void hunt(HunterStage state) {
        ArrayList<Integer> seenOnce = new ArrayList<>();
        ArrayList<Integer> deadEnd = new ArrayList<>();
        PlanetStatus[] neighbours = state.neighbors();
        int earthID = state.currentID();
        double maxSignal = 0;
        int nextMove = state.currentID();

        // set earth as current id, if we hit a dead end, move to earth and restart
        while (!state.onKamino()) {

            for (int i = 0; i < neighbours.length; i++) {
                if (neighbours[i].signal() > maxSignal) {
                    maxSignal = neighbours[i].signal();
                    nextMove = neighbours[i].id();
                }
            }

            if (!seenOnce.contains(nextMove)) {
                state.moveTo(nextMove);
                seenOnce.add(nextMove);
            }


        }




    }

    @Override
    public void gather(GathererStage state) {
        // TODO: Implement this method
    }

}
