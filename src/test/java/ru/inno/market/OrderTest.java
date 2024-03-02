package ru.inno.market;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.inno.market.core.Catalog;
import ru.inno.market.model.Client;
import ru.inno.market.model.Item;
import ru.inno.market.model.Order;
import ru.inno.market.model.PromoCodes;
import static org.junit.jupiter.api.Assertions.*;


public class OrderTest {

    Catalog catalog = new Catalog();
    Client client;
    Item IphoneSE;
    Order order;
    Item Xiaomi;


    @BeforeEach
    public void setup() {
        client = new Client(1, "Petr");
        IphoneSE = catalog.getItemById(1);
        order = new Order(client.getId(), client);
        Xiaomi = catalog.getItemById(2);
    }

    @Test
    @DisplayName("Проверка метода по добавлению товара в заказ")
    public void addItem() {
        order.addItem(IphoneSE);
        assertEquals(order.getCart().get(IphoneSE), 1);
        assertEquals(order.getTotalPrice(), IphoneSE.getPrice());
        assertEquals(1, order.getCart().size());
    }

    @Test
    @DisplayName("Проверка применения скидки")
    public void applyDiscount() {
        order.addItem(IphoneSE);
        assertFalse(order.isDiscountApplied());
        order.applyDiscount(PromoCodes.HAPPY_HOUR.getDiscount());
        assertEquals(order.getTotalPrice(), IphoneSE.getPrice() * (1 - PromoCodes.HAPPY_HOUR.getDiscount()));
        assertTrue(order.isDiscountApplied());
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
    @DisplayName("Проверка финальной цены")
    public void checkTotalPrice() {}

    @Test
    @DisplayName("Проверка что скидка автоматически не применена ")
    public void checkDiscount() {
    }


}
