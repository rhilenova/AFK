package rhilenova.afk.client.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import rhilenova.afk.RN_AFK;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.EnumChatFormatting;
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
		ScaledResolution res = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int width = res.getScaledWidth();
        FontRenderer fontrenderer = mc.fontRenderer;
        
		ScoreObjective scoreobjective = this.mc.theWorld.getScoreboard().func_96539_a(0);
        NetClientHandler handler = mc.thePlayer.sendQueue;
        
		if (event != null && event.type == RenderGameOverlayEvent.ElementType.PLAYER_LIST && event.isCancelable())
		{
			event.setCanceled(true);
			this.mc.mcProfiler.startSection("playerList");
            List players = handler.playerInfoList;
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
            drawRect(left - 1, border - 1, left + columnWidth * columns, border + 9 * rows, Integer.MIN_VALUE);

            for (int i = 0; i < maxPlayers; i++)
            {
                int xPos = left + i % columns * columnWidth;
                int yPos = border + i / columns * 9;
                drawRect(xPos, yPos, xPos + columnWidth - 1, yPos + 8, 553648127);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_ALPHA_TEST);

                if (i < players.size())
                {
                    GuiPlayerInfo player = (GuiPlayerInfo)players.get(i);
                    ScorePlayerTeam team = mc.theWorld.getScoreboard().getPlayersTeam(player.name);
                    String displayName = player.name;
                    int namePos = xPos;
                    if (RN_AFK.afk_status.containsKey(player.name) && RN_AFK.afk_status.get(player.name))
                    {
                    	displayName = EnumChatFormatting.ITALIC + displayName;
                    	mc.getTextureManager().bindTexture(RN_AFK.afk_icon);
                        this.drawTexturedModalRect(xPos, yPos, 0, 0, 8, 8);
                        namePos += 9;
                    }
                    displayName = ScorePlayerTeam.formatPlayerName(team, displayName);
                    fontrenderer.drawStringWithShadow(displayName, namePos, yPos, 16777215);

                    if (scoreobjective != null)
                    {
                        int endX = xPos + fontrenderer.getStringWidth(displayName) + 5;
                        int maxX = xPos + columnWidth - 12 - 5;

                        if (maxX - endX > 5)
                        {
                            Score score = scoreobjective.getScoreboard().func_96529_a(player.name, scoreobjective);
                            String scoreDisplay = EnumChatFormatting.YELLOW + "" + score.getScorePoints();
                            fontrenderer.drawStringWithShadow(scoreDisplay, maxX - fontrenderer.getStringWidth(scoreDisplay), yPos, 16777215);
                        }
                    }

                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                    mc.getTextureManager().bindTexture(Gui.icons);
                    int pingIndex = 4;
                    int ping = player.responseTime;
                    if (ping < 0) pingIndex = 5;
                    else if (ping < 150) pingIndex = 0;
                    else if (ping < 300) pingIndex = 1;
                    else if (ping < 600) pingIndex = 2;
                    else if (ping < 1000) pingIndex = 3;

                    zLevel += 100.0F;
                    drawTexturedModalRect(xPos + columnWidth - 12, yPos, 0, 176 + pingIndex * 8, 10, 8);
                    zLevel -= 100.0F;
                }
            }
		}
	}
}
