package hu.hundevelopers.elysium.client.gui;

import cpw.mods.fml.client.GuiModList;
import cpw.mods.fml.common.registry.LanguageRegistry;
import hu.hundevelopers.elysium.Elysium;
import hu.hundevelopers.elysium.render.RenderingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.util.ResourceLocation;
import static org.lwjgl.opengl.GL11.*;

public class ElysiumGuiMainMenu extends ElysiumGui {
	public ResourceLocation imgBackground = new ResourceLocation(Elysium.MODID + ":textures/gui/menu/bg.png");
	public ResourceLocation imgTitle = new ResourceLocation(Elysium.MODID + ":textures/gui/menu/title.png");
	public ElysiumGuiButton btnSingle, btnMulti, btnMods, btnOptions, btnQuit;
	public ElysiumGui subGui;

	public int margin;
	public int titleX, titleY, titleWidth, titleHeight;
	public int menuX, menuY, menuWidth;
	public int subX, subY, subWidth, subHeight;
	public int btnHeight;

	public ElysiumGuiMainMenu(ElysiumGui parent) {
		super(parent);
	}

	public void setSubGui(ElysiumGui gui) {
		if (this.subGui != null)
			this.subGui.close();
		this.subGui = gui;
	}

	@Override
	public void onMouseClicked(int x, int y, int btn) {
		super.onMouseClicked(x, y, btn);
		if (this.subGui != null)
			this.subGui.onMouseClicked(x, y, btn);
	}

	@Override
	public void onKeyTyped(char c, int i) {
		super.onKeyTyped(c, i);
		if (this.subGui != null)
			this.subGui.onKeyTyped(c, i);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);

		this.margin = this.height / 100;
		this.titleX = this.width / 4;
		this.titleY = this.margin;
		this.titleWidth = this.width / 2;
		this.titleHeight = this.titleWidth / 5;
		this.menuX = this.margin;
		this.menuY = this.titleY + this.titleHeight + this.margin;
		this.menuWidth = this.titleX - this.menuX - this.margin;
		this.subX = this.titleX;
		this.subY = this.menuY;
		this.subWidth = this.titleWidth;
		this.subHeight = this.height - this.subY - this.margin;
		this.btnHeight = this.menuWidth / 5;

		this.btnSingle = new ElysiumGuiButton(this, this.menuX, this.menuY, this.menuWidth, this.btnHeight, LanguageRegistry.instance()
				.getStringLocalization("menu.singleplayer"));
		this.subs.add(this.btnSingle);
		this.btnMulti = new ElysiumGuiButton(this, this.menuX, this.menuY + this.btnHeight + this.margin, this.menuWidth, this.btnHeight,
				LanguageRegistry.instance().getStringLocalization("menu.multiplayer"));
		this.subs.add(this.btnMulti);
		this.btnMods = new ElysiumGuiButton(this, this.menuX, this.menuY + (this.btnHeight + this.margin) * 2, this.menuWidth, this.btnHeight, "Mods");
		this.subs.add(this.btnMods);
		this.btnOptions = new ElysiumGuiButton(this, this.menuX, this.menuY + (this.btnHeight + this.margin) * 3, this.menuWidth, this.btnHeight,
				LanguageRegistry.instance().getStringLocalization("menu.options"));
		this.subs.add(this.btnOptions);
		this.btnQuit = new ElysiumGuiButton(this, this.menuX, this.menuY + this.subHeight - this.btnHeight, this.menuWidth, this.btnHeight,
				LanguageRegistry.instance().getStringLocalization("menu.quit"));
		this.subs.add(this.btnQuit);
		
		if(this.subGui != null)
			this.subGui.setBounds(this.subX, this.subY, this.subWidth, this.subHeight);
	}

	@Override
	public void update(int mouseX, int mouseY, boolean pressed) {
		super.update(mouseX, mouseY, pressed);
		if (this.subGui != null)
			this.subGui.update(mouseX, mouseY, pressed);

		if (this.btnSingle.wasClicked())
			this.setSubGui(new ElysiumGuiSelectWorld(this, this.subX, this.subY, this.subWidth, this.subHeight));
		if (this.btnMulti.wasClicked())
			this.setSubGui(new ElysiumGuiMultiplayer(this, this.subX, this.subY, this.subWidth, this.subHeight));
		if (this.btnMods.wasClicked())
			Minecraft.getMinecraft().displayGuiScreen(new GuiModList(ElysiumGuiEmulator.instance));
		if (this.btnOptions.wasClicked())
			Minecraft.getMinecraft().displayGuiScreen(new GuiOptions(ElysiumGuiEmulator.instance, Minecraft.getMinecraft().gameSettings));
		if (this.btnQuit.wasClicked())
			Minecraft.getMinecraft().shutdown();
	}

	@Override
	public void render(float partialTick) {
		RenderingHelper.setColor(1F, 1F, 1F, 1F);
		RenderingHelper.bindTexture(this.imgBackground);
		RenderingHelper.Gui.drawTexturedRect(0, 0, this.width, this.height, 0F, 0F, 1F, 1F);
		RenderingHelper.bindTexture(this.imgTitle);
		RenderingHelper.Gui.drawTexturedRect(this.titleX, this.titleY, this.titleWidth, this.titleHeight, 0F, 0F, 1F, 1F);
		RenderingHelper.setColor(0F, 0F, 0F, 0.75F);
		super.render(partialTick);
		if (this.subGui != null)
			this.subGui.render(partialTick);
	}

	@Override
	public void close() {
		super.close();
		if (this.subGui != null)
			this.subGui.close();
	}
}
