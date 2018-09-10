package net.torocraft.jakel.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.torocraft.jakel.api.LootApi;
import net.torocraft.jakel.capabilites.CapabilityPlayerData;
import net.torocraft.jakel.capabilites.CapabilitySpell;
import net.torocraft.jakel.loot.Stats;
import net.torocraft.jakel.spells.SpellData;
import net.torocraft.jakel.spells.SpellDataInventory;
import net.torocraft.jakel.spells.SpellTarget;
import net.torocraft.jakel.spells.Spells;

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

    SpellData data = CapabilitySpell.get(spell);

    if (data.type == null) {
      data.type = Spells.LIGHTNING;
    }

    if (data.inventory == null) {
      data.inventory = new SpellDataInventory();
    }

    for (int i = 0; i < data.inventory.getSizeInventory(); ++i) {
      System.out.println("======= " + data.inventory.getStackInSlot(i));
    }

    World world = player.world;

    Stats stats = CapabilityPlayerData.get(player).stats;
    // player.getCooldownTracker().setCooldown(stack.getItem(), 20);

    // TODO read from spell
    //AttackApi.largeFireBall(world, AttackApi.inFrontOf(message.pos, message.look, 3), message.look.scale(50), 2);

    SpellTarget target;
    if (MessageCastSpell.HIT_TYPE_BLOCK == message.hitType) {
      target = new SpellTarget(message.block, message.pos, message.look);
    } else if (MessageCastSpell.HIT_TYPE_ENTITY == message.hitType) {
      target = new SpellTarget(player.world.getEntityByID(message.entityId), message.pos, message.look);
    } else {
      target = new SpellTarget(message.pos, message.look);
    }

    Spells.LIGHTNING.cast(player, target);


  }
}