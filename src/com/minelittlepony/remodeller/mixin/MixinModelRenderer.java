package com.minelittlepony.remodeller.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.minelittlepony.remodeller.ducks.IModelRenderer;
import com.minelittlepony.remodeller.serialization.RenderContext;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

@Mixin(ModelRenderer.class)
public abstract class MixinModelRenderer implements IModelRenderer {

    private RenderContext context;

    @Shadow
    private boolean compiled;

    @Shadow @Final
    private ModelBase baseModel;

    @Accessor
    @Override
    public abstract int getTextureOffsetX();

    @Accessor
    @Override
    public abstract int getTextureOffsetY();

    @Override
    public void setRenderingContext(RenderContext context) {
        this.context = context;
    }

    @Override
    public RenderContext getRenderingContext() {
        return context;
    }

    @Override
    public ModelBiped getOwningModel() {
        return (ModelBiped)baseModel;
    }

    private boolean shouldApplyContext() {
        return compiled && !unwrap().isHidden && unwrap().showModel && getRenderingContext() != null;
    }

    @Inject(method = "render(F)V", at = @At("HEAD"))
    private void beforeRender(float scale, CallbackInfo info) {
        if (shouldApplyContext()) {
            getRenderingContext().setup(this);
        }
    }

    @Override
    public void rerender(float scale) {
        unwrap().render(scale);
    }

    @Inject(method = "render(F)V", at = @At("RETURN"))
    private void afterRender(float scale, CallbackInfo info) {
        if (shouldApplyContext()) {
            getRenderingContext().tearDown(this, scale);
        }
    }
}
