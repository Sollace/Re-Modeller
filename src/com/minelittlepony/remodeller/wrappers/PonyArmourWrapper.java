package com.minelittlepony.remodeller.wrappers;

import com.google.gson.Gson;
import com.minelittlepony.model.armour.ModelPonyArmor;
import com.minelittlepony.model.armour.PonyArmor;
import com.minelittlepony.remodeller.ModelRewriteManager;
import com.minelittlepony.remodeller.ducks.IModelBiped;
import com.minelittlepony.remodeller.model.Stance;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class PonyArmourWrapper extends ModelPonyArmor {

    private final ModelBiped wrappedObj;

    protected static ModelBiped clone(ModelBiped model) {
        Gson gson = new Gson();

        String json = gson.toJson(model);

        return gson.fromJson(json, model.getClass());
    }

    public static PonyArmor wrap(ModelBiped model) {
        PonyArmourWrapper wrapper = new PonyArmourWrapper(model);
        wrapper.init(0, 0);
        return new PonyArmor(wrapper, wrapper);
    }

    private PonyArmourWrapper(ModelBiped model) {
        wrappedObj = model;
    }

    @Override
    public void init(float yOffset, float stretch) {
        super.init(yOffset, stretch);

        bipedBody = new WrappedPonyRenderer(this, bipedBody, wrappedObj.bipedBody);
        bipedHead = new WrappedPonyRenderer(this, bipedHead, wrappedObj.bipedHead);

        bipedLeftArm = new WrappedPonyRenderer(this, bipedLeftArm, wrappedObj.bipedLeftArm);
        bipedRightArm = new WrappedPonyRenderer(this, bipedRightArm, wrappedObj.bipedRightArm);
        bipedLeftLeg = new WrappedPonyRenderer(this, bipedLeftLeg, wrappedObj.bipedLeftLeg);
        bipedRightLeg = new WrappedPonyRenderer(this, bipedRightLeg, wrappedObj.bipedRightLeg);

        if (wrappedObj instanceof ModelPlayer) {
            ModelPlayer mp = (ModelPlayer)wrappedObj;

            bipedBodyWear = new WrappedPonyRenderer(this, bipedBodyWear, mp.bipedBodyWear);
            bipedHeadwear = new WrappedPonyRenderer(this, bipedHeadwear, mp.bipedHeadwear);

            bipedLeftArmwear = new WrappedPonyRenderer(this, bipedLeftArmwear, mp.bipedLeftArmwear);
            bipedRightArmwear = new WrappedPonyRenderer(this, bipedRightArmwear, mp.bipedRightLegwear);
            bipedLeftLegwear = new WrappedPonyRenderer(this, bipedLeftLegwear, mp.bipedLeftLegwear);
            bipedRightLegwear = new WrappedPonyRenderer(this, bipedRightLegwear, mp.bipedRightLegwear);
        }
    }

    @Override
    public void showSaddle(boolean isPony) {
        flankGuard.showModel = false;
        saddle.showModel = false;
    }

    @Override
    public ModelRenderer getHead() {
        return wrappedObj.bipedHead;
    }

    private void setSecondVisible(boolean show) {

        wrappedObj.bipedLeftArm.showModel = show;
        wrappedObj.bipedRightArm.showModel = show;
        wrappedObj.bipedLeftLeg.showModel = show;
        wrappedObj.bipedRightLeg.showModel = show;

        if (wrappedObj instanceof ModelPlayer) {
            ModelPlayer mp = (ModelPlayer)wrappedObj;

            mp.bipedLeftArmwear.showModel = show;
            mp.bipedRightArmwear.showModel = show;
            mp.bipedLeftLegwear.showModel = show;
            mp.bipedRightLegwear.showModel = show;
        }
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entity, float move, float swing, float ticks) {
        wrappedObj.setLivingAnimations(entity, move, swing, ticks);
        super.setLivingAnimations(entity, move, swing, ticks);
    }

    @Override
    public void setRotationAngles(float move, float swing, float ticks, float headYaw, float headPitch, float scale, Entity entity) {
        super.setRotationAngles(move, swing, ticks, headYaw, headPitch, scale, entity);

        // Reverse engineer pony head angles to mimic the vanilla call
       // float theadYaw = this.bipedHead.rotateAngleY / 0.017453292F;
       // float theadPitch = this.bipedHead.rotateAngleX / 0.017453292F;

        wrappedObj.setRotationAngles(move, swing, ticks, headYaw, headPitch, scale, entity);
    }

    @Override
    public void setModelAttributes(ModelBase model) {
        wrappedObj.setModelAttributes(model);
        super.setModelAttributes(model);
    }

    protected Stance getActiveStance() {
        if (isSneak) {
            return Stance.SNEAKING;
        }
        if (isGoingFast()) {
            return Stance.FLYING;
        }
        if (isSwimming()) {
            return Stance.SWIMMING;
        }
        if (isRiding()) {
            return Stance.RIDING;
        }
        if (isSleeping()) {
            return Stance.SLEEPING;
        }

        return Stance.STANDING;
    }

    @Override
    public void render(Entity entity, float move, float swing, float ticks, float headYaw, float headPitch, float scale) {
        Stance.setActiveStance(getActiveStance());

        ModelRewriteManager.INSTANCE.rewriteModel((IModelBiped)wrappedObj);

        GlStateManager.pushMatrix();
        GlStateManager.scale(1.01F, 1.01F, 1.01F);

        super.render(entity, move, swing, ticks, headYaw, headPitch, scale);

        copyModelAngles(bipedHead, wrappedObj.bipedHead);
        copyModelAngles(bipedHeadwear, wrappedObj.bipedHeadwear);
        copyModelAngles(bipedBody, wrappedObj.bipedBody);

        setSecondVisible(false);
        wrappedObj.render(entity, move, swing, ticks, headYaw, headPitch, scale);
        setSecondVisible(true);

        GlStateManager.popMatrix();

        Stance.setActiveStance(Stance.NONE);
    }
}
