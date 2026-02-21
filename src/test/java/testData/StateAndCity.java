package testData;

import net.datafaker.Faker;

public enum StateAndCity {
    NCR("NCR", "Delhi", "Gurgaon", "Noida"),
    UTTAR_PRADESH("Uttar Pradesh", "Agra", "Lucknow", "Merrut"),
    HARYANA("Haryana", "Karnal", "Panipat"),
    RAJASTHAN("Rajasthan", "Jaipur", "Jaiselmer");

    private final String state;
    private final String[] cities;

    StateAndCity(String state, String... cities) {
        this.state = state;
        this.cities = cities;
    }

    public String getState() {
        return state;
    }

    public String getRandomCity(Faker faker) {
        return faker.options().nextElement(cities);
    }
}

