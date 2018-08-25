package net.torocraft.jakel.api;

import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.torocraft.jakel.loot.Element;
import net.torocraft.jakel.loot.WeaponAttributes;
import net.torocraft.jakel.loot.WeaponAttributes.AttackType;
import net.torocraft.jakel.loot.modifiers.Data;
import net.torocraft.jakel.loot.modifiers.Type;
import net.torocraft.jakel.nbt.NbtSerializer;

public class EnchantApi {

  public static void enchant(ItemStack item) {

    WeaponAttributes attributes = new WeaponAttributes();
    attributes.damageType = Element.FIRE;
    attributes.type = AttackType.LARGE_FIREBALL;
    attributes.modifiers = new ArrayList<>();
    attributes.modifiers.add(new Data(Type.FIRE, 5));
    attributes.modifiers.add(new Data(Type.FIRE_RESIST, 10));

    NbtSerializer.write(item.getOrCreateSubCompound("jakel"), attributes);
  }

}
