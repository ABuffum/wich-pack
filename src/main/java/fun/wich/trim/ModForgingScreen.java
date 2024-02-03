package fun.wich.trim;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public abstract class ModForgingScreen<T extends ModForgingScreenHandler> extends HandledScreen<T> implements ScreenHandlerListener {
	private final Identifier texture;
	public ModForgingScreen(T handler, PlayerInventory playerInventory, Text title, Identifier texture) {
		super(handler, playerInventory, title);
		this.texture = texture;
	}
	protected void setup() { }
	@Override
	protected void init() {
		super.init();
		this.setup();
		this.handler.addListener(this);
	}
	@Override
	public void removed() {
		super.removed();
		this.handler.removeListener(this);
	}
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		RenderSystem.disableBlend();
		this.renderForeground(matrices, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(matrices, mouseX, mouseY);
	}
	protected void renderForeground(MatrixStack matrices, int mouseX, int mouseY, float delta) { }
	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, this.texture);
		this.drawTexture(matrices, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
		this.drawInvalidRecipeArrow(matrices, this.x, this.y);
	}
	protected abstract void drawInvalidRecipeArrow(MatrixStack var1, int var2, int var3);
	@Override
	public void onPropertyUpdate(ScreenHandler handler, int property, int value) { }
	@Override
	public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) { }
}
