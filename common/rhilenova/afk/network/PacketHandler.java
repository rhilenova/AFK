package rhilenova.afk.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import rhilenova.afk.RN_AFK;
import rhilenova.afk.Reference;

public class PacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player)
	{
		if (packet.channel.equals(Reference.MOD_CHANNEL))
        {
            ByteArrayInputStream bis = new ByteArrayInputStream(packet.data);
            DataInputStream dis = new DataInputStream(bis);

            try
            {
                int type = dis.readInt();
                
                if (type < Reference.REQUEST_AFK)
                {
                    String username = dis.readUTF();
	                System.out.println("Got: " + type + " for: " + username);
	                
	                if (type == Reference.REQUEST_AFK)
	                {
	                    RN_AFK.toggleAFK(username);
	                }
	                else if(type == Reference.INIT_AFK)
	                {
	                    RN_AFK.initAFK(username);
	                }                
                }
                else if(type == Reference.AFK_RESULTS)
                {
                	int num_users = dis.readInt();
                	for (int x = 0; x < num_users; ++x)
                	{
                		System.out.println(dis.readUTF());
                	}
                }
                else
                {
                	sendAFKResults();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
	}
	
	public static void initAFK(String username)
	{
		sendAFK(username, Reference.INIT_AFK);
	}
	
	public static void toggleAFK(String username)
	{
		sendAFK(username, Reference.TOGGLE_AFK);
	}
	
	public static void sendAFK(String username, int type)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(username.getBytes().length);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		try
		{
			outputStream.writeInt(type);
			outputStream.writeUTF(username);
		}
		catch (Exception ex)
		{
	        ex.printStackTrace();
		}
        
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = Reference.MOD_CHANNEL;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		PacketDispatcher.sendPacketToServer(packet);
	}

	public static void requestAFK()
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(4);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		try
		{
			outputStream.writeInt(Reference.REQUEST_AFK);
		}
		catch (Exception ex)
		{
	        ex.printStackTrace();
		}
        
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = Reference.MOD_CHANNEL;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		PacketDispatcher.sendPacketToServer(packet);
	}
	
	private void sendAFKResults()
	{
		ArrayList<String> usernames = RN_AFK.getAFKResults();
		int size = 0;
		
		Iterator<String> iter = usernames.iterator();
		while(iter.hasNext())
		{
			String username = iter.next();
			size += username.getBytes().length;
		}
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		try
		{

			outputStream.writeInt(Reference.AFK_RESULTS);
			outputStream.writeInt(usernames.size());
			
			iter = usernames.iterator();
			while(iter.hasNext())
			{
				outputStream.writeUTF(iter.next());
			}
		}
		catch (Exception ex)
		{
	        ex.printStackTrace();
		}
        
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = Reference.MOD_CHANNEL;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		PacketDispatcher.sendPacketToServer(packet);
	}
}
