package com.minelittlepony.remodeller.serialization;

import com.google.gson.annotations.Expose;
import com.minelittlepony.remodeller.ducks.IModelRenderer;

import net.minecraft.client.renderer.GlStateManager;

public class RenderContext extends Serializable {

    @Expose
    double[] offset = new double[3];

    @Expose
    float[] rotate = new float[3];

    @Expose
    float[] scale = new float[3];

    @Expose
    StancedRenderContext clone;

    @Expose
    float[][] lookmatrix = new float[3][3];

    public void setup(IModelRenderer sender) {
        GlStateManager.pushMatrix();

        if (offset[0] != 0 || offset[1] != 0 || offset[2] != 0) {
            GlStateManager.translate(offset[0], offset[1], offset[2]);
        }

        if (rotate[0] != 0) {
            GlStateManager.rotate(rotate[0], 1, 0, 0);
        }

        if (rotate[1] != 0) {
            GlStateManager.rotate(rotate[1], 0, 1, 0);
        }

        if (rotate[2] != 0) {
            GlStateManager.rotate(rotate[2], 0, 0, 1);
        }

        matrixTransformHead(0, sender.getOwningModel().bipedHead.rotateAngleX, 1, 0, 0);
        matrixTransformHead(1, sender.getOwningModel().bipedHead.rotateAngleY, 0, 1, 0);
        matrixTransformHead(2, sender.getOwningModel().bipedHead.rotateAngleZ, 0, 0, 1);

        if (scale[0] != 0 || scale[1] != 0 || scale[2] != 0) {
            GlStateManager.scale(1 + scale[0], 1 + scale[1], 1 + scale[2]);
        }

    }

    public void tearDown(IModelRenderer sender, float scale) {
        GlStateManager.popMatrix();

        if (clone != null) {
            RenderContext cached = sender.getRenderingContext();

            sender.setRenderingContext(clone);
            sender.rerender(scale);
            sender.setRenderingContext(cached);
        }
    }

    private void matrixTransformHead(int i, float f, float x, float y, float z) {
        if (lookmatrix[i][0] != 0) {
            f *= 180 / (float)Math.PI;
            GlStateManager.rotate((lookmatrix[i][0] * f) + clamp(lookmatrix[i][1] * f, lookmatrix[i][2], lookmatrix[i][3]), x, y, z);
        }
    }

    private static float clamp(float value, float min, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
}
