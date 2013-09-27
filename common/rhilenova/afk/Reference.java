package rhilenova.afk;

public class Reference
{
	public static final String MOD_ID = "rn_afk";
	public static final String MOD_NAME = "AFK";
	public static final String MOD_CHANNEL = "rn_afk";
	public static final String VERSION_NUMBER = "0.1";
	
	public static final String CLIENT_PROXY_CLASS = "rhilenova.afk.proxy.CommonProxy";
	public static final String SERVER_PROXY_CLASS = "rhilenova.afk.proxy.CommonProxy";
	
	public static final int INIT_AFK = 0;
	public static final int TOGGLE_AFK = 1;
	public static final int REQUEST_AFK = 2;
	public static final int AFK_RESULTS = 3;
}
