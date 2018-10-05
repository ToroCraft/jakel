package net.torocraft.jakel.util;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.torocraft.jakel.loot.Element;

public class MagicDamageSource extends EntityDamageSource {

  private final Entity indirectEntity;
  private final Element element;

  public MagicDamageSource(Element element, Entity source, @Nullable Entity indirectEntityIn) {
    super(element.toString().toLowerCase() + "_magic", source);
    this.indirectEntity = indirectEntityIn;
    this.element = element;
  }

  @Nullable
  public Entity getImmediateSource() {
    return this.damageSourceEntity;
  }

  @Nullable
  public Entity getTrueSource() {
    return this.indirectEntity;
  }

  public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
    ITextComponent itextcomponent = this.indirectEntity == null ? this.damageSourceEntity.getDisplayName() : this.indirectEntity.getDisplayName();
    ItemStack itemstack = this.indirectEntity instanceof EntityLivingBase ? ((EntityLivingBase) this.indirectEntity).getHeldItemMainhand() : ItemStack.EMPTY;
    String s = "death.attack." + this.damageType;
    String s1 = s + ".item";
    return !itemstack.isEmpty() && itemstack.hasDisplayName() && I18n.canTranslate(s1) ? new TextComponentTranslation(s1,
        entityLivingBaseIn.getDisplayName(), itextcomponent, itemstack.getTextComponent())
        : new TextComponentTranslation(s, entityLivingBaseIn.getDisplayName(), itextcomponent);
  }

  public Element getElement() {
    return element;
  }
}