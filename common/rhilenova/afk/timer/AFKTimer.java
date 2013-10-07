package rhilenova.afk.timer;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

import rhilenova.afk.RN_AFK;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class AFKTimer implements IScheduledTickHandler
{
    private static AFKTimer instance;
    
    private int timer;
    private Minecraft minecraft;
    //private int timer_init = 120; // TODO from config file. Currently set to 2 minutes.
    private int timer_init = 10;
    
	public AFKTimer(Minecraft minecraft)
	{
		this.minecraft = minecraft;
		AFKTimer.instance = this;
		TickRegistry.registerScheduledTickHandler(this, Side.CLIENT);
		
		timer = timer_init;
		System.out.println("Starting");
	}
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		if (AFKTimer.instance.minecraft.theWorld != null && timer > 0)
		{
			
			--timer;
			System.out.println("Ticking... " + timer);
			
			if (timer == 0)
			{
				RN_AFK.proxy.toggleAFK(Minecraft.getMinecraft().thePlayer.username);
				System.out.println("Idle!");
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
	}

	@Override
	public EnumSet<TickType> ticks()
	{
        return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel()
	{
        return "AFKTimer";
	}

	@Override
	public int nextTickSpacing()
	{
		// TODO tick slower?
		return 20;
	}
	
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void onMouseEvent(MouseEvent event)
	{
		System.out.println("Resetting");
		if (timer == 0)
		{
			RN_AFK.proxy.toggleAFK(Minecraft.getMinecraft().thePlayer.username);
		}
		timer = timer_init;
	}
}
