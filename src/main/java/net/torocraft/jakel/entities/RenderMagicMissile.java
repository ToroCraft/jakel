package net.torocraft.jakel.entities;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.torocraft.jakel.loot.Element;

public class RenderMagicMissile extends Render<EntityMagicMissile> {

  private final float scale;

  public RenderMagicMissile(RenderManager renderManagerIn) {
    this(renderManagerIn, 0.5f);
  }


  public RenderMagicMissile(RenderManager renderManagerIn, float scaleIn) {
    super(renderManagerIn);
    this.scale = scaleIn;
  }

  @Override
  public void doRender(EntityMagicMissile entity, double x, double y, double z, float entityYaw, float partialTicks) {
    GlStateManager.pushMatrix();
    this.bindEntityTexture(entity);
    GlStateManager.translate((float) x, (float) y, (float) z);
    GlStateManager.enableRescaleNormal();

    GlStateManager.scale(this.scale, this.scale, this.scale);
    TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(Items.FIRE_CHARGE);
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    float f = textureatlassprite.getMinU();
    float f1 = textureatlassprite.getMaxU();
    float f2 = textureatlassprite.getMinV();
    float f3 = textureatlassprite.getMaxV();
    float f4 = 1.0F;
    float f5 = 0.5F;
    float f6 = 0.25F;
    GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate((float) (this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

    if (this.renderOutlines) {
      GlStateManager.enableColorMaterial();
      GlStateManager.enableOutlineMode(this.getTeamColor(entity));
    }

    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
    bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex((double) f, (double) f3).normal(0.0F, 1.0F, 0.0F).endVertex();
    bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex((double) f1, (double) f3).normal(0.0F, 1.0F, 0.0F).endVertex();
    bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex((double) f1, (double) f2).normal(0.0F, 1.0F, 0.0F).endVertex();
    bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex((double) f, (double) f2).normal(0.0F, 1.0F, 0.0F).endVertex();

    if (entity.elemental.equals(Element.COLD)) {
      GlStateManager.color(0x0, 0x00, 0xff);
    }

    if (entity.elemental.equals(Element.POISON)) {
      GlStateManager.color(0x0, 0xff, 0x0);
    }

    tessellator.draw();

    if (entity.elemental.equals(Element.COLD) || entity.elemental.equals(Element.POISON)) {
      GlStateManager.color(0xff, 0xff, 0xff);
    }

    if (this.renderOutlines) {
      GlStateManager.disableOutlineMode();
      GlStateManager.disableColorMaterial();
    }

    GlStateManager.disableRescaleNormal();
    GlStateManager.popMatrix();
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
  }

  @Nullable
  @Override
  protected ResourceLocation getEntityTexture(EntityMagicMissile entity) {
    return TextureMap.LOCATION_BLOCKS_TEXTURE;
  }

}