class Planet extends CosmicBody {
    private int numberOfMoons;
    private boolean hasRings;
    private String planetType;
    private double orbitalPeriod; // в земных годах
    private double rotationPeriod; // в земных сутках

    public Planet(String name, double mass, double diameter, double distanceFromSun,
                  int discoveryYear, int numberOfMoons, boolean hasRings,
                  String planetType, double orbitalPeriod, double rotationPeriod) {
        super(name, mass, diameter, distanceFromSun, discoveryYear);
        this.numberOfMoons = numberOfMoons;
        this.hasRings = hasRings;
        this.planetType = planetType;
        this.orbitalPeriod = orbitalPeriod;
        this.rotationPeriod = rotationPeriod;
        CosmicBody.addCosmicBody(this);
    }

    @Override
    public String getType() {
        return "Planet";
    }

    @Override
    public String getInfo() {
        return String.format(
                "Планета: %s\n" +
                        "  Масса: %.2e кг\n" +
                        "  Диаметр: %.0f км\n" +
                        "  Расстояние от Солнца: %.1f млн км\n" +
                        "  Год открытия: %s\n" +
                        "  Количество спутников: %d\n" +
                        "  Есть кольца: %s\n" +
                        "  Тип планеты: %s\n" +
                        "  Орбитальный период: %.2f лет\n" +
                        "  Период вращения: %.2f суток\n" +
                        "  Плотность: %.2f кг/м³\n" +
                        "  Объем: %.2e куб. км",
                name, mass, diameter, distanceFromSun,
                discoveryYear < 0 ? Math.abs(discoveryYear) + " до н.э." : discoveryYear,
                numberOfMoons, hasRings ? "Да" : "Нет", planetType,
                orbitalPeriod, rotationPeriod, calculateDensity(), calculateVolume()
        );
    }

    // Специфические методы планеты
    public boolean isHabitable() {
        return planetType.equals("землеподобная") && distanceFromSun > 100 && distanceFromSun < 300;
    }

    public double calculateOrbitalSpeed() {
        double orbitCircumference = 2 * Math.PI * distanceFromSun;
        return orbitCircumference / (orbitalPeriod * 365);
    }

    // Геттеры и сеттеры для специфических полей планеты
    public int getNumberOfMoons() { return numberOfMoons; }
    public boolean hasRings() { return hasRings; }
    public String getPlanetType() { return planetType; }
    public double getOrbitalPeriod() { return orbitalPeriod; }
    public double getRotationPeriod() { return rotationPeriod; }

    public void setNumberOfMoons(int numberOfMoons) { this.numberOfMoons = numberOfMoons; }
    public void setHasRings(boolean hasRings) { this.hasRings = hasRings; }
    public void setPlanetType(String planetType) { this.planetType = planetType; }
    public void setOrbitalPeriod(double orbitalPeriod) { this.orbitalPeriod = orbitalPeriod; }
    public void setRotationPeriod(double rotationPeriod) { this.rotationPeriod = rotationPeriod; }
}