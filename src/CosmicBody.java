import java.util.ArrayList;
import java.util.List;

abstract class CosmicBody {
    protected String name;
    protected double mass; // в кг
    protected double diameter; // в км
    protected double distanceFromSun; // в млн км
    protected int discoveryYear;

    // Статический список для хранения всех космических тел
    protected static List<CosmicBody> allCosmicBodies = new ArrayList<>();

    public CosmicBody(String name, double mass, double diameter, double distanceFromSun, int discoveryYear) {
        this.name = name;
        this.mass = mass;
        this.diameter = diameter;
        this.distanceFromSun = distanceFromSun;
        this.discoveryYear = discoveryYear;
    }

    // Абстрактные методы
    public abstract String getType();
    public abstract String getInfo(); // Возвращает информацию как строку

    // Общие методы расчетов
    public double calculateVolume() {
        double radius = diameter / 2;
        return (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
    }

    public double calculateDensity() {
        double volume = calculateVolume() * 1e9; // переводим в куб. метры
        return mass / volume;
    }

    // Статические методы для работы со списком всех космических тел
    public static void addCosmicBody(CosmicBody body) {
        allCosmicBodies.add(body);
    }

    public static List<CosmicBody> getAllCosmicBodies() {
        return new ArrayList<>(allCosmicBodies);
    }

    // Геттеры
    public String getName() { return name; }
    public double getMass() { return mass; }
    public double getDiameter() { return diameter; }
    public double getDistanceFromSun() { return distanceFromSun; }
    public int getDiscoveryYear() { return discoveryYear; }

    // Сеттеры (без setDistanceFromSun - это управляется Галактикой)
    public void setName(String name) { this.name = name; }
    public void setMass(double mass) { this.mass = mass; }
    public void setDiameter(double diameter) { this.diameter = diameter; }
    public void setDiscoveryYear(int discoveryYear) { this.discoveryYear = discoveryYear; }
}