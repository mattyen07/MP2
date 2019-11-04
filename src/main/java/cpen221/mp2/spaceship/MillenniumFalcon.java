package cpen221.mp2.spaceship;

import cpen221.mp2.controllers.GathererStage;
import cpen221.mp2.controllers.HunterStage;
import cpen221.mp2.controllers.Spaceship;
import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.ImGraph;
import cpen221.mp2.models.Link;
import cpen221.mp2.models.Planet;
import cpen221.mp2.models.PlanetStatus;
import cpen221.mp2.util.Heap;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An instance implements the methods needed to complete the mission.
 */
public class MillenniumFalcon implements Spaceship {
    long startTime = System.nanoTime(); // start time of rescue phase
    private ImGraph<Planet, Link> universeMap;

    @Override
    public void hunt(HunterStage state) {
        Set<Integer> visited = new HashSet<>();
        Map<Integer, Integer> parentList = new HashMap<>();
        int currID = state.currentID();
        int nextMove = state.currentID();

        // set earth as current id, traverse down a path, if we hit a dead end,
        // move back up the path until we have a new path

        parentList.put(currID, currID); //puts earth into parent list

        //while we are not on kamino, continue to go down a path
        while (!state.onKamino()) {
            PlanetStatus[] neighbours = state.neighbors();
            double maxSignal = 0.0;

            //for each planet in the neighbour array, find the planet with the
            // strongest signal that hasn't been visited
            for (PlanetStatus planet : neighbours) {
                if (planet.signal() >= maxSignal && !visited.contains(planet.id())) {
                    maxSignal = planet.signal();
                    nextMove = planet.id();
                }
            }

            //if our visited doesn't have the nextMove, then we add to the visited list and move
            //otherwise we go back on our path to find a planet that has unvisited neighbours
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
        universeMap = state.planetGraph();

        Map<Planet, Double> weightedMap = updateWeightedMap(state);

        while(!spiciestPlanet(weightedMap, state).equals(state.currentPlanet())) {
            Planet p = spiciestPlanet(weightedMap, state);
            moveToPlanet(p, state);
            weightedMap = updateWeightedMap(state);
        }
        moveToPlanet(state.earth(), state);

    }

    private Map<Planet, Double> updateWeightedMap(GathererStage state) {
        Map<Planet, Double> weightedPlanetMap = new HashMap<>();
        for(Planet P: state.planets()) {
            int distance = universeMap.pathLength(universeMap.shortestPath(state.currentPlanet(), P));
            int spice = P.spice();
            if (distance!=0) {
                weightedPlanetMap.put(P, (double) spice / (double) distance);
            }
        }

        return weightedPlanetMap;
    }

    /**
     * returns the most economical planet to visit.
     * @param weightedMap
     * @param state
     * @return
     */
    private Planet spiciestPlanet(Map<Planet, Double> weightedMap, GathererStage state) {
        double score = 0;
        Planet bestPlanet = state.currentPlanet();
        for(Planet p: weightedMap.keySet()) {
            //planet is good if there is enough fuel and if its weight makes it viable.
            if (score <= weightedMap.get(p) && enoughFuel(p, state)) {
                bestPlanet = p;
                score = weightedMap.get(p);
            }
        }
        return bestPlanet;
    }

    /**
     * see if spaceship has enough fuel to get to earth from state.currentPlanet while passing through planet.
     * @param planet
     * @param state
     * @return
     */
    private boolean enoughFuel(Planet planet, GathererStage state) {

        int fuelRequired = universeMap.pathLength(universeMap.shortestPath(planet, state.earth()));
        fuelRequired =  fuelRequired + universeMap.pathLength(universeMap.shortestPath(state.currentPlanet(), planet));

        if(fuelRequired>state.fuelRemaining()) {
            return true;
        }

        return false;

    }

    /**
     * attempt to move to planet in the shortest path.
     * @param p
     * @param state
     */
    private void moveToPlanet(Planet p, GathererStage state) {
        List<Planet> shortestPath = universeMap.shortestPath(state.currentPlanet(), p);
        shortestPath.remove(state.currentPlanet());
        for(Planet nextPlanet: shortestPath) {
            state.moveTo(nextPlanet);
        }

    }


}
