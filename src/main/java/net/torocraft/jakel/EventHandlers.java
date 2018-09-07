package net.torocraft.jakel;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.torocraft.jakel.api.AttackApi;
import net.torocraft.jakel.capabilites.CapabilityPlayerData;
import net.torocraft.jakel.loot.Stats;
import net.torocraft.jakel.traits.TraitDistributor;
import net.torocraft.jakel.traits.logic.Greedy;

@Mod.EventBusSubscriber(modid = Jakel.MODID)
public class EventHandlers {

  //RightClickBlock} or entity {@link EntityInteract} {@link EntityInteractSpecific

//
//  @SubscribeEvent
//  public static void handleMagicalWeapon(RightClickItem event) {
//    handleMagicalWeapon(event, event.getItemStack());
//  }

  @SubscribeEvent
  public static void handleMagicalWeapon(PlayerInteractEvent event) {
    handleMagicalWeapon(event, event.getItemStack());
  }

  private static void handleMagicalWeapon(LivingEvent event, ItemStack stack) {

    if (event.getEntityLiving().world.isRemote) {
      return;
    }

    if (!isMagicalWeapon(stack)) {
      return;
    }

    System.out.println("**** event: " + event.getClass().getName());

    //ItemStack stack = event.getItemStack();

    // get magical skill

    // check cooldown / cost

    // reset cooldown or subtract mana

    EntityLivingBase entity = event.getEntityLiving();
    World world = entity.world;

    if (entity instanceof EntityPlayer) {
      Stats stats = CapabilityPlayerData.get((EntityPlayer) entity).stats;

      ((EntityPlayer) entity).getCooldownTracker().setCooldown(stack.getItem(), 20);

    }

    event.setCanceled(true);

    AttackApi.largeFireBall(world, AttackApi.inFrontOf(entity), entity.getLookVec().scale(50), 1);
  }

  private static boolean isMagicalWeapon(ItemStack stack) {
    if (stack == null || stack.isEmpty()) {
      return false;
    }
    // TODO check cap, must be a magical conduit

    //CapabilityItemData.get(stack).isMagicalConduit;

    return true;
  }


  @SubscribeEvent
  public static void livingUpdate(LivingUpdateEvent event) {
    if (event.getEntity().world.getTotalWorldTime() % 20 != 0) {
      return;
    }

    if (!(event.getEntity() instanceof EntityCreature)) {
      return;
    }

    EntityCreature entity = (EntityCreature) event.getEntity();

    if (entity.getTags().contains(Jakel.TAG_HAS_TRAIT)) {
      Greedy.decrementCooldown(entity);
      TraitDistributor.distribute(event, entity, TraitDistributor::onUpdate);
    }
  }

  @SubscribeEvent
  public static void onDrop(LivingDropsEvent event) {
    if (!(event.getEntity() instanceof EntityCreature)) {
      return;
    }

    if (event.getEntity().getTags().contains(Jakel.TAG_HAS_TRAIT)) {
      EntityCreature entity = (EntityCreature) event.getEntity();
      TraitDistributor.distribute(event, entity, TraitDistributor::onDrop);
    }
  }

  @SubscribeEvent
  public static void onHurtOrAttack(LivingHurtEvent event) {
    handleOnHurtVictim(event);
    handleOnHurtAttacker(event);
  }

  private static void handleOnHurtVictim(LivingHurtEvent event) {
    EntityCreature victim = getVictimWithTraits(event);
    if (victim != null) {
      TraitDistributor.distribute(event, victim, TraitDistributor::onHurt);
    }
  }

  private static void handleOnHurtAttacker(LivingHurtEvent event) {
    EntityCreature attacker = getAttacker(event);
    if (attacker != null) {
      TraitDistributor.distribute(event, attacker, TraitDistributor::onAttack);
    }
  }

  public static EntityLivingBase getVictim(EntityEvent event) {
    Entity entity = event.getEntity();
    if (entity != null && entity instanceof EntityLivingBase) {
      return (EntityLivingBase) entity;
    }
    return null;
  }

  private static EntityCreature getVictimWithTraits(EntityEvent event) {
    Entity entity = event.getEntity();
    if (entity != null && entity instanceof EntityCreature && hasTraits(entity)) {
      return (EntityCreature) entity;
    }
    return null;
  }

  private static EntityCreature getAttacker(LivingHurtEvent event) {
    if (event.getSource() == null || event.getSource().getTrueSource() == null) {
      return null;
    }
    Entity entity = event.getSource().getTrueSource();
    if (entity instanceof EntityCreature && hasTraits(entity)) {
      return (EntityCreature) entity;
    }
    return null;
  }

  private static boolean hasTraits(Entity entity) {
    return entity.getTags().contains(Jakel.TAG_HAS_TRAIT);
  }

}
