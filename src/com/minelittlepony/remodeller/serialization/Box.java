package com.minelittlepony.remodeller.serialization;

import com.google.gson.annotations.Expose;

import net.minecraft.client.model.ModelBox;

public class Box extends Serializable {

    @Expose
    float[] pos1;

    @Expose
    float[] pos2;

    @Expose
    String name;

    public Box() {}

    public Box(ModelBox box) {
        name = box.boxName;

        pos1 = new float[] {
            box.posX1, box.posY1, box.posZ1
        };

        pos2 = new float[] {
            box.posX2, box.posY2, box.posZ2
        };
    }
}
