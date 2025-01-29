package backend.academy.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс, представляющий ячейку лабиринта.
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"row", "col"})
public class Cell {
    private int row;
    private int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
