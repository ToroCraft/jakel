package net.torocraft.jakel.network;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.torocraft.jakel.api.AttackApi;
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
    System.out
        .println(" **** Cast Spell on Server Side  hit[" + message.hitType + "] entity[" + message.entityId + "] block[" + message.block + "] pos[" + message.pos + "] look[" + message.look + "]");


    World world = player.world;

    Stats stats = CapabilityPlayerData.get(player).stats;

    // TODO spell type needs to be sent

    // player.getCooldownTracker().setCooldown(stack.getItem(), 20);


    AttackApi.largeFireBall(world, AttackApi.inFrontOf(message.pos, message.look, 3), message.look.scale(50), 2);

  }
//
//  private void interactOnBlock(MessageCastSpell message, EntityPlayerMP player) {
//    IExtendedReach extendedReachItem = (IExtendedReach) player.getHeldItemMainhand().getItem();
//    double distanceSq = player.getDistanceSq(message.block);
//    double reachSq = extendedReachItem.getReach() * extendedReachItem.getReach();
//
//    if (reachSq >= distanceSq) {
//      Vec3d vec = player.getPositionVector();
//
//      extendedReachItem.onItemUseExtended(player, player.getEntityWorld(), message.block, EnumHand.MAIN_HAND, null, (float) vec.x,
//          (float) vec.y, (float) vec.z);
//
//      player.swingArm(EnumHand.MAIN_HAND);
//    }
//  }
//
//  private void interaceOnEntity(final MessageCastSpell message, final EntityPlayerMP player) {
//    Entity entity = player.world.getEntityByID(message.entityId);
//
//    if (!(entity instanceof EntityLivingBase)) {
//      return;
//    }
//
//    if (notAnExtendedReachItem(player)) {
//      return;
//    }
//
//    IExtendedReach extendedReachItem = (IExtendedReach) player.getHeldItemMainhand().getItem();
//
//    double distanceSq = player.getDistanceSq(entity);
//    double reachSq = extendedReachItem.getReach() * extendedReachItem.getReach();
//
//    if (reachSq >= distanceSq) {
//      extendedReachItem.itemInteractionForEntityExtended(player.getHeldItemMainhand(), player, (EntityLivingBase) entity, EnumHand.MAIN_HAND);
//      player.swingArm(EnumHand.MAIN_HAND);
//    }
//  }
//
//  private boolean notAnExtendedReachItem(final EntityPlayerMP player) {
//    return player.getHeldItemMainhand() == null || !(player.getHeldItemMainhand().getItem() instanceof IExtendedReach);
//  }
}