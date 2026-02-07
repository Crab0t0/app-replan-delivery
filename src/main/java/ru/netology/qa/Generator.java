package ru.netology.qa;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Generator {
    private Generator() {
    }

    private static final String[] cities = new String[]{
            "Майкоп", "Горно-Алтайск", "Уфа", "Улан-Удэ", "Махачкала", "Магас", "Нальчик", "Элиста", "Черкесск", "Петрозаводск",
            "Сыктывкар", "Симферополь", "Йошкар-Ола", "Саранск", "Якутск", "Владикавказ", "Казань", "Кызыл", "Ижевск", "Абакан"
    };

    public static String generateDate(int data) {
        return LocalDate.now().plusDays(data).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity(Faker faker) {
        return faker.options().option(cities);
    }

    public static String generateName(Faker faker) {
        return faker.name().firstName() + " " + faker.name().lastName()
                .replace("ё", "е").replace("Ё", "Е");
    }

    public static String generatePhone(Faker faker) {
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private static Faker faker;

        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            var faker = new Faker(new Locale(locale));
            return new UserInfo(generateCity(faker), generateName(faker), generatePhone(faker));
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
