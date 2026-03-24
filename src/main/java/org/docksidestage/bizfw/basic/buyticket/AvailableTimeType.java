package org.docksidestage.bizfw.basic.buyticket;

public enum AvailableTimeType {
    ALL_DAY(9),
    NIGHT_ONLY(17),
    AFTER_SIX(18);

    private final int entryTime;

    AvailableTimeType(int entryTime) {
        this.entryTime = entryTime;
    }

    public int getEntryTime() {
        return entryTime;
    }
}
