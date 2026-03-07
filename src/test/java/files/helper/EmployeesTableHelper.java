package files.helper;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class EmployeesTableHelper {

    public static void validateTable(List<String[]> lines) {

        assertSoftly(
                softly -> {
                    softly.assertThat(lines.size())
                            .as("Файл должен содержать заголовок и хотя бы одну строку данных")
                            .isGreaterThan(1);

                    String[] header = lines.getFirst();

                    softly.assertThat(header)
                            .as("Заголовки колонок")
                            .containsExactly("ID", "Name", "Age", "Salary");

                    List<String[]> dataRows = lines.subList(1, lines.size());

                    Set<Integer> ids = new HashSet<>();
                    Pattern namePattern = Pattern.compile("^[a-zA-Z]+ [a-zA-Z]+$");

                    for (String[] row : dataRows) {

                        int id = Integer.parseInt(row[0].trim());
                        softly.assertThat(ids.add(id)).as("Дубликат ID: " + id).isTrue();

                        String name = row[1].trim();
                        softly.assertThat(namePattern.matcher(name).matches())
                                .as("Имя должно быть из двух слов: " + name)
                                .isTrue();

                        int age = Integer.parseInt(row[2].trim());
                        softly.assertThat(age)
                                .as("Возраст вне диапазона: " + age)
                                .isBetween(18, 70);

                        double salary = Double.parseDouble(row[3].trim());
                        softly.assertThat(salary)
                                .as("Зарплата вне диапазона: " + salary)
                                .isBetween(20000.0, 200000.0);
                    }
                });
    }
}
