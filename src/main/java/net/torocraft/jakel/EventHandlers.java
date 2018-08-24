package net.torocraft.jakel;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.torocraft.jakel.traits.TraitDistributor;
import net.torocraft.jakel.traits.logic.Fireball;
import net.torocraft.jakel.traits.logic.Greedy;

@Mod.EventBusSubscriber(modid = Jakel.MODID)
public class EventHandlers {

  @SubscribeEvent
  public static void handleMagicalWeapon(RightClickItem event) {

    System.out.println("use magical weapon");

    EntityLivingBase entity = event.getEntityLiving();
    World world = entity.world;

    Vec3d lookVector = entity.getLook(1.0F);


    EntityLargeFireball fireball = new EntityLargeFireball(world, entity.posX, entity.posY, entity.posZ, lookVector.x * 2, lookVector.y *2, lookVector.z*2);
    fireball.explosionPower = 2;
    world.spawnEntity(fireball);
    world.playEvent(null, 1016, new BlockPos(entity), 0);
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
