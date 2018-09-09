package net.torocraft.jakel.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.torocraft.jakel.api.AttackApi;
import net.torocraft.jakel.api.LootApi;
import net.torocraft.jakel.capabilites.CapabilityPlayerData;
import net.torocraft.jakel.loot.Stats;

public class MessageCastSpellHandlerWorker implements Runnable {

  private final EntityPlayerMP player;
  private final MessageCastSpell message;

  public MessageCastSpellHandlerWorker(EntityPlayerMP player, MessageCastSpell message) {
    this.player = player;
    this.message = message;
  }

  @Override
  public void run() {

    ItemStack spell = LootApi.getEquippedSpell(player, message.slot);

    if (spell.isEmpty()) {
      System.out.println("Spell not equipped in " + message.slot);
      return;
    }

    World world = player.world;

    Stats stats = CapabilityPlayerData.get(player).stats;
    // player.getCooldownTracker().setCooldown(stack.getItem(), 20);

    // TODO read from spell
    AttackApi.largeFireBall(world, AttackApi.inFrontOf(message.pos, message.look, 3), message.look.scale(50), 2);

  }
}