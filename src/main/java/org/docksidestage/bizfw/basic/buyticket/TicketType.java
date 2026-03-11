package org.docksidestage.bizfw.basic.buyticket;

import static org.docksidestage.bizfw.basic.buyticket.TicketBooth.*;

// #1on1: コミット粒度、1コミットで1やった、って感じでOK (少なくともjavatryでは) (2026/03/11)
// 一方で、現場の粒度についての話。スカッシュとか。
// gitコメントのチケット番号のお話。
// gitコメントに背景書くというのはとても良い習慣。but jfluteとしては、コードに書こう感。 (2026/03/11)
// プルリクであれこれ説明するならコードにコメントに書こう
// https://jflute.hatenadiary.jp/entry/20181016/pulcomment

// done ichikawa Boothに依存したオブジェクトではないと思うので、独立させちゃった方がわかりやすいかも by jflute (2026/03/03)
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
