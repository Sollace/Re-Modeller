package com.minelittlepony.remodeller.model;

import com.minelittlepony.remodeller.ducks.IModelRenderer;

public class BasicRewrite implements IApplyable {

    public float x;
    public float y;
    public float z;

    RewriteAction action = RewriteAction.NONE;

    Arithmetic operation = Arithmetic.ADD;

    @Override
    public void apply(IModelRenderer renderer) {
        action.apply(renderer.unwrap(), operation, this);
    }
}
