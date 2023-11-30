package be.vinci.ipl.gateway.models;


import be.vinci.ipl.gateway.models.enums.OrderSide;
import be.vinci.ipl.gateway.models.enums.OrderType;

public class Order {

    private String owner;
    private int timestamp;
    private String ticker;
    private int quantity;
    private OrderSide side;
    private OrderType type;
    private double limit;
    private int filled = 0;

}

