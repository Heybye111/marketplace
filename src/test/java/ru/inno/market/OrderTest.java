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
    Item IphoneSE;
    Order order;
    Item Xiaomi;


    @BeforeEach
    public void setup() {
        catalog = new Catalog();
        //Для каждого теста создаем новый каталог
        client = new Client(1, "Petr");
        //Для каждого теста создаем нового клиента
        IphoneSE = catalog.getItemById(1);
        //Создаем переменную в которой будет лежать первый элемент из каталога
        order = new Order(client.getId(), client);
        // Создаем новый заказ
        Xiaomi = catalog.getItemById(2);
    }

    @Test
    @DisplayName("Проверка метода по добавлению товара в заказ")
    public void addItem() {
        order.addItem(IphoneSE);
        // В заранее созданный новый заказ добавляем вещь из каталога
        assertEquals(order.getCart().get(IphoneSE), 1);
        // Проверяем что в заказе 1 товар
        assertEquals(order.getTotalPrice(), IphoneSE.getPrice());
        // Проверяем что в общая стоимость равна стоимости товара так как он был один в заказе
        assertEquals(1, order.getCart().size());
        // Проверяем что в заказе 1 товар, так как мы добавили только один
    }

    @Test
    @DisplayName("Проверка применения скидки на нескольких товарах")
    public void applyDiscountOnManyItems() {
        order.addItem(IphoneSE);
        order.addItem(Xiaomi);
        assertFalse(order.isDiscountApplied());
        order.applyDiscount(PromoCodes.HAPPY_HOUR.getDiscount());
        assertEquals(order.getTotalPrice(), (IphoneSE.getPrice() + Xiaomi.getPrice()) * (1 - PromoCodes.HAPPY_HOUR.getDiscount()));
        assertTrue(order.isDiscountApplied());
    }

    @Test
    @DisplayName("Проверка применения скидки на одном товаре")
    public void applyDiscount() {
        order.addItem(IphoneSE);
        // Добавляем заказ 1 товар из каталога
        assertFalse(order.isDiscountApplied());
        // Проверяем что в заказе нет скидки
        order.applyDiscount(PromoCodes.HAPPY_HOUR.getDiscount());
        // Добавляем скидку
        assertEquals(order.getTotalPrice(), IphoneSE.getPrice() * (1 - PromoCodes.HAPPY_HOUR.getDiscount()));
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
        order.addItem(IphoneSE);
        order.addItem(IphoneSE);
        assertEquals(2, order.getCart().get(IphoneSE));
    }

    @Test
    @DisplayName("Проверка что в корзину можно положить несколько разных товаров")
    public void canCardHasDifferentItems() {
        assertNotNull(order.getCart());
        order.addItem(IphoneSE);
        order.addItem(Xiaomi);
        assertEquals(2, order.getCart().size());
    }

    @Test
    @DisplayName("Проверка что в заказе есть финальная сумма")
    public void isTotalPriceExistsInOrder() {
        assertEquals(0, order.getTotalPrice());
    }

//    @Test
//    @DisplayName("Проверка что все предметы из каталога можно добавить в заказ")
//    public void isAllItemsCanBeAddedToOrder() throws {
//        catalog.getAllItems();
//        order.addItem(catalog.getItemById());
//
//
//    }


//    @Test
//    @DisplayName("Проверка что нельзя положить товаров больше чем их в наличии")
//    void cantAddMoreThenExists() {
//
//        assertEquals(5, catalog.getCountForItem(IphoneSE));
//
//        int countOfItems = catalog.getCountForItem(IphoneSE);
//
//        order.addItem(IphoneSE);
//
//    }

//    @Test
//    @DisplayName("dasd")
//    void smf() {
//        catalog.getAllItems();
//    }


}
