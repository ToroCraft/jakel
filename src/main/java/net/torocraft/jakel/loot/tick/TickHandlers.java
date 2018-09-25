package net.torocraft.jakel.loot.tick;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public enum TickHandlers implements ITickHandler {

  DAMAGE_BOOST_FROM_NEARBY_MOBS((player, parameters) -> {
    System.out.println("handle tick!");
  });

  private final ITickHandler handler;

  TickHandlers(ITickHandler handler) {
    this.handler = handler;
  }

  @Override
  public void update(EntityPlayer player, List<Integer> parameters) {
    handler.update(player, parameters);
  }

}
