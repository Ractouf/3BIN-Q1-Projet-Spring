package be.vinci.ipl.gateway.models.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.STRING)
public enum OrderType {
    MARKET, LIMIT
}
