package com.demoqa.pages.components;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class CalendarComponent {
    private final SelenideElement yearPicker = $(".react-datepicker__year-select"),
            monthPicker = $(".react-datepicker__month-select");

    private SelenideElement getDayElement(String day) {
        return $(".react-datepicker__day--0" + day + ":not(.react-datepicker__day--outside-month)");
    }

    public void setDate(LocalDate date) {
        String day = date.format(DateTimeFormatter.ofPattern("dd")),
                monthName = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                year = date.format(DateTimeFormatter.ofPattern("yyyy"));

        yearPicker.selectOption(year);
        monthPicker.selectOption(monthName);
        getDayElement(day).click();
    }
}
