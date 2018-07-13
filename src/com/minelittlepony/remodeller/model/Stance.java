package com.minelittlepony.remodeller.model;

public enum Stance {
    NONE,
    STANDING,
    SNEAKING,
    SWIMMING,
    RIDING,
    SLEEPING,
    FLYING;

    private static Stance activeStance = NONE;

    public static void setActiveStance(Stance stance) {
        activeStance = stance;
    }

    public static Stance getActiveStance() {
        return activeStance;
    }
}
