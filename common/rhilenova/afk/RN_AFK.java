package rhilenova.afk;

import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import rhilenova.afk.Reference;
import rhilenova.afk.client.gui.GuiAFKPlayerList;
import rhilenova.afk.commands.CommandAFK;
import rhilenova.afk.network.AFKConnectionHandler;
import rhilenova.afk.network.PacketHandler;
import rhilenova.afk.proxy.CommonProxy;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER)
@NetworkMod(channels = {Reference.MOD_CHANNEL}, clientSideRequired = true, serverSideRequired = true, packetHandler = PacketHandler.class)
public class RN_AFK
{
	@Instance(Reference.MOD_ID)
    public static RN_AFK instance;
	
	public static HashMap<String, Boolean> afk_status =
		new HashMap<String, Boolean>();
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
	
	@EventHandler
    public void load(FMLInitializationEvent event)
    {
		NetworkRegistry.instance().registerConnectionHandler(new AFKConnectionHandler());
    }
	
	@EventHandler
    public void load(FMLPostInitializationEvent event)
	{
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {
        	MinecraftForge.EVENT_BUS.register(new GuiAFKPlayerList(Minecraft.getMinecraft()));
        }
	}
	
	@EventHandler
	public void serverStart(FMLServerStartingEvent event)
	{
		ServerCommandManager manager = (ServerCommandManager)MinecraftServer.getServer().getCommandManager();
		manager.registerCommand(new CommandAFK());
	}

	public static void initAFK(String username)
	{
		afk_status.put(username, false);
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			PacketHandler.sendAFK(username, PacketHandler.INIT_AFK);
		}
	}
	
	public static void toggleAFK(String username)
	{
		Boolean is_afk = false;
		if (afk_status.containsKey(username)) is_afk = afk_status.get(username);
		
		afk_status.put(username, !is_afk);
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			PacketHandler.sendAFK(username, PacketHandler.TOGGLE_AFK);
		}
	}

	public static void removeAFK(String username)
	{
		afk_status.remove(username);
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			PacketHandler.sendAFK(username, PacketHandler.REMOVE_AFK);
		}
	}
}
