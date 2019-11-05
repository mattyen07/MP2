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
    private ImGraph<Planet, Link> universeMap; //Immutable ImGraph representing universe.
    private Set<Planet> unvisitedPlanets = new HashSet<>(); //a set of planets not visited during gather
    // the number of planets to be considered as destinations for possible spice gathering routes.
    private final static int NUM_PLANETS_TO_CHECK = 50; //must be greater than 0.

    /**
     * The spaceship is on the location given by parameter state.
     * Move the spaceship to Kamino and then return.
     * This completes the first phase of the mission.<br><br>
     * <p>
     * If the spaceship continues to move after reaching Kamino, rather than
     * returning, it will not count. A return from this procedure while
     * not on Kamino count as a failure.<br><br>
     * <p>
     * There is no limit to how many steps you can take, but the score is
     * directly related to how long it takes you to find Kamino.<br><br>
     * <p>
     * At every step, you know only the current planet's ID, the IDs of
     * neighboring planets, and the strength of the signal from Kamino
     * at each planet.<br><br>
     * <p>
     * In this stage of the game,<br>
     * (1) In order to get information about the current state, use
     * functions currentID(), neighbors(), and signal().<br><br>
     * <p>
     * (2) Use method onKamino() to know if your ship is on Kamino.<br><br>
     * <p>
     * (3) Use method moveTo(int id) to move to a neighboring planet
     * with the given ID. Doing this will change state to reflect the
     * ship's new position.
     */
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


    /**
     * The spaceship is on the location given by state. Get back to Earth
     * without running out of fuel and return while on Earth. Your ship can
     * determine how much fuel it has left via method fuelRemaining(), and how
     * much fuel is needed to travel on a link via link's fuelNeeded().<br><br>
     * <p>
     * Each Planet has some spice. Moving to a Planet automatically
     * collects any spice it carries, which increases your score. your
     * objective is to return to earth with as much spice as possible.<br><br>
     * <p>
     * You now have access to the entire underlying graph, which can be
     * accessed through parameter state. currentNode() and earth() return
     * planets of interest, and planets() returns a collection of
     * all planets in the graph.<br><br>
     * <p>
     * Note: Use moveTo() to move to a destination node adjacent to
     * your ship's current node.
     */
    @Override
    public void gather(GathererStage state) {
        universeMap = state.planetGraph();

        for (Planet p : state.planets()) {
            unvisitedPlanets.add(p);
        }

        while (!state.currentPlanet().equals(state.earth())) {
            executeRoute(bestRoute(state), state);
        }

    }

    /**
     * Moves spaceship along path designated by route starting with the first planet in the list and terminating
     * with the last planet.
     * @modifies unvisitedPlanets by removing planets from the set as they are visited.
     * @modifies planet.spice, as planets are visited, all spice is collected.
     * @param route the path that the spaceship will move along. Consecutive planets in route must be adjacent.
     *              First planet in route must be either state.currentPlanet() or adjacent to state.currentPlanet().
     * @param state of game.
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
        }

    }

    /**
     * Returns the amount of fuel needed to execute a specific route
     * @param route path to be travelled. Route must be a valid path.
     * @param state of game.
     * @return fuel needed to travel along route.
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
     * Will return a list of planets describing the route with the highest validity.
     * If all routes have a validity of 0.0, returns a route describing the shortest path from state.currentPlanet()
     * to state.earth()
     * @param state of game
     * @return best route to take.
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
     * Returns a map that maps numPlanets planets of interest to a viability score representing how beneficial
     * is it to travel to said planet from state.currentPlanet().
     * Planets of interest are determined to be the planets with the most spice plus state.earth().
     * @param state of game.
     * @param numPlanets > 0, number of planets to get viability scores for.
     * @return Map of viability for numPlanets of interest.
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
     * Determines a viability score >= 0.0 representing how desirable a planet is
     * as a destination during GathererStage based on state.currentPlanet().
     * The higher the viability score, the more beneficial it is to visit the planet
     * during GathererStage. Viability score for planet P is determined by taking the total spice collected on
     * shortest path from state.currentPlanet() to P divided by the fuel required to move along said path.
     * If unvisitedPlanets.contains(P) then a viability of 0.0 is assigned instead.
     * If there is not enough fuel remaining to move to state.earth() after moving to P,
     * the P gets a viability of 0.0.
     * @param destination the planet for which the viability score is calculated.
     *                    destination exists and can be reached from state.currentPlanet()
     *                    if it has not been visited already.
     * @param state of game
     * @return viability score for destination.
     */
    private double viability(Planet destination, GathererStage state) {
        List<Planet> routeDest = universeMap.shortestPath(state.currentPlanet(), destination);
        List<Planet> routeEarth = universeMap.shortestPath(destination, state.earth());

        int fuelNeeded = fuelRequired(routeDest, state) + fuelRequired(routeEarth, state);
        if (fuelNeeded > state.fuelRemaining() || routeDest.contains(state.earth())) {
            return 0.0;
        }
        return (double) spiceCollected(routeDest) / (double) fuelNeeded;
        //return spiceCollected(route);
    }

    /**
     * Returns the total amount of spice that will be collected if spaceship moves along path
     * defined by route.
     * @param route potential path of spaceship. All planets in route must exist.
     * @return total amount of spice.
     */
    private int spiceCollected(List<Planet> route) {
        int totalSpice = 0;
        for (Planet p: route) {
            totalSpice = totalSpice + p.spice();
        }
        return totalSpice;
    }

    /**
     * Returns a set of max size number of the planets in unvisitedPlanets containing the most spice.
     * Ex: If there are less or equal planets in unvisitedPlanets than number,
     * returns a set containing all planets in unvisitedPlanets
     * Ex: If there are more planets in unvisitedPlanets than number,
     * Then planets are added in order of highest spice until set size is equal to number.
     * @param number of max planets in set.
     * @param state of game
     * @return set of spiciest unvisited planets.
     */
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

    /**
     * Returns the planet containing the highest amount of spice in the Set planets.
     * If Set is empty returns state.earth() even if state.earth() is not part of planets.
     * @param planets set of planets to look at.
     * @param state of game
     * @return planet of highest spice.
     */
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
