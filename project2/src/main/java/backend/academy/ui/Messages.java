package backend.academy.ui;

/**
 * Класс для хранения строковых констант приложения.
 */
public final class Messages {
    private Messages() {
        // Приватный конструктор, чтобы предотвратить создание экземпляров
    }

    public static final String ENTER_MAZE_WIDTH = "Введите ширину лабиринта (от %d до %d): ";
    public static final String ENTER_MAZE_HEIGHT = "Введите высоту лабиринта (от %d до %d): ";
    public static final String SELECT_GENERATOR = "Выберите алгоритм генерации лабиринта:";
    public static final String GENERATOR_OPTION_PRIM = "1. Алгоритм Прима";
    public static final String GENERATOR_OPTION_KRUSKAL = "2. Алгоритм Краскала";
    public static final String YOUR_CHOICE = "Ваш выбор: ";
    public static final String ENTER_START_ROW = "Введите начальную строку: ";
    public static final String ENTER_START_COL = "Введите начальный столбец: ";
    public static final String ENTER_END_ROW = "Введите конечную строку: ";
    public static final String ENTER_END_COL = "Введите конечный столбец: ";
    public static final String SELECT_SOLVER = "Выберите алгоритм решения лабиринта:";
    public static final String SOLVER_OPTION_BFS = "1. Поиск в ширину (BFS)";
    public static final String SOLVER_OPTION_ASTAR = "2. A* (A-star)";
    public static final String PATH_FOUND = "Путь найден:";
    public static final String PATH_NOT_FOUND = "Путь не найден.";
    public static final String INPUT_ERROR = "Ошибка ввода. Пожалуйста, введите целое число.";
    public static final String INVALID_START = "Недопустимая начальная точка. Попробуйте снова.";
    public static final String INVALID_END = "Недопустимая конечная точка. Попробуйте снова.";
    public static final String CONTINUE_PROMPT = "Хотите сгенерировать новый лабиринт? (да/нет): ";
    public static final String EXIT_MESSAGE = "Спасибо за использование программы!";
    public static final String INPUT_RANGE_ERROR = "Пожалуйста, введите число от %d до %d.";
}
