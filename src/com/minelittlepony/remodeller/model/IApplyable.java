package com.minelittlepony.remodeller.model;

import com.minelittlepony.remodeller.ducks.IModelRenderer;

public interface IApplyable {
    void apply(IModelRenderer renderer);
}
