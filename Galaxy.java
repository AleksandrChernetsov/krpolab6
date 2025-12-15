import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Galaxy {
    private List<Planet> planets;
    private List<Star> stars;
    private String name;

    public Galaxy(String name) {
        this.name = name;
        this.planets = new ArrayList<>();
        this.stars = new ArrayList<>();
    }

    // Методы для работы с планетами
    public void addPlanet(Planet planet) {
        planets.add(planet);
    }

    public List<Planet> getAllPlanets() {
        return new ArrayList<>(planets);
    }

    // Основные функции из задания
    public Planet findFurthestPlanet() {
        if (planets.isEmpty()) return null;

        Planet furthest = planets.get(0);
        for (Planet planet : planets) {
            if (planet.getDistanceFromSun() > furthest.getDistanceFromSun()) {
                furthest = planet;
            }
        }
        return furthest;
    }

    public List<Planet> findPlanetsCloserThanEarth() {
        List<Planet> result = new ArrayList<>();
        double earthDistance = 149.6; // расстояние Земли от Солнца в млн км

        for (Planet planet : planets) {
            if (planet.getDistanceFromSun() < earthDistance && !planet.getName().equals("Земля")) {
                result.add(planet);
            }
        }
        return result;
    }

    public List<Planet> sortPlanetsByDistance() {
        List<Planet> sortedPlanets = new ArrayList<>(planets);
        sortedPlanets.sort(Comparator.comparingDouble(Planet::getDistanceFromSun));
        return sortedPlanets;
    }

    public Planet findPlanetByName(String planetName) {
        for (Planet planet : planets) {
            if (planet.getName().equalsIgnoreCase(planetName)) {
                return planet;
            }
        }
        return null;
    }

    public void updatePlanetDistance(String planetName, double newDistance) {
        Planet planet = findPlanetByName(planetName);
        if (planet != null) {
            try {
                java.lang.reflect.Field field = CosmicBody.class.getDeclaredField("distanceFromSun");
                field.setAccessible(true);
                field.set(planet, newDistance);
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при обновлении расстояния: " + e.getMessage());
            }
        }
    }

    // Методы для работы со звездами
    public void addStar(Star star) {
        stars.add(star);
    }

    public List<Star> getAllStars() {
        return new ArrayList<>(stars);
    }

    // Геттеры
    public String getName() { return name; }
    public int getPlanetCount() { return planets.size(); }
    public int getStarCount() { return stars.size(); }
}