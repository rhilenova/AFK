package rhilenova.afk.proxy;

import java.util.Iterator;
import java.util.List;

import rhilenova.afk.RN_AFK;

public class ClientProxy extends CommonProxy
{
	@Override
	public void receiveAFKList(List<String> afk_users)
	{
		Iterator<String> iter = afk_users.iterator();
		while (iter.hasNext())
		{
			RN_AFK.instance.afk_status.put(iter.next(), true);
		}
	}
}
