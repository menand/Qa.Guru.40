package files.helper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Assertions;

public class EmployeesTableHelper {

    public static void validateTable(List<String[]> lines) {
        Assertions.assertTrue(
                lines.size() > 1, "Файл должен содержать заголовок и хотя бы одну строку данных");

        String[] header = lines.getFirst();
        Assertions.assertArrayEquals(
                new String[] {"ID", "Name", "Age", "Salary"},
                header,
                "Заголовки колонок не соответствуют ожидаемым");

        List<String[]> dataRows = lines.subList(1, lines.size());

        Set<Integer> ids = new HashSet<>();
        Pattern namePattern = Pattern.compile("^[a-zA-Z]+ [a-zA-Z]+$");

        for (String[] row : dataRows) {

            // ID
            String idStr = row[0].trim();
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                Assertions.fail("ID не является целым числом: " + idStr);
                return;
            }

            Assertions.assertTrue(ids.add(id), "Обнаружен дубликат ID: " + id);

            // Name
            String name = row[1].trim();
            Assertions.assertTrue(
                    namePattern.matcher(name).matches(),
                    "Имя должно состоять из двух слов: " + name);

            // Age
            int age = Integer.parseInt(row[2].trim());
            Assertions.assertTrue(
                    age >= 18 && age <= 70, "Возраст должен быть от 18 до 70: " + age);

            // Salary
            double salary = Double.parseDouble(row[3].trim());
            Assertions.assertTrue(
                    salary >= 20000 && salary <= 200000,
                    "Зарплата должна быть от 20000 до 200000: " + salary);
        }
    }
}
