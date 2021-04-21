package framework.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import com.codeborne.selenide.ex.ElementShouldNot;

import java.time.Duration;

/**
 * Класс с методами-обертками над проверками состояния элементов с возвращением boolean
 */
public class FluentWaitCondition {

    /** Проверка, что элемент виден на странице с использованием явного ожидания
     *
     * @param elem     SelenideElement
     * @param duration Duration of fluent wait in sec
     * @return true - element is visible, false - element is not visible within the duration time limit
     */
    public static boolean ShouldBeVisible(SelenideElement elem, long duration) {
        boolean flag;
        try {
            elem.should(Condition.visible, Duration.ofSeconds(duration));
            flag = true;

        } catch (ElementNotFound | ElementShould e) {
            flag = false;
        }
        return flag;
    }

    /**
     * Проверка, что элемент виден на странице с использованием 4-секундного явного ожидания
     *
     * @param elem SelenideElement
     * @return true - element is visible, false - element is not visible within 4 secs
     */
    public static boolean ShouldBeVisible(SelenideElement elem) {
        return ShouldBeVisible(elem, 4);
    }

    /***
     * Проверка, что элемент недоступен на странице с использованием явного ожидания
     *
     * @param elem SelenideElement
     * @param duration Duration of fluent wait in sec
     * @return true - element is not visible, false - element is visible within the duration time limit
     */
    public static boolean ShouldNotBeVisible(SelenideElement elem, long duration) {
        boolean flag;
        try {
            elem.shouldNotBe(Condition.visible, Duration.ofSeconds(duration));
            flag = true;
        } catch (ElementShouldNot e) {
            flag = false;
        }
        return flag;
    }

    /**
     * Проверка, что элемент не виден на странице с использованием 4-секудного явного ожидания
     *
     * @param elem SelenideElement
     * @return element is not visible, false - element is visible within 4 secs
     */
    public static boolean ShouldNotBeVisible(SelenideElement elem) {
        return ShouldNotBeVisible(elem, 4);
    }

}
