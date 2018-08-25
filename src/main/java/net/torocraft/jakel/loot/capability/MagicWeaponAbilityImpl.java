package net.torocraft.jakel.loot.capability;

public class MagicWeaponAbilityImpl implements MagicWeaponAbility {

  private int coolDown;

  @Override
  public int getCoolDown() {
    return coolDown;
  }

  @Override
  public void setCoolDown(int coolDown) {
    this.coolDown = coolDown;
  }
}
