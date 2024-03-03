package ru.inno.market;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.inno.market.core.Catalog;
import ru.inno.market.model.Client;
import ru.inno.market.model.Item;
import ru.inno.market.model.Order;
import ru.inno.market.model.PromoCodes;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class OrderTest {

    Catalog catalog;
    Client client;
    Item iphoneSE;
    Order order;
    Item xiaomi;


    @BeforeEach
    public void setup() {
        catalog = new Catalog();
        //Для каждого теста создаем новый каталог
        client = new Client(1, "Petr");
        //Для каждого теста создаем нового клиента
        iphoneSE = catalog.getItemById(1);
        //Создаем переменную в которой будет лежать первый элемент из каталога
        order = new Order(client.getId(), client);
        // Создаем новый заказ
        xiaomi = catalog.getItemById(2);
    }

    @Test
    @DisplayName("Проверка метода по добавлению товара в заказ")
    public void addItem() {
        order.addItem(iphoneSE);
        // В заранее созданный новый заказ добавляем вещь из каталога
        assertEquals(order.getCart().get(iphoneSE), 1);
        // Проверяем что в заказе 1 товар
        assertEquals(order.getTotalPrice(), iphoneSE.getPrice());
        // Проверяем что в общая стоимость равна стоимости товара так как он был один в заказе
        assertEquals(1, order.getCart().size());
        // Проверяем что в заказе 1 товар, так как мы добавили только один
    }

    @Test
    @DisplayName("Проверка применения скидки на нескольких товарах")
    public void applyDiscountOnManyItems() {
        order.addItem(iphoneSE);
        order.addItem(xiaomi);
        assertFalse(order.isDiscountApplied());
        order.applyDiscount(PromoCodes.HAPPY_HOUR.getDiscount());
        assertEquals(order.getTotalPrice(), (iphoneSE.getPrice() + xiaomi.getPrice()) * (1 - PromoCodes.HAPPY_HOUR.getDiscount()));
        assertTrue(order.isDiscountApplied());
    }

    @Test
    @DisplayName("Проверка применения скидки на одном товаре")
    public void applyDiscount() {
        order.addItem(iphoneSE);
        // Добавляем заказ 1 товар из каталога
        assertFalse(order.isDiscountApplied());
        // Проверяем что в заказе нет скидки
        order.applyDiscount(PromoCodes.HAPPY_HOUR.getDiscount());
        // Добавляем скидку
        assertEquals(order.getTotalPrice(), iphoneSE.getPrice() * (1 - PromoCodes.HAPPY_HOUR.getDiscount()));
        // Проверяем что цена товара соответствует цене с примененной скидкой
        assertTrue(order.isDiscountApplied());
        // Проверяем что скидка применена
    }


    @Test
    @DisplayName("Проверка что у заказа есть айди")
    public void isIdExistInOrder() {
        assertEquals(1, order.getId());
    }

    @Test
    @DisplayName("Проверка что у заказа есть клиент")
    public void isClientExistInOrder() {
        assertNotNull(order.getClient());
        assertEquals(client, order.getClient());
    }

    @Test
    @DisplayName("Проверка на то что в корзину можно положить два одинаковых товара")
    public void canCardHasSameItems() {
        assertNotNull(order.getCart());
        order.addItem(iphoneSE);
        order.addItem(iphoneSE);
        assertEquals(2, order.getCart().get(iphoneSE));
    }

    @Test
    @DisplayName("Проверка что в корзину можно положить несколько разных товаров")
    public void canCardHasDifferentItems() {
        assertNotNull(order.getCart());
        order.addItem(iphoneSE);
        order.addItem(xiaomi);
        assertEquals(2, order.getCart().size());
    }

    @Test
    @DisplayName("Проверка что в заказе есть финальная сумма")
    public void isTotalPriceExistsInOrder() {
        assertEquals(0, order.getTotalPrice());
    }


}
