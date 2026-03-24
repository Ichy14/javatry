package org.docksidestage.bizfw.basic.buyticket;

import java.time.*;

// #1on1: mainコードで使わせたくないジレンマのお話 (2026/03/24)
// 100%防ぐってのはそもそも難しいので、ある程度防ぐ演出をする言うニュアンス。
// (悪意は防げないけど、いかにポカミスを減らすか？)
// o mainとtestでTimeController分けてパッケージスコープ
// o use() じゃなくて、もっと露骨なメソッド名に e.g. useClockSwitchedForUnitTest()
// o JavaDocに書く
// o LastaFluteの例、lockの演出、ログの演出

public class TimeController implements AutoCloseable {
    // デフォルトはシステム時刻
    private static Clock currentClock = Clock.systemDefaultZone();

    // TODO ichikawa javadocで、paramとreturnを by jflute (2026/03/24)
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
