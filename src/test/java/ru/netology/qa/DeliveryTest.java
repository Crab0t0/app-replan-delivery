package ru.netology.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.qa.Generator;
import com.github.javafaker.Faker;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = Generator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = Generator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = Generator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.SHIFT, Keys.HOME, Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] span").click();
        $(".button__content").click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id='date'] input").sendKeys(Keys.SHIFT, Keys.HOME, Keys.DELETE);
        $("[data-test-id='date'] input").setValue(secondMeetingDate);
        $(".button__content").click();
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $(".button_view_extra").click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate));
    }

}
