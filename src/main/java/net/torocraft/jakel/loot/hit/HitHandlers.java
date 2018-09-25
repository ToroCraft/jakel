package net.torocraft.jakel.loot.hit;

import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public enum HitHandlers implements IHitHandler {

  TEST((player, victim, parameters) -> {
    System.out.println("on hit test");
  });

  private static float toPercent(int amount) {
    return ((float) amount) / 100;
  }

  private final IHitHandler handler;

  HitHandlers(IHitHandler handler) {
    this.handler = handler;
  }

  public void onHit(EntityPlayer player, EntityLivingBase victim, List<Integer> parameters) {
    handler.onHit(player, victim, parameters);
  }

}
