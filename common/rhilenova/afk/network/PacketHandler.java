package rhilenova.afk.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import rhilenova.afk.RN_AFK;
import rhilenova.afk.Reference;

public class PacketHandler implements IPacketHandler
{
	public static final int INIT_AFK = 0;
	public static final int TOGGLE_AFK = 1;
	public static final int REMOVE_AFK = 2;
	
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
                String username = dis.readUTF();
                
                if (type == INIT_AFK) RN_AFK.initAFK(username);
                else if (type == TOGGLE_AFK) RN_AFK.toggleAFK(username);
                else if (type == REMOVE_AFK) RN_AFK.removeAFK(username);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
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
		
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			PacketDispatcher.sendPacketToAllPlayers(packet);
		}
		else
		{
			PacketDispatcher.sendPacketToServer(packet);
		}
	}
}
