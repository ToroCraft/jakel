package net.torocraft.jakel.spells;

import net.minecraft.inventory.InventoryBasic;

public class SpellDataInventory extends InventoryBasic {

  private final static String NAME = "jakelSpellInventory";

  public SpellDataInventory() {
    super(NAME, false, 2);
  }

}
