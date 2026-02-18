package pages.components;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;


public class CalendarComponent {
    public void setDate(LocalDate date){
        int day = date.getDayOfMonth();
        String monthName = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        int year = date.getYear();

        $(".react-datepicker__year-select").selectOption(String.valueOf(year));
        $(".react-datepicker__month-select").selectOption(monthName);
        $(".react-datepicker__day--0" + day + ":not(.react-datepicker__day--outside-month)").click();
    }
}
