package org.docksidestage.bizfw.basic.buyticket;

import static org.docksidestage.bizfw.basic.buyticket.TicketBooth.*;

// done TODO ichikawa Boothに依存したオブジェクトではないと思うので、独立させちゃった方がわかりやすいかも by jflute (2026/03/03)
public enum TicketType {
    ONE_DAY(1, ONE_DAY_PRICE, AvailableTimeType.ALL_DAY),
    TWO_DAY(2, TWO_DAY_PRICE, AvailableTimeType.ALL_DAY),
    TWO_DAY_NIGHT_ONLY(2, TWO_DAY_NIGHT_ONLY_PRICE, AvailableTimeType.NIGHT_ONLY),
    FOUR_DAY(4, FOUR_DAY_PRICE, AvailableTimeType.ALL_DAY);

    private final int availableDays;
    private final int price;
    private final AvailableTimeType availableTime;

    TicketType(int day, int price, AvailableTimeType availableTime) {
        this.availableDays = day;
        this.price = price;
        this.availableTime = availableTime;
    }

    public int getAvailableDays() {
        return availableDays;
    }

    public int getPrice() {
        return price;
    }

    public AvailableTimeType getAvailableTime() {
        return availableTime;
    }

    public static TicketType of(int day) {
        for (TicketType d : values()) {
            if (d.availableDays == day) {
                return d;
            }
        }
        throw new IllegalArgumentException("Unknown day: " + day);
    }
}
