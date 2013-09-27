package rhilenova.afk.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;
import java.net.SocketAddress;
import java.util.HashMap;

import rhilenova.afk.RN_AFK;

public class AFKConnectionHandler implements IConnectionHandler
{
	HashMap<SocketAddress, String> clients;
	
	public AFKConnectionHandler()
	{
		clients = new HashMap<SocketAddress, String>();
	}
	
	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler,
			INetworkManager manager)
	{
		EntityPlayerMP player_mp = (EntityPlayerMP) player;
		String username = player_mp.username;
		//System.out.println("----------" + username + " logged in.----------");
		clients.put(manager.getSocketAddress(), username);
		RN_AFK.initAFK(username);
	}

	@Override
	public String connectionReceived(NetLoginHandler netHandler,
			INetworkManager manager)
	{
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server,
			int port, INetworkManager manager)
	{
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler,
			MinecraftServer server, INetworkManager manager)
	{
	}

	@Override
	public void connectionClosed(INetworkManager manager)
	{
		SocketAddress user_address = manager.getSocketAddress();
		if (clients.containsKey(user_address))
		{
			//System.out.println("----------" + clients.get(user_address) + " disconnected.----------");
			RN_AFK.removeAFK(clients.get(user_address));
			clients.remove(user_address);
		}
	}

	@Override
	public void clientLoggedIn(NetHandler clientHandler,
			INetworkManager manager, Packet1Login login)
	{
	}
}
