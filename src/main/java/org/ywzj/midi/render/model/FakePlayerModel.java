package org.ywzj.midi.render.model;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import org.ywzj.midi.entity.FakePlayerEntity;

public class FakePlayerModel extends PlayerModel<FakePlayerEntity> {

    public FakePlayerModel(ModelPart modelPart) {
        super(modelPart, false);
    }

    @Override
    public void setupAnim(FakePlayerEntity entity, float p_102867_, float p_102868_, float p_102869_, float p_102870_, float p_102871_) {
        super.setupAnim(entity, p_102867_, p_102868_, p_102869_, p_102870_, p_102871_);
        this.rightArm.xRot = Math.min(this.rightArm.xRot, -0.2f);
        this.rightSleeve.xRot = Math.min(this.rightSleeve.xRot, -0.2f);
        this.leftArm.xRot = Math.min(this.leftArm.xRot, -0.2f);
        this.leftSleeve.xRot = Math.min(this.leftSleeve.xRot, -0.2f);
        if (entity.isSitting() && !this.riding) {
            this.rightLeg.xRot = -1.4137167F;
            this.rightLeg.yRot = ((float)Math.PI / 10F);
            this.rightLeg.zRot = 0.07853982F;
            this.rightPants.xRot = -1.4137167F;
            this.rightPants.yRot = ((float)Math.PI / 10F);
            this.rightPants.zRot = 0.07853982F;
            this.leftLeg.xRot = -1.4137167F;
            this.leftLeg.yRot = (-(float)Math.PI / 10F);
            this.leftLeg.zRot = -0.07853982F;
            this.leftPants.xRot = -1.4137167F;
            this.leftPants.yRot = (-(float)Math.PI / 10F);
            this.leftPants.zRot = -0.07853982F;
        }
    }

}
