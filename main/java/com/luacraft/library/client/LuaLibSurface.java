package com.luacraft.library.client;

import org.lwjgl.opengl.GL11;

import com.luacraft.LuaCraft;
import com.luacraft.LuaCraftState;
import com.luacraft.classes.Color;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaRuntimeException;
import com.naef.jnlua.LuaState;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class LuaLibSurface {
	private static Minecraft client = LuaCraft.getClient();

	public static ResourceLocation currentTexture = null;
	public static Color drawColor = new Color(255, 255, 255, 255);

	/**
	 * @author Jake
	 * @library surface
	 * @function GetDefaultGalacticFont
	 * @info Gets the default font object that minecraft uses to draw text
	 * @arguments nil
	 * @return [[Font]]:font
	 */

	private static JavaFunction GetDefaultGalacticFont = new JavaFunction() {
		public int invoke(LuaState l) {
			l.pushUserdataWithMeta(client.standardGalacticFontRenderer, "Font");
			return 1;
		}
	};
	/**
	 * @author Jake
	 * @library surface
	 * @function GetDefaultFont
	 * @info Gets the default font object that minecraft uses to draw text
	 * @arguments nil
	 * @return [[Font]]:font
	 */

	private static JavaFunction GetDefaultFont = new JavaFunction() {
		public int invoke(LuaState l) {
			l.pushUserdataWithMeta(client.fontRenderer, "Font");
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @library surface
	 * @function CreateFont
	 * @info Creates a font object that can be used for drawing text to the screen
	 * @arguments [[String]]:fileResource, [ [[Number]]:size, [[Boolean]]:unicode ]
	 * @return [[Font]]:font
	 */

	private static JavaFunction CreateFont = new JavaFunction() {
		public int invoke(LuaState l) {
			ResourceLocation resource = new ResourceLocation(l.checkString(1));

			FontRenderer newFont = new FontRenderer(client.gameSettings, resource, client.getTextureManager(),
					l.checkBoolean(3, false));
			newFont.FONT_HEIGHT = l.checkInteger(2, newFont.FONT_HEIGHT);

			l.pushUserdataWithMeta(newFont, "Font");
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @library surface
	 * @function GetDrawColor
	 * @info Gets the current drawing color
	 * @arguments nil
	 * @return [[Color]]:color
	 */

	private static JavaFunction GetDrawColor = new JavaFunction() {
		public int invoke(LuaState l) {
			l.pushUserdataWithMeta(drawColor, "Color");
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @library surface
	 * @function SetDrawColor
	 * @info Sets the current drawing color
	 * @arguments ( [[Number]]:r, [[Number]]:g, [[Number]]:b, [[Number]]:a ) OR [[Color]]:color
	 * @return nil
	 */

	private static JavaFunction SetDrawColor = new JavaFunction() {
		public int invoke(LuaState l) {
			if (l.isUserdata(1, Color.class))
				drawColor = (Color) l.checkUserdata(1, Color.class, "Color");
			else if (l.isNumber(1)) {
				int r = l.checkInteger(1);
				int g = l.checkInteger(2);
				int b = l.checkInteger(3);
				int a = l.checkInteger(4, 255);
				drawColor = new Color(r, g, b, a);
			} else
				throw new LuaRuntimeException("surface.SetDrawColor: No valid color passed");
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @library surface
	 * @function DrawRect
	 * @info Draws a rectangle
	 * @arguments [[Number]]:xPos, [[Number]]:yPos, [[Number]]:width, [[Number]]:height
	 * @return nil
	 */

	private static JavaFunction DrawRect = new JavaFunction() {
		public int invoke(LuaState l) {
			int x = l.checkInteger(1);
			int y = l.checkInteger(2);
			int w = l.checkInteger(3);
			int h = l.checkInteger(4);
			
			/*Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();
			GlStateManager.enableBlend();
			GlStateManager.disableTexture2D();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.color(drawColor.r / 255, drawColor.g / 255, drawColor.b / 255, drawColor.a / 255);
			bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
			bufferbuilder.pos(x + w, y, 0.0D).endVertex();
			bufferbuilder.pos(x, y, 0.0D).endVertex();
			bufferbuilder.pos(x, y + h, 0.0D).endVertex();
			bufferbuilder.pos(x + w, y + h, 0.0D).endVertex();
			tessellator.draw();
			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();*/
			
			Gui.drawRect(x, y, x + w, y + h, drawColor.getRGBA());
			GlStateManager.enableBlend();
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @library surface
	 * @function DrawGradientRect
	 * @info Draws a rectangle with a gradient
	 * @arguments [[Number]]:xPos, [[Number]]:yPos, [[Number]]:width, [[Number]]:height, [[Color]]:fadeto
	 * @return nil
	 */

	private static JavaFunction DrawGradientRect = new JavaFunction() {
		public int invoke(LuaState l) {
			int x = l.checkInteger(1);
			int y = l.checkInteger(2);
			int w = l.checkInteger(3);
			int h = l.checkInteger(4);

			Color fadeColor = (Color) l.checkUserdata(5, Color.class, "Color");

			GlStateManager.disableTexture2D();
	        GlStateManager.enableBlend();
	        GlStateManager.disableAlpha();
	        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.shadeModel(GL11.GL_SMOOTH);

			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder worldrenderer = tessellator.getBuffer();
			
			worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
			worldrenderer.pos(x + w, y, 0.0D).color(drawColor.r, drawColor.g, drawColor.b, drawColor.a).endVertex();
			worldrenderer.pos(x, y, 0.0D).color(drawColor.r, drawColor.g, drawColor.b, drawColor.a).endVertex();
			worldrenderer.pos(x, y + h, 0.0D).color(fadeColor.r, fadeColor.g, fadeColor.b, fadeColor.a).endVertex();
			worldrenderer.pos(x + w, y + h, 0.0D).color(fadeColor.r, fadeColor.g, fadeColor.b, fadeColor.a).endVertex();
			tessellator.draw();

			GlStateManager.shadeModel(GL11.GL_FLAT);
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @library surface
	 * @function SetTexture
	 * @info Sets a texture ID for to be used when drawing
	 * @arguments [[Resource]]:file
	 * @return nil
	 */

	private static JavaFunction SetTexture = new JavaFunction() {
		public int invoke(LuaState l) {
			currentTexture = (ResourceLocation) l.checkUserdata(1, ResourceLocation.class, "Resource");
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @library surface
	 * @function DrawTexturedRect
	 * @info Draws a textured rectangle
	 * @arguments [[Number]]:xPos, [[Number]]:yPos, [[Number]]:width, [[Number]]:height
	 * @return nil
	 */

	private static JavaFunction DrawTexturedRect = new JavaFunction() {
		public int invoke(LuaState l) {
			if (currentTexture == null)
				return 0;

			int x = l.checkInteger(1);
			int y = l.checkInteger(2);
			int w = l.checkInteger(3);
			int h = l.checkInteger(4);

			GlStateManager.enableTexture2D();
			GlStateManager.enableAlpha();
			
			GlStateManager.color(drawColor.r / 255, drawColor.g / 255, drawColor.b / 255, drawColor.a / 255);

			client.renderEngine.bindTexture(currentTexture);

			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder worldrenderer = tessellator.getBuffer();
			
			worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			worldrenderer.pos(x + w, y, 0.0D).tex(1.D, 0.D).endVertex();
			worldrenderer.pos(x, y, 0.0D).tex(0.D, 0.D).endVertex();
			worldrenderer.pos(x, y + h, 0.0D).tex(0.D, 1.D).endVertex();
			worldrenderer.pos(x + w, y + h, 0.0D).tex(1.D, 1.D).endVertex();
			tessellator.draw();
			
			GlStateManager.enableBlend();
			return 0;
		}
	};
	
	/**
	 * @author Somepotato
	 * @library surface
	 * @function DrawTexturedSubRect
	 * @info Draws a textured (sub) rectangle
	 * @arguments [[Number]]:xPos, [[Number]]:yPos, [[Number]]:width, [[Number]]:height, [[Number]]:u1, [[Number]]:v1, [[Number]]:u2, [[Number]]:v2
	 * @return nil
	 */

	private static JavaFunction DrawTexturedSubRect = new JavaFunction() {
		public int invoke(LuaState l) {
			if (currentTexture == null)
				return 0;

			int x = l.checkInteger(1);
			int y = l.checkInteger(2);
			int w = l.checkInteger(3);
			int h = l.checkInteger(4);
			double u1 = l.checkNumber(3);
			double v1 = l.checkNumber(4);
			double u2 = l.checkNumber(3);
			double v2 = l.checkNumber(4);
			

			GlStateManager.enableTexture2D();
			GlStateManager.enableAlpha();
			
			GlStateManager.color(drawColor.r / 255, drawColor.g / 255, drawColor.b / 255, drawColor.a / 255);

			client.renderEngine.bindTexture(currentTexture);

			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder worldrenderer = tessellator.getBuffer();
			
			worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			worldrenderer.pos(x + w, y, 0.0D).tex(u2, v1).endVertex();
			worldrenderer.pos(x, y, 0.0D).tex(u1, v1).endVertex();
			worldrenderer.pos(x, y + h, 0.0D).tex(u1, v2).endVertex();
			worldrenderer.pos(x + w, y + h, 0.0D).tex(u2, v2).endVertex();
			tessellator.draw();
			
			GlStateManager.enableBlend();
			return 0;
		}
	};

	private static JavaFunction StartPoly = new JavaFunction() {
		public int invoke(LuaState l) {

			return 0;
		}
	};

	public static void Init(final LuaCraftState l) {
		l.newTable();
		{
			l.pushJavaFunction(GetDefaultFont);
			l.setField(-2, "GetDefaultFont");
			l.pushJavaFunction(CreateFont);
			l.setField(-2, "CreateFont");
			l.pushJavaFunction(GetDrawColor);
			l.setField(-2, "GetDrawColor");
			l.pushJavaFunction(SetDrawColor);
			l.setField(-2, "SetDrawColor");
			l.pushJavaFunction(DrawRect);
			l.setField(-2, "DrawRect");
			l.pushJavaFunction(DrawGradientRect);
			l.setField(-2, "DrawGradientRect");
			l.pushJavaFunction(SetTexture);
			l.setField(-2, "SetTexture");
			l.pushJavaFunction(DrawTexturedRect);
			l.setField(-2, "DrawTexturedRect");
			l.pushJavaFunction(DrawTexturedSubRect);
			l.setField(-2, "DrawTexturedSubRect");
		}
		l.setGlobal("surface");
	}
}
