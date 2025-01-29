package backend.academy.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    @DisplayName("Конструктор должен корректно инициализировать строку и столбец")
    void constructor_shouldInitializeCoordinatesCorrectly() {
        // Arrange
        int expectedRow = 2;
        int expectedCol = 3;

        // Act
        Coordinate coord = new Coordinate(expectedRow, expectedCol);

        // Assert
        assertAll("Инициализация координаты",
            () -> assertEquals(expectedRow, coord.getRow(), "Строка должна соответствовать заданному значению"),
            () -> assertEquals(expectedCol, coord.getCol(), "Столбец должен соответствовать заданному значению")
        );
    }

    @Test
    @DisplayName("Координаты с одинаковыми значениями должны быть равны и иметь одинаковый hashCode")
    void equalsAndHashCode_shouldRespectCoordinateValues() {
        // Arrange
        Coordinate coord1 = new Coordinate(1, 1);
        Coordinate coord2 = new Coordinate(1, 1);
        Coordinate coord3 = new Coordinate(2, 2);

        // Act & Assert
        assertAll("Проверка равенства и hashCode",
            () -> assertEquals(coord1, coord2, "Координаты с одинаковыми значениями должны быть равны"),
            () -> assertNotEquals(coord1, coord3, "Координаты с разными значениями не должны быть равны"),
            () -> assertEquals(coord1.hashCode(), coord2.hashCode(), "hashCode должен быть одинаковым для равных координат"),
            () -> assertNotEquals(coord1.hashCode(), coord3.hashCode(), "hashCode должен отличаться для разных координат")
        );
    }
}
