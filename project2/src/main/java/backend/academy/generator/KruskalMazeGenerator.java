package backend.academy.generator;

import backend.academy.model.Cell;
import backend.academy.model.Maze;
import backend.academy.model.Maze.Edge;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Реализация генератора лабиринтов с использованием алгоритма Краскала.
 */
public class KruskalMazeGenerator extends AbstractMazeGenerator {

    @Override
    public Maze generate(int height, int width) {
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Height and width must be greater than 0");
        }

        Maze maze = new Maze(height, width);
        Cell[][] grid = maze.getGrid();

        initializeGrid(maze);


        Map<Cell, Set<Cell>> sets = new HashMap<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell cell = grid[row][col];
                Set<Cell> set = new HashSet<>();
                set.add(cell);
                sets.put(cell, set);
            }
        }


        List<Edge> walls = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell cell = grid[row][col];
                if (col < width - 1) {
                    Cell rightNeighbor = grid[row][col + 1];
                    walls.add(new Edge(cell, rightNeighbor));
                }
                if (row < height - 1) {
                    Cell bottomNeighbor = grid[row + 1][col];
                    walls.add(new Edge(cell, bottomNeighbor));
                }
            }
        }

        Collections.shuffle(walls);

        for (Maze.Edge wall : walls) {
            Cell cell1 = wall.getCell1();
            Cell cell2 = wall.getCell2();

            Set<Cell> set1 = sets.get(cell1);
            Set<Cell> set2 = sets.get(cell2);

            if (!set1.equals(set2)) {
                maze.getEdges().add(wall);
                set1.addAll(set2);
                for (Cell cell : set2) {
                    sets.put(cell, set1);
                }
            }
        }

        return maze; // Возврат сгенерированного лабиринта
    }
}
