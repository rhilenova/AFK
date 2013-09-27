package rhilenova.afk.commands;
import rhilenova.afk.RN_AFK;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class CommandAFK extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "afk";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "/afk";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring)
	{
		if(icommandsender instanceof EntityPlayer)
        {
                EntityPlayer player = (EntityPlayer)icommandsender;
                RN_AFK.toggleAFK(player.username);
        }
	}
}
