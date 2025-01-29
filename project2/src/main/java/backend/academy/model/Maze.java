package backend.academy.model;


import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

/**
 * Класс, представляющий лабиринт.
 * Содержит структуру для хранения связей между ячейками.
 */
@Getter
public class Maze {
    private final int height;
    private final int width;
    private final Cell[][] grid;
    private final Set<Edge> edges;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new Cell[height][width];
        this.edges = new HashSet<>();


        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Cell(row, col);
            }
        }
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    /**
     * Внутренний класс для представления рёбер между ячейками.
     */
    public static class Edge {
        private final Cell cell1;
        private final Cell cell2;

        public Edge(Cell cell1, Cell cell2) {
            this.cell1 = cell1;
            this.cell2 = cell2;
        }

        public Cell getCell1() {
            return cell1;
        }

        public Cell getCell2() {
            return cell2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Edge)) {
                return false;
            }
            Edge edge = (Edge) o;
            return (cell1.equals(edge.cell1) && cell2.equals(edge.cell2))
                || (cell1.equals(edge.cell2) && cell2.equals(edge.cell1));
        }

        @Override
        public int hashCode() {
            return cell1.hashCode() + cell2.hashCode();
        }
    }
}
