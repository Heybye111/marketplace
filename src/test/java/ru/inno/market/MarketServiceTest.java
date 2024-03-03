package ru.inno.market;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.inno.market.core.Catalog;
import ru.inno.market.core.MarketService;
import ru.inno.market.model.Client;
import ru.inno.market.model.Item;
import ru.inno.market.model.Order;
import ru.inno.market.model.PromoCodes;

import static org.junit.jupiter.api.Assertions.*;

public class MarketServiceTest {


    Catalog catalog;
    Client client;
    Order order;
    MarketService market;

    Item macbook;
    Client client1;
    Client client2;


    @BeforeEach
    public void setup(){
        catalog = new Catalog();
        market = new MarketService();
        client = new Client(1, "John");
        client1 = new Client(2, "Alex");
        client2 = new Client(3, "Bob");
        order = new Order(client.getId(), client);
        macbook = catalog.getItemById(5);


    }
    @Test
    @DisplayName("Проверка создания заказа")
    public void testCreateOrder(){
        int orderId = market.createOrderFor(client);
        assertTrue(orderId > 0);
        Order order = market.getOrderInfo(orderId);
        assertNotNull(order);
        assertEquals(orderId, order.getId());
        assertEquals(client, order.getClient());
    }

    @Test
    @DisplayName("Проверка создания нескольких заказов")
    void testCreateOrderForTwoClient(){
        int orderId1 = market.createOrderFor(client1);
        int orderId2 = market.createOrderFor(client2);
        assertNotEquals(orderId1,orderId2);
        Order order1 = market.getOrderInfo(orderId1);
        Order order2 = market.getOrderInfo(orderId2);

        assertNotNull(order1);
        assertNotNull(order2);
        assertEquals(client1, order1.getClient());
        assertEquals(client2, order2.getClient());
    }
    @Test
    @DisplayName("Проверка добавления заказа")
    public void addItemInMarket(){
        market.addItemToOrder(macbook,market.createOrderFor(client));
        assertEquals(1, market.getOrderInfo(1).getItems().size());


    }

    @Test
    @DisplayName("Проверка применения скидки")
    void testApplyDiscount(){
        market.addItemToOrder(macbook, market.createOrderFor(client));
        double totalPrice = market.applyDiscountForOrder(order.getId(), PromoCodes.FIRST_ORDER);
        assertEquals(71992.0, totalPrice);

    }

    @Test
    @DisplayName("Проверка информации заказа")
    void testGetOrderInfo(){
        int  orderId = market.createOrderFor(client);
        Order order = market.getOrderInfo(orderId);
        assertNotNull(order);
        assertEquals(orderId, order.getId());
        assertEquals(client, order.getClient());
    }




}


