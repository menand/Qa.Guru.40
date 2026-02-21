package pages.components;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;


public class CalendarComponent {
    public void setDate(LocalDate date) {
        String day = date.format(DateTimeFormatter.ofPattern("dd")),
                monthName = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                year = date.format(DateTimeFormatter.ofPattern("yyyy"));

        $(".react-datepicker__year-select").selectOption(year);
        $(".react-datepicker__month-select").selectOption(monthName);
        $(".react-datepicker__day--0" + day + ":not(.react-datepicker__day--outside-month)").click();
    }
}
