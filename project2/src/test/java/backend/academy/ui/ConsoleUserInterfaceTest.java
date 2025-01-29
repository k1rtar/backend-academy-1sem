package backend.academy.ui;

import backend.academy.generator.Generator;
import backend.academy.generator.PrimMazeGenerator;
import backend.academy.model.Coordinate;
import backend.academy.model.Maze;
import backend.academy.renderer.ConsoleRenderer;
import backend.academy.renderer.Renderer;
import backend.academy.solver.BFSSolver;
import backend.academy.solver.Solver;
import org.junit.jupiter.api.*;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsoleUserInterfaceTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream output;

    @BeforeEach
    void setUp() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("run() при валидном вводе с BFS")
    void runValidInputWithBFS() {
        String input = String.join(System.lineSeparator(),
            "5", "5",    // размеры
            "1",         // выбор генератора (Prim)
            "0", "0",    // start (0,0)
            "4", "4",    // end (4,4)
            "1",         // BFS
            "no"         // выход
        );
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Maze mockMaze = mock(Maze.class);
        when(mockMaze.getHeight()).thenReturn(5);
        when(mockMaze.getWidth()).thenReturn(5);

        when(mockMaze.getEdges()).thenReturn(java.util.Set.of());

        Renderer mockRenderer = mock(Renderer.class);
        when(mockRenderer.render(any(Maze.class))).thenReturn("Mocked Maze");
        when(mockRenderer.render(any(Maze.class), anyList(), any(), any())).thenReturn("Mocked Maze with Path");

        List<Coordinate> mockPath = List.of(new Coordinate(0,0), new Coordinate(4,4));

        Solver mockSolver = mock(Solver.class);
        when(mockSolver.solve(eq(mockMaze), eq(new Coordinate(0,0)), eq(new Coordinate(4,4)))).thenReturn(mockPath);

        Generator mockGenerator = mock(Generator.class);
        when(mockGenerator.generate(5,5)).thenReturn(mockMaze);

        try (MockedConstruction<PrimMazeGenerator> genConstruction = Mockito.mockConstruction(PrimMazeGenerator.class,
            (gen, context)->when(gen.generate(5,5)).thenReturn(mockMaze));
             MockedConstruction<ConsoleRenderer> renderConstruction = Mockito.mockConstruction(ConsoleRenderer.class,
                 (r,context)->{
                     when(r.render(mockMaze)).thenReturn("Mocked Maze");
                     when(r.render(mockMaze,mockPath,new Coordinate(0,0),new Coordinate(4,4))).thenReturn("Mocked Maze with Path");
                 });
             MockedConstruction<BFSSolver> solverConstruction = Mockito.mockConstruction(BFSSolver.class,
                 (s,context)->when(s.solve(mockMaze,new Coordinate(0,0),new Coordinate(4,4))).thenReturn(mockPath));
        ) {
            ConsoleUserInterface ui = new ConsoleUserInterface();
            ui.run();
        }

        String out = output.toString();
        assertThat(out).contains("Сгенерированный лабиринт:")
            .contains("Mocked Maze")
            .contains("Путь найден")
            .contains("Mocked Maze with Path");
    }
}
