package net.torocraft.jakel.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.torocraft.jakel.api.LootApi;
import net.torocraft.jakel.capabilites.CapabilitySpell;
import net.torocraft.jakel.spells.ISpellCaster;
import net.torocraft.jakel.spells.SpellData;
import net.torocraft.jakel.spells.SpellTarget;

public class MessageCastSpellHandlerWorker implements Runnable {

  private final EntityPlayerMP player;
  private final MessageCastSpell message;

  public MessageCastSpellHandlerWorker(EntityPlayerMP player, MessageCastSpell message) {
    this.player = player;
    this.message = message;
  }

  @Override
  public void run() {
    ItemStack spellItemStack = LootApi.getEquippedSpell(player, message.slot);
    if (spellItemStack.isEmpty() || !(spellItemStack.getItem() instanceof ISpellCaster)) {
      return;
    }
    ((ISpellCaster)spellItemStack.getItem()).cast(player, spellItemStack, buildTargetObj());
  }

  private SpellTarget buildTargetObj() {
    SpellTarget target;
    if (MessageCastSpell.HIT_TYPE_BLOCK == message.hitType) {
      target = new SpellTarget(message.block, message.pos, message.look);
    } else if (MessageCastSpell.HIT_TYPE_ENTITY == message.hitType) {
      target = new SpellTarget(player.world.getEntityByID(message.entityId), message.pos, message.look);
    } else {
      target = new SpellTarget(message.pos, message.look);
    }
    return target;
  }
}