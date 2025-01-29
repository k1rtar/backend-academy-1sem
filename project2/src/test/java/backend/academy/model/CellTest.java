package backend.academy.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    @DisplayName("Default конструктор должен инициализировать строку и столбец нулями")
    void defaultConstructor_shouldInitializeCoordinatesToZero() {
        // Act
        Cell cell = new Cell();

        // Assert
        assertAll("Инициализация по умолчанию",
            () -> assertEquals(0, cell.getRow(), "Строка должна быть 0"),
            () -> assertEquals(0, cell.getCol(), "Столбец должен быть 0")
        );
    }

    @Test
    @DisplayName("Параметризованный конструктор должен корректно устанавливать строку и столбец")
    void parameterizedConstructor_shouldSetCoordinatesCorrectly() {
        // Arrange
        int expectedRow = 2;
        int expectedCol = 3;

        // Act
        Cell cell = new Cell(expectedRow, expectedCol);

        // Assert
        assertAll("Параметризованная инициализация",
            () -> assertEquals(expectedRow, cell.getRow(), "Строка должна соответствовать заданному значению"),
            () -> assertEquals(expectedCol, cell.getCol(), "Столбец должен соответствовать заданному значению")
        );
    }

    @Test
    @DisplayName("Сеттеры должны корректно обновлять строку и столбец")
    void setters_shouldUpdateCoordinatesCorrectly() {
        // Arrange
        Cell cell = new Cell();

        // Act
        cell.setRow(5);
        cell.setCol(7);

        // Assert
        assertAll("Обновление координат через сеттеры",
            () -> assertEquals(5, cell.getRow(), "Строка должна быть обновлена до 5"),
            () -> assertEquals(7, cell.getCol(), "Столбец должен быть обновлен до 7")
        );
    }

    @Test
    @DisplayName("Клетки с одинаковыми координатами должны быть равны и иметь одинаковый hashCode")
    void equalsAndHashCode_shouldRespectCoordinates() {
        // Arrange
        Cell cell1 = new Cell(1, 2);
        Cell cell2 = new Cell(1, 2);
        Cell cell3 = new Cell(2, 3);

        // Act & Assert
        assertAll("Проверка равенства и hashCode",
            () -> assertEquals(cell1, cell2, "Клетки с одинаковыми координатами должны быть равны"),
            () -> assertNotEquals(cell1, cell3, "Клетки с разными координатами не должны быть равны"),
            () -> assertEquals(cell1.hashCode(), cell2.hashCode(), "hashCode должен быть одинаковым для равных клеток"),
            () -> assertNotEquals(cell1.hashCode(), cell3.hashCode(), "hashCode должен отличаться для разных клеток")
        );
    }
}
