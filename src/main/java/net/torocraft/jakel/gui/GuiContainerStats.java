package net.torocraft.jakel.gui;

import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.torocraft.jakel.Jakel;
import net.torocraft.jakel.capabilites.CapabilityPlayerData;
import net.torocraft.jakel.nbt.NbtField;
import net.torocraft.jakel.stats.Stats;
import org.lwjgl.input.Keyboard;

public class GuiContainerStats extends GuiContainer {

  public static final ResourceLocation BACKGROUND = new ResourceLocation(Jakel.MODID, "textures/gui/stats.png");

  private static final int GUI_WIDTH = 176;
  private static final int GUI_HEIGHT = 166;
  private static int STAT_COLOR = Color.darkGray.getRGB();
  private int contentLeft;
  private int contentTop;

  int labelLeft;
  int labelTop;
  int valueLeft;

  private Stats stats;

  private int guiX = 0;
  private int guiY = 0;

  private GuiScrollPane pane = new GuiScrollPane() {
    @Override
    protected void drawPane(int mouseX, int mouseY, float partialTicks) {
      drawTextContent();
    }
  };

  public GuiContainerStats(EntityPlayer player, World world) {
    super(new ContainerStats(player, world));
    stats = CapabilityPlayerData.get(player).stats;
  }

  @Override
  public void initGui() {
    super.initGui();
    xSize = GUI_WIDTH;
    ySize = GUI_HEIGHT;

    contentLeft = guiLeft + 29;
    contentTop = guiTop + 7;

    labelLeft = contentLeft + 30;
    valueLeft = contentLeft + 4;
    labelTop = contentTop + 1;

    pane.setHidden(false);

    int paneHeight = 134;
    pane.updateRect(contentLeft, contentTop, 140, paneHeight);
    pane.setHeight(0);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    GlStateManager.color(1, 1, 1, 1);
    Minecraft.getMinecraft().getTextureManager().bindTexture(BACKGROUND);
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    this.pane.drawScreen(mouseX, mouseY, partialTicks);
  }

  private void drawStat(String label, float value, int y) {
    fontRenderer.drawString(value + "", valueLeft, y + labelTop, 0);
    fontRenderer.drawString(label, labelLeft, y + labelTop, STAT_COLOR);
  }

  private void drawTextContent() {
    if (stats == null) {
      return;
    }

    int y = 0;
    int lh = 10;

    drawStat("gui.stats.manaMaximum", stats.manaMaximum, y);
    y += lh;
    drawStat("gui.stats.manaPerTick", stats.manaPerTick, y);
    y += lh;
    drawStat("gui.stats.cooldownPerTick", stats.cooldownPerTick, y);
    y += lh;
    drawStat("gui.stats.lifePerHit", stats.lifePerHit, y);
    y += lh;
    drawStat("gui.stats.lifePerKill", stats.lifePerKill, y);
    y += lh;
    drawStat("gui.stats.lifePerTick", stats.lifePerTick, y);
    y += lh;
    drawStat("gui.stats.criticalHitDamage", stats.criticalHitDamage, y);
    y += lh;
    drawStat("gui.stats.criticalHitChance", stats.criticalHitChance, y);

    /*
     * Special Damage
     */
    y += lh;
    drawStat("gui.stats.thorns", stats.thorns, y);
    y += lh;
    drawStat("gui.stats.areaDamage", stats.areaDamage, y);

    /*
     * Damage Base
     */
    y += lh;
    drawStat("gui.stats.damage", stats.damage, y);
    y += lh;
    drawStat("gui.stats.fire", stats.fire, y);
    y += lh;
    drawStat("gui.stats.lightning", stats.lightning, y);
    y += lh;
    drawStat("gui.stats.wither", stats.wither, y);
    y += lh;
    drawStat("gui.stats.cold", stats.cold, y);
    y += lh;
    drawStat("gui.stats.poison", stats.poison, y);

    /*
     * Damage Buffs
     */
    y += lh;
    drawStat("gui.stats.fireMultiplier", stats.fireMultiplier, y);
    y += lh;
    drawStat("gui.stats.lightningMultiplier", stats.lightningMultiplier, y);
    y += lh;
    drawStat("gui.stats.witherMultiplier", stats.witherMultiplier, y);
    y += lh;
    drawStat("gui.stats.coldMultiplier", stats.coldMultiplier, y);
    y += lh;
    drawStat("gui.stats.poisonMultiplier", stats.poisonMultiplier, y);
    
    /*
     * Resistance Buffs
     */
    y += lh;
    drawStat("gui.stats.defense", stats.defense, y);
    y += lh;
    drawStat("gui.stats.fireResist", stats.fireResist, y);
    y += lh;
    drawStat("gui.stats.lightningResist", stats.lightningResist, y);
    y += lh;
    drawStat("gui.stats.witherResist", stats.witherResist, y);
    y += lh;
    drawStat("gui.stats.coldResist", stats.coldResist, y);

    pane.setHeight(y);
  }

  private String i18n(String unlocalizedName) {
    return I18n.format(unlocalizedName);
  }

  @Override
  public void handleMouseInput() throws IOException {
    pane.handleMouseInput();
    super.handleMouseInput();
  }

  @Override
  public void setWorldAndResolution(Minecraft mc, int width, int height) {
    super.setWorldAndResolution(mc, width, height);
    pane.setWorldAndResolution(mc, width, height);
  }


  @Override
  protected void keyTyped(char typedChar, int keyCode) throws IOException {
    super.keyTyped(typedChar, keyCode);
    System.out.println("key typed " + keyCode);
    if (keyCode == Keyboard.KEY_DOWN) {
      System.out.println("scroll down");
      pane.scrollBy(30);
    } else if (keyCode == Keyboard.KEY_UP) {
      pane.scrollBy(-30);
      System.out.println("scroll up");
    } else if (keyCode == Keyboard.KEY_LEFT) {
      pane.scrollTo(0);
    } else if (keyCode == Keyboard.KEY_RIGHT) {
      pane.scrollTo(pane.getHeight());
    }

  }

}