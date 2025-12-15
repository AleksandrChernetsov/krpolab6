import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

class UI {
    private Scanner scanner;
    private boolean exitRequested;
    private Galaxy galaxy;

    // Регулярные выражения для валидации
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^-?\\d+$");
    private static final Pattern POSITIVE_INTEGER_PATTERN = Pattern.compile("^\\d+$");
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("^-?\\d+(\\.\\d+)?$");
    private static final Pattern POSITIVE_DOUBLE_PATTERN = Pattern.compile("^\\d+(\\.\\d+)?$");
    private static final Pattern BOOLEAN_PATTERN = Pattern.compile("^(true|false|да|нет|yes|no|1|0)$", Pattern.CASE_INSENSITIVE);
    private static final Pattern YEAR_PATTERN = Pattern.compile("^-?\\d{1,5}$");

    public UI() {
        this.scanner = new Scanner(System.in);
        this.exitRequested = false;
        this.galaxy = initializeGalaxy();
        setupShutdownHook();
    }

    private void setupShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n\nПрограмма завершена. До свидания!");
            if (scanner != null) {
                scanner.close();
            }
        }));
    }

    private Galaxy initializeGalaxy() {
        Galaxy milkyWay = new Galaxy("Млечный Путь");

        // Создаем и добавляем планеты
        new Planet("Меркурий", 3.3e23, 4879, 57.9, -3000, 0, false, "землеподобная", 0.24, 58.6);
        new Planet("Венера", 4.87e24, 12104, 108.2, -3000, 0, false, "землеподобная", 0.62, 243);
        new Planet("Земля", 5.97e24, 12756, 149.6, -3000, 1, false, "землеподобная", 1.0, 1.0);
        new Planet("Марс", 6.42e23, 6792, 227.9, -3000, 2, false, "землеподобная", 1.88, 1.03);
        new Planet("Юпитер", 1.9e27, 142984, 778.3, -3000, 79, true, "газовый гигант", 11.86, 0.41);
        new Planet("Сатурн", 5.68e26, 120536, 1427, -3000, 82, true, "газовый гигант", 29.46, 0.45);
        new Planet("Уран", 8.68e25, 51118, 2871, 1781, 27, true, "ледяной гигант", 84.01, 0.72);
        new Planet("Нептун", 1.02e26, 49528, 4495, 1846, 14, true, "ледяной гигант", 164.8, 0.67);

        // Создаем и добавляем звезды
        new Star("Солнце", 1.989e30, 1392000, 0, -3000, "G2V", 5778, 1.0, 4600, true);
        new Star("Сириус", 4.176e30, 2390000, 8.6, -3000, "A1V", 9940, 25.4, 300, true);

        // Добавляем созданные тела в галактику
        for (CosmicBody body : CosmicBody.getAllCosmicBodies()) {
            if (body instanceof Planet) {
                milkyWay.addPlanet((Planet) body);
            } else if (body instanceof Star) {
                milkyWay.addStar((Star) body);
            }
        }

        return milkyWay;
    }

    public void run() {
        displayWelcomeMessage();
        displayMenu();

        while (!exitRequested) {
            try {
                String command = getCommand();

                if (exitRequested) {
                    break;
                }

                if (isMenuCommand(command)) {
                    displayMenu();
                    continue;
                }

                // Обработка числовых команд
                if (command.matches("^[1-9]$")) {
                    int choice = Integer.parseInt(command);
                    executeCommand(choice);
                } else {
                    displayMessage("Неизвестная команда. Введите 'menu' для просмотра доступных команд.");
                }

            } catch (Exception e) {
                displayMessage("Произошла ошибка: " + e.getMessage());
                displayMessage("Продолжаем работу...");
            }
        }

        displayMessage("Выход из программы. До свидания!");
        close();
    }

    private void displayWelcomeMessage() {
        displayMessage("Добро пожаловать в систему управления галактикой '" + galaxy.getName() + "'!");
    }

    private void displayMenu() {
        System.out.println("\n=== СИСТЕМА УПРАВЛЕНИЯ ГАЛАКТИКОЙ ===");
        System.out.println("1. Найти самую далекую планету от Солнца");
        System.out.println("2. Найти планеты ближе к Солнцу, чем Земля");
        System.out.println("3. Отсортировать планеты по расстоянию от Солнца");
        System.out.println("4. Найти и редактировать планету");
        System.out.println("5. Добавить новую планету");
        System.out.println("6. Показать все планеты в галактике");
        System.out.println("7. Показать все звезды в галактике");
        System.out.println("8. Показать все космические тела");
        System.out.println("9. Статистика галактики");
        System.out.println("menu - Показать меню");
        System.out.println("exit - Выход из программы");
        System.out.println("Команды: 'cancel' - отмена операции");
        System.out.println("Ctrl+C - экстренное завершение программы");
    }

    private String getCommand() {
        System.out.print("\nВведите команду: ");
        String input = scanner.nextLine().trim();

        if (isExitCommand(input)) {
            exitRequested = true;
            return "exit";
        }

        if (isMenuCommand(input)) {
            return "menu";
        }

        return input;
    }

    private void executeCommand(int choice) {
        switch (choice) {
            case 1:
                displayFurthestPlanet();
                break;
            case 2:
                displayPlanetsCloserThanEarth();
                break;
            case 3:
                displaySortedPlanets();
                break;
            case 4:
                searchAndEditPlanet();
                break;
            case 5:
                addNewPlanet();
                break;
            case 6:
                displayAllPlanets();
                break;
            case 7:
                displayAllStars();
                break;
            case 8:
                displayAllCosmicBodies();
                break;
            case 9:
                displayGalaxyStats();
                break;
        }
    }

    private void displayFurthestPlanet() {
        Planet furthest = galaxy.findFurthestPlanet();
        if (furthest != null) {
            System.out.println("\nСамая далекая планета от Солнца:");
            System.out.println(furthest.getInfo());
        } else {
            System.out.println("Планеты не найдены!");
        }
    }

    private void displayPlanetsCloserThanEarth() {
        List<Planet> planets = galaxy.findPlanetsCloserThanEarth();
        if (!planets.isEmpty()) {
            System.out.println("\nПланеты ближе к Солнцу, чем Земля:");
            for (Planet planet : planets) {
                System.out.println("  - " + planet.getName() + " (" + planet.getDistanceFromSun() + " млн км)");
            }
        } else {
            System.out.println("Планеты не найдены!");
        }
    }

    private void displaySortedPlanets() {
        List<Planet> planets = galaxy.sortPlanetsByDistance();
        if (!planets.isEmpty()) {
            System.out.println("\nПланеты, отсортированные по расстоянию от Солнца:");
            for (Planet planet : planets) {
                System.out.println("  - " + planet.getName() + ": " + planet.getDistanceFromSun() + " млн км");
            }
        } else {
            System.out.println("Планеты не найдены!");
        }
    }

    private void searchAndEditPlanet() {
        System.out.println("\n=== ПОИСК И РЕДАКТИРОВАНИЕ ПЛАНЕТЫ ===");
        System.out.println("Введите 'cancel' для отмены операции");

        String planetName = getNonEmptyInput("Введите название планеты для поиска: ");
        if (planetName == null) {
            System.out.println("Операция отменена.");
            return;
        }

        Planet planet = galaxy.findPlanetByName(planetName);
        if (planet != null) {
            System.out.println("\nНайдена планета:");
            System.out.println(planet.getInfo());

            System.out.println("\nКакое поле вы хотите изменить?");
            System.out.println("1. Количество спутников");
            System.out.println("2. Наличие колец");
            System.out.println("3. Масса");
            System.out.println("4. Диаметр");
            System.out.println("5. Расстояние от Солнца");
            System.out.println("Введите 'cancel' для отмены");

            int fieldChoice = getChoiceWithCancel(1, 5, "Выберите поле: ");
            if (fieldChoice == -1) return;

            switch (fieldChoice) {
                case 1:
                    Integer newMoons = getPositiveIntInput("Введите новое количество спутников: ");
                    if (newMoons != null) planet.setNumberOfMoons(newMoons);
                    break;
                case 2:
                    Boolean newRings = getBooleanInput("Есть кольца? (true/false/да/нет): ");
                    if (newRings != null) planet.setHasRings(newRings);
                    break;
                case 3:
                    Double newMass = getPositiveDoubleInput("Введите новую массу (кг): ");
                    if (newMass != null) planet.setMass(newMass);
                    break;
                case 4:
                    Double newDiameter = getPositiveDoubleInput("Введите новый диаметр (км): ");
                    if (newDiameter != null) planet.setDiameter(newDiameter);
                    break;
                case 5:
                    Double newDistance = getPositiveDoubleInput("Введите новое расстояние от Солнца (млн км): ");
                    if (newDistance != null) galaxy.updatePlanetDistance(planetName, newDistance);
                    break;
            }

            System.out.println("\nПланета после редактирования:");
            System.out.println(planet.getInfo());

        } else {
            System.out.println("Планета с названием '" + planetName + "' не найдена!");
        }
    }

    private void addNewPlanet() {
        System.out.println("\n=== ДОБАВЛЕНИЕ НОВОЙ ПЛАНЕТЫ ===");
        System.out.println("Введите 'cancel' для отмены операции");

        String name = getNonEmptyInput("Название: ");
        if (name == null) {
            System.out.println("Операция отменена.");
            return;
        }

        // Проверяем, нет ли уже планеты с таким именем
        if (galaxy.findPlanetByName(name) != null) {
            System.out.println("Планета с названием '" + name + "' уже существует!");
            return;
        }

        Double mass = getPositiveDoubleInput("Масса (кг): ");
        if (mass == null) return;

        Double diameter = getPositiveDoubleInput("Диаметр (км): ");
        if (diameter == null) return;

        Double distance = getPositiveDoubleInput("Расстояние от Солнца (млн км): ");
        if (distance == null) return;

        Integer year = getYearInput("Год открытия: ");
        if (year == null) return;

        Integer moons = getNonNegativeIntInput("Количество спутников: ");
        if (moons == null) return;

        Boolean rings = getBooleanInput("Есть кольца? (true/false/да/нет): ");
        if (rings == null) return;

        String type = getNonEmptyInput("Тип планеты: ");
        if (type == null) return;

        Double orbitalPeriod = getPositiveDoubleInput("Орбитальный период (лет): ");
        if (orbitalPeriod == null) return;

        Double rotationPeriod = getPositiveDoubleInput("Период вращения (суток): ");
        if (rotationPeriod == null) return;

        // Создаем новую планету
        Planet newPlanet = new Planet(name, mass, diameter, distance, year, moons, rings, type, orbitalPeriod, rotationPeriod);
        galaxy.addPlanet(newPlanet);

        System.out.println("Планета '" + name + "' успешно добавлена в галактику!");
    }

    private void displayAllPlanets() {
        List<Planet> planets = galaxy.getAllPlanets();
        if (!planets.isEmpty()) {
            System.out.println("\nВсе планеты в галактике '" + galaxy.getName() + "':");
            for (Planet planet : planets) {
                System.out.println(planet.getInfo());
                System.out.println("---");
            }
        } else {
            System.out.println("Планеты не найдены!");
        }
    }

    private void displayAllStars() {
        List<Star> stars = galaxy.getAllStars();
        if (!stars.isEmpty()) {
            System.out.println("\nВсе звезды в галактике '" + galaxy.getName() + "':");
            for (Star star : stars) {
                System.out.println(star.getInfo());
                System.out.println("---");
            }
        } else {
            System.out.println("Звезды не найдены!");
        }
    }

    private void displayAllCosmicBodies() {
        List<CosmicBody> bodies = CosmicBody.getAllCosmicBodies();
        if (!bodies.isEmpty()) {
            System.out.println("\nВсе космические тела:");
            for (CosmicBody body : bodies) {
                System.out.println(body.getInfo());
                System.out.println("---");
            }
        } else {
            System.out.println("Космические тела не найдены!");
        }
    }

    private void displayGalaxyStats() {
        System.out.println("\n=== СТАТИСТИКА ГАЛАКТИКИ '" + galaxy.getName() + "' ===");
        System.out.println("Количество планет: " + galaxy.getPlanetCount());
        System.out.println("Количество звезд: " + galaxy.getStarCount());
        System.out.println("Всего космических тел: " + CosmicBody.getAllCosmicBodies().size());

        Planet furthest = galaxy.findFurthestPlanet();
        if (furthest != null) {
            System.out.println("Самая далекая планета: " + furthest.getName() + " (" + furthest.getDistanceFromSun() + " млн км)");
        }
    }

    // Вспомогательные методы для проверки ввода с использованием регулярных выражений
    private String getInputWithCancel() {
        String input = scanner.nextLine().trim();

        if (isExitCommand(input)) {
            exitRequested = true;
            return null;
        }

        if (isCancelCommand(input)) {
            return null;
        }

        if (isMenuCommand(input)) {
            return "menu";
        }

        return input;
    }

    private String getNonEmptyInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = getInputWithCancel();
            if (input == null) return null;

            if (input.equals("menu")) {
                displayMenu();
                continue;
            }

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Ошибка: поле не может быть пустым!");
        }
    }

    private Integer getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = getInputWithCancel();
            if (input == null) return null;

            if (input.equals("menu")) {
                displayMenu();
                continue;
            }

            if (INTEGER_PATTERN.matcher(input).matches()) {
                return Integer.parseInt(input);
            }

            System.out.println("Ошибка: введите целое число!");
        }
    }

    private Integer getYearInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = getInputWithCancel();
            if (input == null) return null;

            if (input.equals("menu")) {
                displayMenu();
                continue;
            }

            if (YEAR_PATTERN.matcher(input).matches()) {
                int year = Integer.parseInt(input);
                if (year >= -99999 && year <= 99999) {
                    return year;
                }
            }

            System.out.println("Ошибка: введите корректный год (от -99999 до 99999)!");
        }
    }

    private Integer getNonNegativeIntInput(String prompt) {
        while (true) {
            Integer value = getIntInput(prompt);
            if (value == null) return null;

            if (value >= 0) {
                return value;
            }

            System.out.println("Ошибка: число не может быть отрицательным!");
        }
    }

    private Integer getPositiveIntInput(String prompt) {
        while (true) {
            Integer value = getIntInput(prompt);
            if (value == null) return null;

            if (value > 0) {
                return value;
            }

            System.out.println("Ошибка: число должно быть положительным!");
        }
    }

    private Double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = getInputWithCancel();
            if (input == null) return null;

            if (input.equals("menu")) {
                displayMenu();
                continue;
            }

            if (DOUBLE_PATTERN.matcher(input).matches()) {
                return Double.parseDouble(input);
            }

            System.out.println("Ошибка: введите число!");
        }
    }

    private Double getPositiveDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = getInputWithCancel();
            if (input == null) return null;

            if (input.equals("menu")) {
                displayMenu();
                continue;
            }

            if (POSITIVE_DOUBLE_PATTERN.matcher(input).matches()) {
                double value = Double.parseDouble(input);
                if (value > 0) {
                    return value;
                }
            }

            System.out.println("Ошибка: введите положительное число!");
        }
    }

    private Boolean getBooleanInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = getInputWithCancel();
            if (input == null) return null;

            if (input.equals("menu")) {
                displayMenu();
                continue;
            }

            if (BOOLEAN_PATTERN.matcher(input).matches()) {
                return input.equalsIgnoreCase("true") ||
                        input.equalsIgnoreCase("да") ||
                        input.equalsIgnoreCase("yes") ||
                        input.equals("1");
            }

            System.out.println("Ошибка: введите 'true/false', 'да/нет' или 'yes/no'!");
        }
    }

    private int getChoiceWithCancel(int min, int max, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = getInputWithCancel();
            if (input == null) return -1;

            if (input.equals("menu")) {
                displayMenu();
                continue;
            }

            if (input.matches("^\\d+$")) {
                int choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) {
                    return choice;
                }
            }

            System.out.printf("Ошибка: введите число от %d до %d!%n", min, max);
        }
    }

    private boolean isCancelCommand(String input) {
        return "cancel".equalsIgnoreCase(input) || "отмена".equalsIgnoreCase(input);
    }

    private boolean isExitCommand(String input) {
        return "exit".equalsIgnoreCase(input) || "выход".equalsIgnoreCase(input) || "0".equals(input);
    }

    private boolean isMenuCommand(String input) {
        return "menu".equalsIgnoreCase(input) || "меню".equalsIgnoreCase(input) || "help".equalsIgnoreCase(input) || "?".equals(input);
    }

    private void close() {
        if (scanner != null) {
            scanner.close();
        }
    }

    private void displayMessage(String message) {
        System.out.println(message);
    }
}