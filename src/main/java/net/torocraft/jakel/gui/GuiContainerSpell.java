package net.torocraft.jakel.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.torocraft.jakel.Jakel;
import net.torocraft.jakel.spells.SpellData;

public class GuiContainerSpell extends GuiContainer {

  public static final ResourceLocation LOCATION_BOOK_CODE_BACKGROUND = new ResourceLocation(Jakel.MODID, "textures/gui/spell.png");

  private static final int GUI_WIDTH = 148;
  private static final int GUI_HEIGHT = 100;

  private int guiX = 0;
  private int guiY = 0;

  //private final IInventory inventory;

  public GuiContainerSpell() {
    this(null, null, null);
  }

  @Override
  public void initGui() {
    super.initGui();
    guiX = (width - GUI_WIDTH) / 2;
    guiY = 2;
  }

  public GuiContainerSpell(EntityPlayer player, SpellData spell, World world) {
    super(new ContainerSpell(player, spell.inventory, world));
    //this.inventory = spell.inventory;
//		xSize = 176;
//		ySize = 239;
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
//		Minecraft.getMinecraft().getTextureManager().bindTexture(guiTexture);
//		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
//		drawDonateButton(mouseX, mouseY);
//
//		if (questAccepted) {
//			drawAbandonButton(mouseX, mouseY);
//			drawCompleteButton(mouseX, mouseY);
//		} else {
//			drawAcceptButton(mouseX, mouseY);
//		}
    GlStateManager.color(1, 1, 1, 1);
    Minecraft.getMinecraft().getTextureManager().bindTexture(LOCATION_BOOK_CODE_BACKGROUND);
    drawTexturedModalRect(guiX, guiY, 0, 0, GUI_WIDTH, GUI_HEIGHT);
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    super.drawGuiContainerForegroundLayer(mouseX, mouseY);
//		final int LABEL_XPOS = 5;
//		final int LABEL_YPOS = 5;
//		drawGuiTitle(LABEL_XPOS, LABEL_YPOS);
//		updateReputationDisplay(LABEL_XPOS, LABEL_YPOS);
//		drawQuestTitle(LABEL_XPOS, LABEL_YPOS);
  }

}