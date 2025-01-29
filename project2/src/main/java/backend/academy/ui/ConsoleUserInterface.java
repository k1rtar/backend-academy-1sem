package backend.academy.ui;

import backend.academy.generator.Generator;
import backend.academy.generator.KruskalMazeGenerator;
import backend.academy.generator.PrimMazeGenerator;
import backend.academy.model.Coordinate;
import backend.academy.model.Maze;
import backend.academy.renderer.ConsoleRenderer;
import backend.academy.renderer.Renderer;
import backend.academy.solver.AStarSolver;
import backend.academy.solver.BFSSolver;
import backend.academy.solver.Solver;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс для консольного взаимодействия с пользователем.
 */
@SuppressWarnings("checkstyle:RegexpSinglelineJava")
public class ConsoleUserInterface implements UserInterface {

    private static final Logger LOGGER = LogManager.getLogger(ConsoleUserInterface.class);

    @Override
    public void run() {
        final int MIN_SIZE = 1;
        final int MAX_SIZE = 50;

        boolean continueProgram = true;
        try (Scanner scanner = new Scanner(System.in)) {
            while (continueProgram) {
                try {
                    // Ввод размеров лабиринта с проверкой
                    int width = readInt(scanner, String.format(
                        Messages.ENTER_MAZE_WIDTH, MIN_SIZE, MAX_SIZE), MIN_SIZE, MAX_SIZE);
                    int height = readInt(scanner, String.format(
                        Messages.ENTER_MAZE_HEIGHT, MIN_SIZE, MAX_SIZE), MIN_SIZE, MAX_SIZE);

                    // Выбор алгоритма генерации
                    System.out.println(Messages.SELECT_GENERATOR);
                    System.out.println(Messages.GENERATOR_OPTION_PRIM);
                    System.out.println(Messages.GENERATOR_OPTION_KRUSKAL);
                    int genChoice = readInt(scanner, Messages.YOUR_CHOICE, 1, 2);

                    Generator generator;
                    if (genChoice == 1) {
                        generator = new PrimMazeGenerator();
                    } else {
                        generator = new KruskalMazeGenerator();
                    }

                    // Генерация лабиринта
                    Maze maze = generator.generate(height, width);

                    // Отображение сгенерированного лабиринта без пути и точек
                    Renderer renderer = new ConsoleRenderer();
                    System.out.println("Сгенерированный лабиринт:");
                    System.out.println(renderer.render(maze));

                    // Ввод начальной и конечной точек с проверкой
                    Coordinate start;
                    Coordinate end;

                    while (true) {
                        System.out.println("Координаты начинаются с 0 и должны быть в пределах лабиринта.");
                        int startRow = readInt(scanner, Messages.ENTER_START_ROW, 0, height - 1);
                        int startCol = readInt(scanner, Messages.ENTER_START_COL, 0, width - 1);
                        start = new Coordinate(startRow, startCol);

                        if (isValidCoordinate(start, maze)) {
                            break;
                        } else {
                            System.out.println(Messages.INVALID_START);
                        }
                    }

                    while (true) {
                        int endRow = readInt(scanner, Messages.ENTER_END_ROW, 0, height - 1);
                        int endCol = readInt(scanner, Messages.ENTER_END_COL, 0, width - 1);
                        end = new Coordinate(endRow, endCol);

                        if (isValidCoordinate(end, maze)) {
                            break;
                        } else {
                            System.out.println(Messages.INVALID_END);
                        }
                    }

                    // Выбор алгоритма решения
                    System.out.println(Messages.SELECT_SOLVER);
                    System.out.println(Messages.SOLVER_OPTION_BFS);
                    System.out.println(Messages.SOLVER_OPTION_ASTAR);
                    int solveChoice = readInt(scanner, Messages.YOUR_CHOICE, 1, 2);

                    Solver solver;
                    if (solveChoice == 1) {
                        solver = new BFSSolver();
                    } else {
                        solver = new AStarSolver();
                    }

                    // Решение лабиринта
                    List<Coordinate> path = solver.solve(maze, start, end);

                    String message = (path != null) ? Messages.PATH_FOUND : Messages.PATH_NOT_FOUND;
                    System.out.println(message);
                    System.out.println(renderer.render(maze, path, start, end));

                    // Спросить пользователя, хочет ли он продолжить
                    String response = readLine(scanner, Messages.CONTINUE_PROMPT).trim().toLowerCase();
                    if (!response.equals("да") && !response.equals("yes")) {
                        continueProgram = false;
                        System.out.println(Messages.EXIT_MESSAGE);
                    }
                } catch (UserInterruptException e) {
                    System.out.println(e.getMessage());
                    System.out.println(Messages.EXIT_MESSAGE);
                    continueProgram = false;
                } catch (Exception e) {
                    LOGGER.error(
                        "Произошла ошибка: " + e.getMessage(), e
                    );
                    continueProgram = false;
                }
            }
        }
    }

    /**
     * Метод для безопасного чтения целого числа из консоли с проверкой диапазона.
     *
     * @param scanner Scanner для чтения ввода
     * @param prompt сообщение для пользователя
     * @param min    минимальное допустимое значение (включительно)
     * @param max    максимальное допустимое значение (включительно)
     * @return введенное пользователем целое число
     * @throws UserInterruptException если ввод был прерван
     */
    private int readInt(Scanner scanner, String prompt, int min, int max) throws UserInterruptException {
        while (true) {
            try {
                String line = readLine(scanner, prompt);
                int value = Integer.parseInt(line.trim());
                if (value < min || value > max) {
                    System.out.println(String.format(Messages.INPUT_RANGE_ERROR, min, max));
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println(Messages.INPUT_ERROR);
            }
        }
    }

    /**
     * Метод для безопасного чтения строки из консоли.
     *
     * @param scanner Scanner для чтения ввода
     * @param prompt сообщение для пользователя
     * @return введенная пользователем строка
     * @throws UserInterruptException если ввод был прерван
     */
    private String readLine(Scanner scanner, String prompt) throws UserInterruptException {
        System.out.print(prompt);
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        } else {
            throw new UserInterruptException("Ввод был прерван пользователем.");
        }
    }

    /**
     * Проверяет, является ли координата допустимой (не выходит за границы лабиринта).
     *
     * @param coord координата для проверки
     * @param maze  лабиринт
     * @return true, если координата допустима
     */
    private boolean isValidCoordinate(Coordinate coord, Maze maze) {
        int row = coord.getRow();
        int col = coord.getCol();
        return row >= 0 && row < maze.getHeight() && col >= 0 && col < maze.getWidth();
    }
}
