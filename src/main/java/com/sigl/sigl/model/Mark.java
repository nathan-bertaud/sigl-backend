package com.sigl.sigl.model;

public enum Mark {
    ZERO("0", 0),
    UN("1", 1),
    DEUX("2", 2),
    TROIS("3", 3),
    QUATRE("4", 4),
    CINQ("5", 5),
    SIX("6", 6),
    SEPT("7", 7),
    HUIT("8", 8),
    NEUF("9", 9),
    DIX("10", 10),
    ONZE("11", 11),
    DOUZE("12", 12),
    TREIZE("13", 13),
    QUATORZE("14", 14),
    QUINZE("15", 15),
    SEIZE("16", 16),
    DIX_SEPT("17", 17),
    DIX_HUIT("18", 18),
    DIX_NEUF("19", 19),
    VINGT("20", 20),
    AJOURNE("Ajourné", -1),
    ANNULE("Annulé", -2),
    ABSENT("Absent", -3);

    private final String label;
    private final int value;

    Mark(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }

    public int getValue() {
        return this.value;
    }
}
