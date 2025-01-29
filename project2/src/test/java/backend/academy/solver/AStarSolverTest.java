package backend.academy.solver;

import backend.academy.model.Cell;
import backend.academy.model.Coordinate;
import backend.academy.model.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AStarSolverTest {

    private AStarSolver solver;
    private Maze maze;

    @BeforeEach
    void setUp() {
        solver = new AStarSolver();
        maze = mock(Maze.class);

        when(maze.getHeight()).thenReturn(2);
        when(maze.getWidth()).thenReturn(2);

        Cell[][] grid = new Cell[2][2];
        grid[0][0] = new Cell(0,0);
        grid[0][1] = new Cell(0,1);
        grid[1][0] = new Cell(1,0);
        grid[1][1] = new Cell(1,1);

        when(maze.getGrid()).thenReturn(grid);
        for(int r=0;r<2;r++){
            for(int c=0;c<2;c++){
                when(maze.getCell(r,c)).thenReturn(grid[r][c]);
            }
        }

        // Соединяем все клетки, чтобы путь точно был.
        when(maze.getEdges()).thenReturn(Set.of(
            new Maze.Edge(grid[0][0], grid[0][1]),
            new Maze.Edge(grid[0][1], grid[1][1]),
            new Maze.Edge(grid[0][0], grid[1][0]),
            new Maze.Edge(grid[1][0], grid[1][1])
        ));
    }

    @Test
    @DisplayName("solve() должен найти путь в полностью связном маленьком лабиринте")
    void solveShouldFindPath() {
        // Arrange
        Coordinate start = new Coordinate(0,0);
        Coordinate end = new Coordinate(1,1);

        // Act
        var path = solver.solve(maze, start, end);

        // Assert
        assertThat(path).isNotNull().isNotEmpty();
        assertThat(path.get(0)).isEqualTo(start);
        assertThat(path.get(path.size()-1)).isEqualTo(end);
    }

    @Test
    @DisplayName("solve() должен вернуть null, если нет рёбер и путь невозможен")
    void solveShouldReturnNullIfNoPath() {
        // Arrange
        when(maze.getEdges()).thenReturn(Set.of()); // нет путей
        Coordinate start = new Coordinate(0,0);
        Coordinate end = new Coordinate(1,1);

        // Act
        var path = solver.solve(maze, start, end);

        // Assert
        assertThat(path).isNull();
    }
}
