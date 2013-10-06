package rhilenova.afk.client.gui;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;

import rhilenova.afk.RN_AFK;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

public class GuiAFKPlayerList extends Gui
{
	private Minecraft mc;
	
	public GuiAFKPlayerList(Minecraft minecraft)
	{
		mc = minecraft;
	}
	
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void onRenderTODO(RenderGameOverlayEvent event)
	{
		if (event != null && event.type == RenderGameOverlayEvent.ElementType.ALL && !event.isCancelable())
		{
			ScoreObjective scoreobjective = this.mc.theWorld.getScoreboard().func_96539_a(0);
	        NetClientHandler handler = mc.thePlayer.sendQueue;
	        
			if (mc.gameSettings.keyBindPlayerList.pressed && (!mc.isIntegratedServerRunning() || handler.playerInfoList.size() > 1 || scoreobjective != null))
			{
				@SuppressWarnings("unchecked")
				List<GuiPlayerInfo> players = handler.playerInfoList;
				Iterator<GuiPlayerInfo> iter = players.iterator();
				int i = 0;
				while (iter.hasNext())
				{
					GuiPlayerInfo player = iter.next();
								if(RN_AFK.instance.afk_status.containsKey(player.name) && RN_AFK.instance.afk_status.get(player.name))
					{
						FontRenderer fontrenderer = mc.fontRenderer;
						
						ScaledResolution res = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
						int width = res.getScaledWidth();
			            int maxPlayers = handler.currentServerMaxPlayers;
			            
						int rows = maxPlayers;
			            int columns = 1;

			            for (columns = 1; rows > 20; rows = (maxPlayers + columns - 1) / columns)
			            {
			                columns++;
			            }

			            int columnWidth = 300 / columns;

			            if (columnWidth > 150)
			            {
			                columnWidth = 150;
			            }
			            
			            int left = (width - columns * columnWidth) / 2;

			            byte border = 10;
						int xPos = left + i % columns * columnWidth;
			            int yPos = border + i / columns * 9;
			            
			            fontrenderer.drawStringWithShadow(player.name, xPos, yPos, Color.red.getRGB());
					}
					++i;
				}
			}
		}
	}
}
