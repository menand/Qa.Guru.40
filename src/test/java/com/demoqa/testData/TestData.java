package com.demoqa.testData;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import net.datafaker.Faker;

public class TestData {
    public static String firstName,
            lastName,
            userEmail,
            gender,
            userNumber,
            yearOfBirth,
            monthOfBirth,
            dayOfBirth,
            subjects,
            hobbies,
            picturePath,
            currentAddress,
            state,
            city;
    public static LocalDate birthDate;

    public static void prepareTestDate() {
        Faker faker = new Faker();
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        userEmail = faker.internet().emailAddress();
        gender = faker.options().option("Male", "Female", "Other");
        userNumber = faker.number().digits(10); // 10-значный номер
        // Генерация даты рождения (человек от 18 до 65 лет)
        birthDate = faker.timeAndDate().birthday(18, 65);
        yearOfBirth = String.valueOf(birthDate.getYear());
        monthOfBirth = birthDate.format(DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH));
        dayOfBirth = birthDate.format(DateTimeFormatter.ofPattern("dd"));
        // Образование и хобби
        subjects =
                faker.options()
                        .option("Maths", "Physics", "Chemistry", "Social Studies", "English");
        hobbies = faker.options().option("Sports", "Reading", "Music");
        // Картинка из папки Ресурсы-файлы
        File folder = new File("src/test/resources/files");
        File[] files = folder.listFiles();
        if (files != null && files.length > 0) {
            File randomFile = faker.options().nextElement(files);
            picturePath = "files/" + randomFile.getName();
        }
        currentAddress = faker.address().fullAddress();
        // штат и город связаны
        StateAndCity stateAndCity = faker.options().nextElement(StateAndCity.values());
        state = stateAndCity.getState();
        city = stateAndCity.getRandomCity(faker);
    }
}
