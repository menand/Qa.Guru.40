package files.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import files.models.JsonShop;
import files.models.StaffJson;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class JsonFileTest {

    @Test
    void testParsingJson() {
        try (InputStream in =
                getClass().getClassLoader().getResourceAsStream("files/json/Shop.json")) {
            ObjectMapper mapper = new ObjectMapper();
            JsonShop js = mapper.readValue(in, JsonShop.class);

            assertEquals("menand's shop", js.getTitle());
            assertEquals(5, js.getAddress().getHouseNumber());

            StaffJson[] staff = js.getStaff();
            int sellersCount = 0;
            for (StaffJson staffJson : staff) {
                assertTrue(staffJson.getSalary() > 0);
                assertNotNull(staffJson.getRole());
                if (staffJson.getRole().equals("Seller")) sellersCount++;
                if (!staffJson.getRole().equals("Director"))
                    assertNotNull(staffJson.getManagerId());
                if (staffJson.getRole().equals("Director")) assertNull(staffJson.getManagerId());
            }

            assertTrue(sellersCount >= 2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
