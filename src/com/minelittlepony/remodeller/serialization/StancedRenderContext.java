package com.minelittlepony.remodeller.serialization;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.minelittlepony.remodeller.ducks.IModelRenderer;
import com.minelittlepony.remodeller.model.Stance;

public class StancedRenderContext extends RenderContext {

    @Expose
    Map<Stance, RenderContext> stances = new HashMap<Stance, RenderContext>();

    public RenderContext getCurrentRenderContext() {
        return stances.get(Stance.getActiveStance());
    }

    @Override
    public void setup(IModelRenderer sender) {
        if (Stance.getActiveStance() == Stance.NONE) return;

        super.setup(sender);
        RenderContext stance = getCurrentRenderContext();
        if (stance != null) stance.setup(sender);
    }

    @Override
    public void tearDown(IModelRenderer sender, float scale) {
        if (Stance.getActiveStance() == Stance.NONE) return;

        RenderContext stance = getCurrentRenderContext();
        if (stance != null) stance.tearDown(sender, scale);

        super.tearDown(sender, scale);
    }
}
