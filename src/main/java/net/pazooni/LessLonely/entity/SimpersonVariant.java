package net.pazooni.LessLonely.entity;

import java.util.Arrays;
import java.util.Comparator;

public enum SimpersonVariant {
    SLIM_A(0);

    private static final SimpersonVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(SimpersonVariant::getID)).toArray(SimpersonVariant[]::new);

    private final int id;

    SimpersonVariant(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public static SimpersonVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
