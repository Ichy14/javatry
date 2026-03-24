package org.docksidestage.bizfw.basic.buyticket;

import java.time.*;

public class TimeController implements AutoCloseable {
    // デフォルトはシステム時刻
    private static Clock currentClock = Clock.systemDefaultZone();

    /**
     * 目的のメソッド: 時刻を指定して「固定」モードに入る
     */
    public static TimeController use(LocalDateTime dateTime, ZoneId zoneId) {
        Instant instant = dateTime.atZone(zoneId).toInstant();
        currentClock = Clock.fixed(instant, zoneId);
        return new TimeController();
    }

    /**
     * 現在設定されているClockを取得する
     */
    public static Clock clock() {
        return currentClock;
    }

    /**
     * try-with-resourcesの終了時に自動で呼ばれる
     */
    @Override
    public void close() {
        currentClock = Clock.systemDefaultZone();
    }
}
