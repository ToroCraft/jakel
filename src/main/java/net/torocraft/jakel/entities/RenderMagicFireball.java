package net.torocraft.jakel.entities;

import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderMagicFireball extends RenderFireball {

  public static void init() {
    RenderingRegistry.registerEntityRenderingHandler(EntityMagicFireball.class, RenderMagicFireball::new);
  }

  public RenderMagicFireball(RenderManager renderManagerIn) {
    super(renderManagerIn, 1);
  }

}