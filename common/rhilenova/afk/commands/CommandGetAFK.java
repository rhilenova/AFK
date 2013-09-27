package rhilenova.afk.commands;
import rhilenova.afk.network.PacketHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CommandGetAFK extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "get_afk";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "/get_afk";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring)
	{
		PacketHandler.requestAFK();
	}
}
