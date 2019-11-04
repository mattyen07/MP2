package cpen221.mp2.spaceship;

import cpen221.mp2.controllers.GathererStage;
import cpen221.mp2.controllers.HunterStage;
import cpen221.mp2.controllers.Spaceship;
import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.ImGraph;
import cpen221.mp2.models.Link;
import cpen221.mp2.models.Planet;
import cpen221.mp2.models.PlanetStatus;
import cpen221.mp2.models.Universe;
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
    private ImGraph<Planet, Link> universeMap;

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
        universeMap = state.planetGraph();

        while(!state.currentPlanet().equals(state.earth())) {
            executeRoute(bestRoute(state), state);
        }

    }

    /**
     * will move spaceship along specified route.
     * @param route is a continuous link of planets (route[i+1] is adjacent to route[i])
     * @param state
     */
    private void executeRoute(List<Planet> route, GathererStage state) {
        if(route.contains(state.currentPlanet())) {
            route.remove(state.currentPlanet());
        }

        for(Planet p: route) {
            state.moveTo(p);
        }
    }

    /**
     * returns the total fuel needed to travel to destination from start along shortest path.
     * @requires there exists at least one path between start and destination.
     * @param start starting planet
     * @param destination end planet
     * @param state
     * @return fuelRequired for journey.
     */
    private int fuelRequired(Planet start, Planet destination, GathererStage state) {

        int fuelRequired = universeMap.pathLength(universeMap.shortestPath(start, destination));

        return fuelRequired;

    }

    /**
     * Will return the best route to take.
     * @param state
     * @return
     */
    private List<Planet> bestRoute(GathererStage state) {

        List<Planet> bestRoute;
        Planet bestPlanet = state.earth();

        Map<Planet, Double> viabilityMap = viabilityMap(state);
        double maxViability = viabilityMap.get(bestPlanet);

        for(Planet p: viabilityMap.keySet()) {
            if(viabilityMap.get(p) > maxViability) {
                bestPlanet = p;
                maxViability = viabilityMap.get(p);
            }
        }

        bestRoute = universeMap.shortestPath(state.currentPlanet(), bestPlanet);


        return bestRoute;
    }

    /**
     * returns a map where keys are Planets that map to a value
     * describing how beneficial it is to travel to said planet.
     * @param state
     * @return Map containing how beneficial travelling to each planet in state.planets() is.
     */
    private Map<Planet, Double> viabilityMap(GathererStage state) {
        Map<Planet, Double> viabilityMap = new HashMap<>();
        for(Planet p: state.planets()) {
            viabilityMap.put(p, viability(p, state));
        }
        return viabilityMap;
    }

    /**
     * determines how desirable each planet is as a destination
     * @param p
     * @param state
     * @return
     */
    private double viability(Planet p, GathererStage state) {
        List<Planet> route = universeMap.shortestPath(state.currentPlanet(), p);

        int fuelNeeded = fuelRequired(state.currentPlanet(), p, state) + fuelRequired(p, state.earth(), state);
        if(fuelNeeded > state.fuelRemaining() || route.contains(state.earth())) {
            return 0.0;
        }

        return (double) spiceCollected(route) / (double) fuelNeeded;
        //return spiceCollected(route);
    }

    private int spiceCollected(List<Planet> route) {
        int totalSpice = 0;
        for(Planet p: route) {
            totalSpice = totalSpice + p.spice();
        }
        return totalSpice;
    }








}
