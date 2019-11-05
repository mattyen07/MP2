package cpen221.mp2.spaceship;

import cpen221.mp2.controllers.GathererStage;
import cpen221.mp2.controllers.HunterStage;
import cpen221.mp2.controllers.Spaceship;
import cpen221.mp2.graph.ImGraph;
import cpen221.mp2.models.Link;
import cpen221.mp2.models.Planet;
import cpen221.mp2.models.PlanetStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

/**
 * An instance implements the methods needed to complete the mission.
 */
public class MillenniumFalcon implements Spaceship {
    long startTime = System.nanoTime(); // start time of rescue phase
    private ImGraph<Planet, Link> universeMap;
    private Set<Planet> unvisitedPlanets = new HashSet<>();
    private Map<Planet, Integer> spiceMap = new HashMap<>();
    private final static int NUM_PLANETS_TO_CHECK = 20;

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

        for (Planet p : state.planets()) {
            spiceMap.replace(p, p.spice());
            unvisitedPlanets.add(p);
        }

        while (!state.currentPlanet().equals(state.earth())) {
            executeRoute(bestRoute(state), state);
        }

    }

    /**
     * will move spaceship along specified route.
     * @param route is a continuous link of planets (route[i+1] is adjacent to route[i])
     * @param state
     */
    private void executeRoute(List<Planet> route, GathererStage state) {
        if (route.contains(state.currentPlanet())) {
            route.remove(state.currentPlanet());
        }
        if (unvisitedPlanets.contains(state.currentPlanet())) {
            unvisitedPlanets.remove(state.currentPlanet());
        }

        for (Planet p: route) {
            state.moveTo(p);
            if (unvisitedPlanets.contains(p)) {
                unvisitedPlanets.remove(p);
            }
            spiceMap.replace(p, 0);
        }

    }

    /**
     * returns the total fuel needed to travel to destination from start along shortest path.
     * @requires there exists at least one path between start and destination.
     * @param route route to take.
     * @param state
     * @return fuelRequired for journey.
     */
    private int fuelRequired(List<Planet> route, GathererStage state) {

        int fuelRequired = 0;
        Planet start = (Planet) route.toArray()[0];
        Planet previousPlanet = start;
        for (Planet p : route) {

            if (!p.equals(start)) {
                fuelRequired = fuelRequired + universeMap.getEdge(previousPlanet, p).fuelNeeded();
            }
            previousPlanet = p;
        }

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

        Map<Planet, Double> viabilityMap = viabilityMap(state, NUM_PLANETS_TO_CHECK);
        double maxViability = viabilityMap.get(bestPlanet);

        for (Planet p : viabilityMap.keySet()) {
            if (viabilityMap.get(p) > maxViability) {
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
    private Map<Planet, Double> viabilityMap(GathererStage state, int numPlanets) {
        Map<Planet, Double> viability = new HashMap<>();
        Set<Planet> interestingPlanets = topSpiciestPlanets(numPlanets - 1, state);
        interestingPlanets.add(state.earth());
        for (Planet p : interestingPlanets) {
            viability.put(p, viability(p, state));
        }
        return viability;
    }

    /**
     * determines how desirable each planet is as a destination
     * @param destination
     * @param state
     * @return
     */
    private double viability(Planet destination, GathererStage state) {
        //don't want to revisit planets.
        if (!unvisitedPlanets.contains(destination)) {
            return 0.0;
        }

        List<Planet> routeDest = universeMap.shortestPath(state.currentPlanet(), destination);
        List<Planet> routeEarth = universeMap.shortestPath(destination, state.earth());

        int fuelNeeded = fuelRequired(routeDest, state) + fuelRequired(routeEarth, state);
        if (fuelNeeded > state.fuelRemaining() || routeDest.contains(state.earth())) {
            return 0.0;
        }
        return (double) spiceCollected(routeDest) / (double) fuelNeeded;
        //return spiceCollected(route);
    }

    private int spiceCollected(List<Planet> route) {
        int totalSpice = 0;
        for (Planet p: route) {
            totalSpice = totalSpice + p.spice();
        }
        return totalSpice;
    }


    private Set<Planet> topSpiciestPlanets(int number, GathererStage state) {

        Set<Planet> planets = new HashSet<>(unvisitedPlanets);
        planets.remove(state.earth());

        if (number >= planets.size()) {
            return planets;
        }
        Set<Planet> spiciestPlanets = new HashSet<>();

        for (int i = 0; i < number; i++) {
            spiciestPlanets.add(spiciestPlanet(planets, state));
            planets.remove(spiciestPlanet(planets, state));
        }

        return spiciestPlanets;
    }

    private Planet spiciestPlanet(Set<Planet> planets, GathererStage state) {

        Planet mostSpicy = state.earth();
        int maxSpice = mostSpicy.spice();
        for (Planet p : planets) {
            if (p.spice() >= maxSpice) {
                maxSpice = p.spice();
                mostSpicy = p;
            }
        }
        return mostSpicy;
    }

}
