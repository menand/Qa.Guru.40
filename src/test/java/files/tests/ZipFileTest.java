package files.tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;

class ZipFileTest {
    private final ClassLoader cl = getClass().getClassLoader();
    private final String ZIPFILENAME = "files/zip/FilesExample.zip";

    @Test
    void zipFileTest() throws Exception {
        try (InputStream in = cl.getResourceAsStream(ZIPFILENAME)) {
            assertNotNull(in, "ZIP-файл не найден: " + ZIPFILENAME);
            ZipInputStream zis = new ZipInputStream(in);
            int fileCount = 0;
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) fileCount++;
            assertTrue(fileCount>2,"В файле меньше 3х файлов");
        }
    }

    @Test
    void checkExcelTest() throws IOException {
        try (ZipInputStream zis = new ZipInputStream(
                Objects.requireNonNull(cl.getResourceAsStream(ZIPFILENAME))
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".xls") || entry.getName().endsWith(".xlsx")&&!entry.getName().startsWith("__")) {
                    XLS xls = new XLS(zis);
                    Assertions.assertTrue(xls.excel.getNumberOfSheets() > 0,
                            "Excel файл не содержит ни одного листа");
                    String cellValue = xls.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
                    Assertions.assertNotNull(cellValue, "Первая ячейка равна null");
                    Assertions.assertFalse(cellValue.isEmpty(), "Первая ячейка пустая");
                    Assertions.assertEquals("Title", cellValue);
                }
            }
        }
    }

    @Test
    void checkPdfTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                Objects.requireNonNull(cl.getResourceAsStream(ZIPFILENAME))
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".pdf")&&!entry.getName().startsWith("__")) {
                    PDF pdf = new PDF(zis);
                    assertTrue(pdf.text.contains("CO-BRANDING AAO-HNSF PATIENT HANDOUTS IS AS EASY AS 1-2-3!"),
                            "PDF файл не содержит требуемый текст");
                    assertEquals(1, pdf.numberOfPages);
                }
            }
        }
    }

    @Test
    void checkCsvTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                Objects.requireNonNull(cl.getResourceAsStream(ZIPFILENAME))
        )) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".csv")&&!entry.getName().startsWith("__")) {
                    CSVReader reader = new CSVReader(new InputStreamReader(zis, StandardCharsets.UTF_8));
                    List<String[]> lines = reader.readAll();
                    Assertions.assertTrue(lines.size() > 1,
                            "Файл должен содержать заголовок и хотя бы одну строку данных");

                    // Проверка заголовков колонок
                    String[] header = lines.get(0);
                    Assertions.assertArrayEquals(
                            new String[]{"ID", "Name", "Age", "Salary"},
                            header,
                            "Заголовки колонок не соответствуют ожидаемым"
                    );

                    // Данные начинаются со второй строки (индекс 1)
                    List<String[]> dataRows = lines.subList(1, lines.size());
                    // Множество для проверки уникальности ID
                    Set<Integer> ids = new HashSet<>();
                    // Регулярное выражение для имени: два слова из букв, разделённых пробелом
                    Pattern namePattern = Pattern.compile("^[a-zA-Z]+ [a-zA-Z]+$");
                    for (String[] row : dataRows) {
                        // --- Проверка ID (уникальность и целое число) ---
                        String idStr = row[0].trim();
                        int id;
                        try {
                            id = Integer.parseInt(idStr);
                        } catch (NumberFormatException e) {
                            Assertions.fail("ID не является целым числом: " + idStr);
                            return;
                        }
                        Assertions.assertTrue(ids.add(id), "Обнаружен дубликат ID: " + id);

                        // --- Проверка Name (два слова, только буквы) ---
                        String name = row[1].trim();
                        Assertions.assertTrue(namePattern.matcher(name).matches(),
                                "Имя должно состоять из двух слов, разделённых пробелом, только буквы: " + name);

                        // --- Проверка Age (целое число от 18 до 70) ---
                        String ageStr = row[2].trim();
                        int age;
                        try {
                            age = Integer.parseInt(ageStr);
                        } catch (NumberFormatException e) {
                            Assertions.fail("Age не является целым числом: " + ageStr);
                            return;
                        }
                        Assertions.assertTrue(age >= 18 && age <= 70,
                                "Возраст должен быть от 18 до 70, найдено: " + age);

                        // --- Проверка Salary (число с плавающей точкой от 20000 до 200000) ---
                        String salaryStr = row[3].trim();
                        double salary;
                        try {
                            salary = Double.parseDouble(salaryStr);
                        } catch (NumberFormatException e) {
                            Assertions.fail("Salary не является числом: " + salaryStr);
                            return;
                        }
                        Assertions.assertTrue(salary >= 20000.0 && salary <= 200000.0,
                                "Зарплата должна быть от 20000 до 200000, найдено: " + salary);
                    }
                }
            }
        }
    }
}