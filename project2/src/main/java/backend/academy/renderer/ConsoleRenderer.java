package backend.academy.renderer;

import backend.academy.model.Cell;
import backend.academy.model.Coordinate;
import backend.academy.model.Maze;
import java.util.List;

/**
 * Класс для отображения лабиринта в консоли.
 */
public class ConsoleRenderer implements Renderer {

    private static final String HORIZONTAL = "───";
    private static final String VERTICAL = "│";
    private static final String EMPTY_CELL = "   ";

    @Override
    public String render(Maze maze) {
        return render(maze, null, null, null);
    }

    @Override
    public String render(Maze maze, List<Coordinate> path, Coordinate start, Coordinate end) {
        StringBuilder sb = new StringBuilder();
        int height = maze.getHeight();
        int width = maze.getWidth();
        Cell[][] grid = maze.getGrid();

        appendTopBorder(sb, width);

        for (int row = 0; row < height; row++) {
            appendRow(sb, maze, row, width, path, start, end);
            if (row < height - 1) {
                appendMiddleBorder(sb, maze, row, width);
            }
        }

        // Нижняя граница
        appendBottomBorder(sb, width);

        return sb.toString();
    }

    private void appendTopBorder(StringBuilder sb, int width) {
        sb.append("┌");
        for (int col = 0; col < width; col++) {
            sb.append(HORIZONTAL);
            if (col == width - 1) {
                sb.append("┐");
            } else {
                sb.append("┬");
            }
        }
        sb.append("\n");
    }

    private void appendRow(StringBuilder sb, Maze maze, int row, int width,
        List<Coordinate> path, Coordinate start, Coordinate end) {
        sb.append(VERTICAL);
        for (int col = 0; col < width; col++) {
            Cell cell = maze.getCell(row, col);
            String cellContent = EMPTY_CELL;

            if (start != null && start.getRow() == row && start.getCol() == col) {
                cellContent = " A ";
            } else if (end != null && end.getRow() == row && end.getCol() == col) {
                cellContent = " B ";
            } else if (path != null && path.contains(new Coordinate(row, col))) {
                cellContent = " * ";
            }

            sb.append(cellContent);


            boolean hasEastWall = true;
            for (Maze.Edge edge : maze.getEdges()) {
                if (edge.getCell1().equals(cell) && edge.getCell2().getCol() == col + 1) {
                    hasEastWall = false;
                    break;
                }
                if (edge.getCell2().equals(cell) && edge.getCell1().getCol() == col + 1) {
                    hasEastWall = false;
                    break;
                }
            }

            if (hasEastWall) {
                sb.append(VERTICAL);
            } else {
                sb.append(" ");
            }
        }
        sb.append("\n");
    }

    private void appendMiddleBorder(StringBuilder sb, Maze maze, int row, int width) {
        sb.append("├");
        for (int col = 0; col < width; col++) {
            Cell cell = maze.getCell(row, col);
            boolean hasSouthWall = true;
            for (Maze.Edge edge : maze.getEdges()) {
                if (edge.getCell1().equals(cell) && edge.getCell2().getRow() == row + 1) {
                    hasSouthWall = false;
                    break;
                }
                if (edge.getCell2().equals(cell) && edge.getCell1().getRow() == row + 1) {
                    hasSouthWall = false;
                    break;
                }
            }

            if (hasSouthWall) {
                sb.append(HORIZONTAL);
            } else {
                sb.append(EMPTY_CELL);
            }

            if (col == width - 1) {
                sb.append("┤");
            } else {
                sb.append("┼");
            }
        }
        sb.append("\n");
    }

    private void appendBottomBorder(StringBuilder sb, int width) {
        sb.append("└");
        for (int col = 0; col < width; col++) {
            sb.append(HORIZONTAL);
            if (col == width - 1) {
                sb.append("┘");
            } else {
                sb.append("┴");
            }
        }
        sb.append("\n");
    }
}
