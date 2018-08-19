package net.minewars.lobby.scoreboard;

import net.minewars.lobby.main.Lobby;

public class GroupManager {

	private static GroupManager gm;
	
	private String admin = Lobby.getInstance().getConfig().getString("Tab.Admin.Prefix");
	private String srdev = Lobby.getInstance().getConfig().getString("Tab.SrDev.Prefix");
	private String dev = Lobby.getInstance().getConfig().getString("Tab.Dev.Prefix");
	private String content = Lobby.getInstance().getConfig().getString("Tab.Content.Prefix");
	private String srmod = Lobby.getInstance().getConfig().getString("Tab.SrMod.Prefix");
	private String mod = Lobby.getInstance().getConfig().getString("Tab.Mod.Prefix");
	private String sup = Lobby.getInstance().getConfig().getString("Tab.Sup.Prefix");
	private String hbuilder = Lobby.getInstance().getConfig().getString("Tab.HeadBuilder.Prefix");
	private String builder = Lobby.getInstance().getConfig().getString("Tab.Builder.Prefix");
	private String yt = Lobby.getInstance().getConfig().getString("Tab.YouTuber.Prefix");
	private String premi = Lobby.getInstance().getConfig().getString("Tab.Premium+.Prefix");
	private String mwler = Lobby.getInstance().getConfig().getString("Tab.MWler.Prefix");
	private String elite = Lobby.getInstance().getConfig().getString("Tab.Elite.Prefix");
	private String vip = Lobby.getInstance().getConfig().getString("Tab.Premium.Prefix");
	private String player = Lobby.getInstance().getConfig().getString("Tab.Player.Prefix");
	
	public GroupManager(){
		gm = this;
	}
	
	
	public static GroupManager getGroups(){
		return gm;
	}
	
	public String getSrDevPrefix() {
		return srdev;
	}
	
	public String getContentPrefix() {
		return content;
	}
	
	public String getHeadBuilderPrefix() {
		return hbuilder;
	}
	
	public String getElitePrefix() {
		return elite;
	}
	
	public String getMWlerPrefix() {
		return mwler;
	}
	
	public String getAdminPrefix(){
		return admin;
	}
	
	public String getDevPrefix(){
		return dev;
	}
	
	public String getSrModPrefix(){
		return srmod;
	}
	
	public String getModPrefix(){
		return mod;
	}
	
	public String getSupporterPrefix(){
		return sup;
	}
	
	public String getBuilderPrefix(){
		return builder;
	}
	
	public String getYouTuberPrefix(){
		return yt;
	}
	
	public String getPremiumPlusPrefix(){
		return premi;
	}
	
	public String getPremiumPrefix(){
		return vip;
	}
	
	public String getPlayerPrefix(){
		return player;
	}
	

}

