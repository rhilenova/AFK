package rhilenova.afk.proxy;

import rhilenova.afk.network.PacketHandler;

public class ServerProxy extends CommonProxy
{
	@Override
	public void initAFK(String username)
	{
		super.initAFK(username);
		PacketHandler.sendAFK(username, PacketHandler.INIT_AFK);
	}
	
	@Override
	public void toggleAFK(String username)
	{
		super.toggleAFK(username);
		PacketHandler.sendAFK(username, PacketHandler.TOGGLE_AFK);
	}
	
	@Override
	public void removeAFK(String username)
	{
		super.removeAFK(username);
		PacketHandler.sendAFK(username, PacketHandler.REMOVE_AFK);
	}
}
