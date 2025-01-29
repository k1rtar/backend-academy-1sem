package backend.academy.service.converter;

import backend.academy.domain.Student;

/**
 * Прямой доступ (никакой рефлексии и т.п.).
 */
public class DirectJsonConverter implements JsonMapper {
    @Override
    public String convert(Object o) {
        if (!(o instanceof Student student)) {
            throw new IllegalArgumentException("Unsupported type: " + o.getClass());
        }
        return "{\"name\":\"" + student.name() + "\",\"surname\":\"" + student.surname() + "\"}";
    }
}
