package files.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.codeborne.pdftest.PDF;
import com.opencsv.CSVReader;
import files.helper.EmployeesTableHelper;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("REGRESS")
@Tag("FILES")
@Tag("ZIP")
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
            while ((entry = zis.getNextEntry()) != null)
                if (!entry.getName().startsWith("__")) fileCount++;
            assertTrue(fileCount > 2, "В файле меньше 3х файлов");
        }
    }

    @Test
    void checkExcelTest() throws Exception {
        try (ZipInputStream zis =
                new ZipInputStream(Objects.requireNonNull(cl.getResourceAsStream(ZIPFILENAME)))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if ((entry.getName().endsWith(".xls") || entry.getName().endsWith(".xlsx"))
                        && !entry.getName().startsWith("__")) {
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    zis.transferTo(buffer);
                    try (Workbook workbook =
                            WorkbookFactory.create(
                                    new ByteArrayInputStream(buffer.toByteArray()))) {
                        Sheet sheet = workbook.getSheetAt(0);
                        List<String[]> lines = new ArrayList<>();
                        DataFormatter formatter = new DataFormatter();
                        for (Row row : sheet) {
                            if (row.getRowNum() == 0) {
                                continue;
                            }
                            String[] cells = new String[4];
                            for (int i = 0; i < 4; i++) {
                                Cell cell =
                                        row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                                cells[i] = formatter.formatCellValue(cell);
                            }
                            lines.add(cells);
                        }
                        EmployeesTableHelper.validateTable(lines);
                    }
                }
            }
        }
    }

    @Test
    void checkPdfTest() throws Exception {
        try (ZipInputStream zis =
                new ZipInputStream(Objects.requireNonNull(cl.getResourceAsStream(ZIPFILENAME)))) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".pdf") && !entry.getName().startsWith("__")) {
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    zis.transferTo(buffer);
                    PDF pdf = new PDF(new ByteArrayInputStream(buffer.toByteArray()));
                    List<String[]> lines = new ArrayList<>();

                    for (String row : pdf.text.split("\n")) {
                        String[] p = row.trim().split("\\s+");
                        if (p.length == 4 && p[0].equals("ID")) {
                            lines.add(p);
                        } else if (p.length >= 5 && Character.isDigit(p[0].charAt(0))) {
                            lines.add(new String[] {p[0], p[1] + " " + p[2], p[3], p[4]});
                        }
                    }
                    EmployeesTableHelper.validateTable(lines);
                }
            }
        }
    }

    @Test
    void checkCsvTest() throws Exception {
        try (ZipInputStream zis =
                new ZipInputStream(Objects.requireNonNull(cl.getResourceAsStream(ZIPFILENAME)))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".csv") && !entry.getName().startsWith("__")) {
                    CSVReader reader =
                            new CSVReader(new InputStreamReader(zis, StandardCharsets.UTF_8));
                    List<String[]> lines = reader.readAll();
                    EmployeesTableHelper.validateTable(lines);
                }
            }
        }
    }
}
