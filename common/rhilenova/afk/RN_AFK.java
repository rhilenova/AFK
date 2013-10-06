package rhilenova.afk;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
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
	
	public HashMap<String, Boolean> afk_status = new HashMap<String, Boolean>();
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
	
	@Init
    public void load(FMLInitializationEvent event)
    {
		NetworkRegistry.instance().registerConnectionHandler(new AFKConnectionHandler());
    }
	
	@PostInit
    public void load(FMLPostInitializationEvent event)
	{
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {
        	MinecraftForge.EVENT_BUS.register(new GuiAFKPlayerList(Minecraft.getMinecraft()));
        }
	}
	
	@ServerStarting
	public void serverStart(FMLServerStartingEvent event)
	{
		ServerCommandManager manager = (ServerCommandManager)MinecraftServer.getServer().getCommandManager();
		manager.registerCommand(new CommandAFK());
	}
}
