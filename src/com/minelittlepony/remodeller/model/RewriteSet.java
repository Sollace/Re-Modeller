package com.minelittlepony.remodeller.model;

import java.util.List;

import com.minelittlepony.remodeller.ducks.IModelRenderer;

public class RewriteSet implements IApplyable {

    private List<BasicRewrite> actions;

    @Override
    public void apply(IModelRenderer renderer) {
        for (IApplyable i : actions) {
            i.apply(renderer);
        }
    }
}
