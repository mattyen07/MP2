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
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
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
        Set<Integer> visited = new HashSet<>();
        Map<Integer, Integer> parentList = new HashMap<>();
        int currID = state.currentID();
        int nextMove = state.currentID();
        // set earth as current id, traverse down a path, if we hit a dead end, move back up the path until we have a new path

        parentList.put(currID, currID);

        while (!state.onKamino()) {
            PlanetStatus[] neighbours = state.neighbors();
            double maxSignal = 0;

            for (PlanetStatus planet : neighbours) {
                if (planet.signal() > maxSignal && !visited.contains(planet.id())) {
                    maxSignal = planet.signal();
                    nextMove = planet.id();
                }
            }

            if (!visited.contains(nextMove)) {
                visited.add(nextMove);
                state.moveTo(nextMove);
                parentList.put(nextMove, currID);
                currID = nextMove;
            } else {
                nextMove = parentList.get(currID);
                state.moveTo(nextMove);
                currID = nextMove;
            }
        }

    }

    @Override
    public void gather(GathererStage state) {
        ImGraph<Planet, Link> universeMap = state.planetGraph();

        List<Planet> shortestPathToEarth = universeMap.shortestPath(state.currentPlanet(), state.earth());

        shortestPathToEarth.remove(state.currentPlanet());

        for(Planet p : shortestPathToEarth) {
            state.moveTo(p);
        }
    }


}
