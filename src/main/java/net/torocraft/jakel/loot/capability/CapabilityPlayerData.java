package net.torocraft.jakel.loot.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.torocraft.jakel.Jakel;
import net.torocraft.jakel.nbt.NbtSerializer;

@Mod.EventBusSubscriber
public class CapabilityPlayerData implements INBTSerializable<NBTTagCompound> {

  private static final ResourceLocation RESOURCE = new ResourceLocation(Jakel.MODID, "CapabilityPlayerData");

  @CapabilityInject(CapabilityPlayerData.class)
  private static final Capability<CapabilityPlayerData> PLAYER_DATA_CAPABILITY = null;

  private final PlayerData data;

  public CapabilityPlayerData() {
    this(null);
  }

  public CapabilityPlayerData(EntityPlayer player) {
    this.data = new PlayerData(player);
  }

  public static CapabilityPlayerData get(EntityPlayer player) {
    return player.getCapability(PLAYER_DATA_CAPABILITY, null);
  }

  @Override
  public NBTTagCompound serializeNBT() {
    NBTTagCompound c = new NBTTagCompound();
    NbtSerializer.write(c, data);
    return c;
  }

  @Override
  public void deserializeNBT(NBTTagCompound c) {
    NbtSerializer.read(c, data);
  }

  @SubscribeEvent
  public static void onCapabilityLoad(AttachCapabilitiesEvent<Entity> event) {
    if (event.getObject() instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer) event.getObject();
      event.addCapability(RESOURCE, new Provider(player));
    }
  }

  @SubscribeEvent
  public static void onPlayerCloneEvent(PlayerEvent.Clone event) {
    CapabilityPlayerData newData = CapabilityPlayerData.get(event.getEntityPlayer());
    CapabilityPlayerData oldData = CapabilityPlayerData.get(event.getOriginal());
    newData.deserializeNBT(oldData.serializeNBT());
  }

  @SubscribeEvent
  public static void onLivingUpdateEvent(LivingUpdateEvent event) {
    if (!(event.getEntityLiving() instanceof EntityPlayer)) {
      return;
    }
    EntityPlayer player = (EntityPlayer) event.getEntityLiving();
    if (get(player) != null) {
      CapabilityPlayerData.get(player).data.update(event);
    }
  }

  public static class Provider implements ICapabilitySerializable<NBTTagCompound> {

    private final CapabilityPlayerData data;

    public Provider(EntityPlayer player) {
      data = new CapabilityPlayerData(player);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
      return capability == PLAYER_DATA_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
      if (capability == PLAYER_DATA_CAPABILITY) {
        return PLAYER_DATA_CAPABILITY.cast(data);
      }
      return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
      return data.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
      data.deserializeNBT(nbt);
    }

  }

}
