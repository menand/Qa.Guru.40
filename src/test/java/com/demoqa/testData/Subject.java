package com.demoqa.testData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public enum Subject {
    ACCOUNTING("Accounting"),
    ARTS("Arts"),
    BIOLOGY("Biology"),
    CHEMISTRY("Chemistry"),
    CIVICS("Civics"),
    COMMERCE("Commerce"),
    COMPUTER_SCIENCE("Computer Science"),
    ECONOMICS("Economics"),
    ENGLISH("English"),
    HINDI("Hindi"),
    HISTORY("History"),
    MATHS("Maths"),
    PHYSICS("Physics"),
    SOCIAL_STUDIES("Social Studies");

    private final String displayName;

    Subject(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static List<Subject> getRandomSubjects() {
        Subject[] values = values();                     // все предметы
        List<Subject> copy = new ArrayList<>(List.of(values)); // изменяемая копия
        Collections.shuffle(copy);                        // перемешиваем
        int count = ThreadLocalRandom.current().nextInt(1, values.length + 1); // случайное число от 1 до 14
        return new ArrayList<>(copy.subList(0, count));   // берём первые count элементов
    }
}