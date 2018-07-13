package com.minelittlepony.remodeller.model;

public enum Arithmetic {
    ADD {
        @Override
        public float apply(float one, float two) {
            return one + two;
        }
    },
    SUBSTRACT{
        @Override
        public float apply(float one, float two) {
            return one - two;
        }
    },
    ASSIGN {
        @Override
        public float apply(float one, float two) {
            return two;
        }
    },
    NEGATE {
        @Override
        public float apply(float one, float two) {
            return -one;
        }
    };

    public abstract float apply(float one, float two);
}
