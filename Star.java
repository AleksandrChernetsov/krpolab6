class Star extends CosmicBody {
    private String spectralClass;
    private double surfaceTemperature; // в Кельвинах
    private double luminosity; // светимость относительно Солнца
    private int age; // в млн лет
    private boolean hasPlanetarySystem;

    public Star(String name, double mass, double diameter, double distanceFromSun,
                int discoveryYear, String spectralClass, double surfaceTemperature,
                double luminosity, int age, boolean hasPlanetarySystem) {
        super(name, mass, diameter, distanceFromSun, discoveryYear);
        this.spectralClass = spectralClass;
        this.surfaceTemperature = surfaceTemperature;
        this.luminosity = luminosity;
        this.age = age;
        this.hasPlanetarySystem = hasPlanetarySystem;
        CosmicBody.addCosmicBody(this);
    }

    @Override
    public String getType() {
        return "Star";
    }

    @Override
    public String getInfo() {
        return String.format(
                "Звезда: %s\n" +
                        "  Масса: %.2e кг\n" +
                        "  Диаметр: %.0f км\n" +
                        "  Расстояние от Солнца: %.1f млн км\n" +
                        "  Спектральный класс: %s\n" +
                        "  Температура поверхности: %.0f K\n" +
                        "  Светимость: %.1f L☉\n" +
                        "  Возраст: %d млн лет\n" +
                        "  Есть планетная система: %s\n" +
                        "  Цвет: %s",
                name, mass, diameter, distanceFromSun, spectralClass,
                surfaceTemperature, luminosity, age,
                hasPlanetarySystem ? "Да" : "Нет", getStarColor()
        );
    }

    // Специфические методы звезды
    public String getStarColor() {
        if (surfaceTemperature > 30000) return "Голубая";
        else if (surfaceTemperature > 10000) return "Бело-голубая";
        else if (surfaceTemperature > 7500) return "Белая";
        else if (surfaceTemperature > 6000) return "Желто-белая";
        else if (surfaceTemperature > 5200) return "Желтая";
        else if (surfaceTemperature > 3700) return "Оранжевая";
        else return "Красная";
    }

    public boolean isMainSequenceStar() {
        return spectralClass.matches("[OBAFGKM]\\d");
    }

    // Геттеры и сеттеры для специфических полей звезды
    public String getSpectralClass() { return spectralClass; }
    public double getSurfaceTemperature() { return surfaceTemperature; }
    public double getLuminosity() { return luminosity; }
    public int getAge() { return age; }
    public boolean hasPlanetarySystem() { return hasPlanetarySystem; }

    public void setSpectralClass(String spectralClass) { this.spectralClass = spectralClass; }
    public void setSurfaceTemperature(double surfaceTemperature) { this.surfaceTemperature = surfaceTemperature; }
    public void setLuminosity(double luminosity) { this.luminosity = luminosity; }
    public void setAge(int age) { this.age = age; }
    public void setHasPlanetarySystem(boolean hasPlanetarySystem) { this.hasPlanetarySystem = hasPlanetarySystem; }
}