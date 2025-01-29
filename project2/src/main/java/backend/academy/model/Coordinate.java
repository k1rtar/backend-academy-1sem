package backend.academy.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Класс, представляющий координаты ячейки в лабиринте.
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Coordinate {
    private final int row;
    private final int col;
}
