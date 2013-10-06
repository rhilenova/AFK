package rhilenova.afk.proxy;

import java.util.List;

import rhilenova.afk.RN_AFK;

public class CommonProxy
{
	public void initAFK(String username)
	{
		RN_AFK.instance.afk_status.put(username, false);
	}
	
	public void toggleAFK(String username)
	{
		Boolean is_afk = false;
		if (RN_AFK.instance.afk_status.containsKey(username))
		{
			is_afk = RN_AFK.instance.afk_status.get(username);
		}
		
		RN_AFK.instance.afk_status.put(username, !is_afk);
	}

	public void removeAFK(String username)
	{
		RN_AFK.instance.afk_status.remove(username);
	}
	
	public void receiveAFKList(List<String> afk_users)
	{
	}
}
