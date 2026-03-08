package files.tests;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.fasterxml.jackson.databind.ObjectMapper;
import files.models.JsonShop;
import files.models.StaffJson;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;

class JsonFileTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testParsingJson() throws Exception {
        InputStream in = getClass().getClassLoader().getResourceAsStream("files/json/Shop.json");
        JsonShop js = mapper.readValue(in, JsonShop.class);
        assertSoftly(
                softly -> {
                    softly.assertThat(in).as("JSON файл должен существовать").isNotNull();
                    softly.assertThat(js.getTitle())
                            .as("Название магазина")
                            .isEqualTo("menand's shop");
                    softly.assertThat(js.getAddress().getHouseNumber())
                            .as("Номер дома")
                            .isEqualTo(5);
                    List<StaffJson> staff = js.getStaff();
                    int sellers = 0;
                    for (StaffJson s : staff) {
                        softly.assertThat(s.getSalary())
                                .as("Salary должен быть положительным")
                                .isPositive();
                        softly.assertThat(s.getRole())
                                .as("Role не должен быть пустым")
                                .isNotBlank();
                        switch (s.getRole()) {
                            case "Seller" -> sellers++;
                            case "Director" ->
                                    softly.assertThat(s.getManagerId())
                                            .as("Director не должен иметь managerId")
                                            .isNull();
                            default ->
                                    softly.assertThat(s.getManagerId())
                                            .as("Сотрудник должен иметь managerId")
                                            .isNotNull();
                        }
                    }
                    softly.assertThat(sellers).as("Количество продавцов").isGreaterThanOrEqualTo(2);
                });
    }
}
