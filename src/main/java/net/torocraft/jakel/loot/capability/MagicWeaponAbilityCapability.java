package net.torocraft.jakel.loot.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.torocraft.jakel.Jakel;

public class MagicWeaponAbilityCapability {
  public static final ResourceLocation NAME = new ResourceLocation(Jakel.MODID, "_Repeater");

  @CapabilityInject(MagicWeaponAbility.class)
  public static final Capability<MagicWeaponAbility> LOOT_CAPABILITY = null;

  public static final EnumFacing DEFAULT_FACING = null;

  public static void register() {
    CapabilityManager.INSTANCE.register(MagicWeaponAbility.class, new Capability.IStorage<MagicWeaponAbility>() {
      @Override
      public NBTBase writeNBT(Capability<MagicWeaponAbility> capability, MagicWeaponAbility instance, EnumFacing side) {
        return new NBTTagInt(instance.getCoolDown());
      }

      @Override
      public void readNBT(Capability<MagicWeaponAbility> capability, MagicWeaponAbility instance, EnumFacing side, NBTBase nbt) {
        instance.setCoolDown(((NBTTagInt) nbt).getInt());
      }
    }, MagicWeaponAbilityImpl::new);
  }

  public static MagicWeaponAbility getCapability(ItemStack itemStack) {
    return itemStack != null && itemStack.hasCapability(LOOT_CAPABILITY, DEFAULT_FACING) ? itemStack.getCapability(LOOT_CAPABILITY, DEFAULT_FACING) : null;
  }

  public static ICapabilityProvider createProvider() {
    return new GenericCapabilityProvider<>(LOOT_CAPABILITY, DEFAULT_FACING);
  }

  public static ICapabilityProvider createProvider(MagicWeaponAbility repeaterData) {
    return new GenericCapabilityProvider(LOOT_CAPABILITY, DEFAULT_FACING, repeaterData);
  }
}
