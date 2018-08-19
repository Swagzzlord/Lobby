package net.minewars.lobby.scoreboard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import net.minewars.lobby.main.MySQL;

public class ScoreboardClass {

	private String coinHost = "localhost";
	private String coinPort = "3306";
	private String coinDatabase = "Server";
	private String coinUser = "user";
	private String coinPwd = "schnucki2001";
	
	private String friendHost = "localhost";
	private String friendPort = "3306";
	private String friendDatabase = "Server";
	private String friendUser = "user";
	private String friendPwd = "schnucki2001";
	
	private String nickHost = "localhost";
	private String nickPort = "3306";
	private String nickDatabase = "Server";
	private String nickUser = "user";
	private String nickPwd = "schnucki2001";
	
	private static MySQL coinSQL;
	private static MySQL friendSQL;
	private static MySQL nickSQL;
	private static HashMap<Player, Scoreboard> map = new HashMap<>();
	
	public ScoreboardClass() {
		MySQL coinsql = new MySQL(coinHost, coinPort, coinDatabase, coinUser, coinPwd);
		MySQL friendsql = new MySQL(friendHost, friendPort, friendDatabase, friendUser, friendPwd);
		MySQL nicksql = new MySQL(nickHost, nickPort, nickDatabase, nickUser, nickPwd);
		
		coinsql.connect();
		friendsql.connect();
		nicksql.connect();
		
		coinSQL = coinsql;
		friendSQL = friendsql;
		nickSQL = nicksql;
	}
	
	public static void disconnect() {
		coinSQL.disconnect();
		friendSQL.disconnect();
		nickSQL.disconnect();
	}
	
	@SuppressWarnings("deprecation")
	public static void setLobbyBoard(Player p){
		
		for(Player players : Bukkit.getOnlinePlayers()) {
			
			ScoreboardManager manager = Bukkit.getScoreboardManager();
			Scoreboard board = manager.getNewScoreboard();
	    	Objective obj = board.registerNewObjective("Board", "dummy");
		    
		    obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		    obj.setDisplayName("§2§lM§aine§2w§aars");
		    
			/*
			 * 
			 * Server
			 *   Lobby-1
			 * 
			 * Coins
			 *   1000
			 * 
			 * Freunde
			 *   0 / 1
			 *   
			 * Website/Ts3
			 *   minewars.net
			 *   
			 *   •
			 *
			 */
		    
		    int coins1 = getCoins(players.getUniqueId().toString());
		    int current = getOnlineFriends(players.getUniqueId().toString());
		    int max = getFriendsSize(players.getUniqueId().toString());
		    
		    obj.getScore(Bukkit.getOfflinePlayer("§7Server§8:")).setScore(15);
		    obj.getScore(Bukkit.getOfflinePlayer("  §8• §6" + "Lobby")).setScore(14);
		    obj.getScore(Bukkit.getOfflinePlayer("§e")).setScore(13);
		    obj.getScore(Bukkit.getOfflinePlayer("§7Coins§8:")).setScore(12);
		    obj.getScore(Bukkit.getOfflinePlayer("  §8• §e" + coins1)).setScore(11);
		    obj.getScore(Bukkit.getOfflinePlayer("§a")).setScore(10);
		    obj.getScore(Bukkit.getOfflinePlayer("§7Freunde§8:")).setScore(9);
		    obj.getScore(Bukkit.getOfflinePlayer("  §8• §a" + current + "§8/§2" + max)).setScore(8);
		    obj.getScore(Bukkit.getOfflinePlayer("§b")).setScore(7);
		    obj.getScore(Bukkit.getOfflinePlayer("§7Website/TS§8:")).setScore(6);
		    obj.getScore(Bukkit.getOfflinePlayer("  §8• §bMinewars.net")).setScore(5);
		    
		    Team admin = board.registerNewTeam("00000Admin");
		    Team srdev = board.registerNewTeam("00001SrDev");
			Team dev = board.registerNewTeam("00002Dev");
			Team content = board.registerNewTeam("00003Content");
			Team srmod = board.registerNewTeam("00004SrMod");
			Team mod = board.registerNewTeam("00005Mod");
			Team sup = board.registerNewTeam("00006Sup");
			Team hbuild = board.registerNewTeam("00007HBuilder");
			Team build = board.registerNewTeam("00008Build");
			Team youtuber = board.registerNewTeam("00009Youtuber");
			Team premiumplus = board.registerNewTeam("00010Premium+");
			Team mwler = board.registerNewTeam("00011MWler");
			Team elite = board.registerNewTeam("00012Elite");
			Team premium = board.registerNewTeam("00013Premium"); 
			Team spieler = board.registerNewTeam("00014Spieler");
			    
			   //Prefixe  §8§
			admin.setPrefix(GroupManager.getGroups().getAdminPrefix());
			srdev.setPrefix(GroupManager.getGroups().getSrDevPrefix());
			dev.setPrefix(GroupManager.getGroups().getDevPrefix());
			content.setPrefix(GroupManager.getGroups().getContentPrefix());
			srmod.setPrefix(GroupManager.getGroups().getSrModPrefix());
			mod.setPrefix(GroupManager.getGroups().getModPrefix());
			sup.setPrefix(GroupManager.getGroups().getSupporterPrefix());
			hbuild.setPrefix(GroupManager.getGroups().getHeadBuilderPrefix());
			build.setPrefix(GroupManager.getGroups().getBuilderPrefix());
			youtuber.setPrefix(GroupManager.getGroups().getYouTuberPrefix());
			premiumplus.setPrefix(GroupManager.getGroups().getPremiumPlusPrefix());
			mwler.setPrefix(GroupManager.getGroups().getMWlerPrefix());
			elite.setPrefix(GroupManager.getGroups().getElitePrefix());
			premium.setPrefix(GroupManager.getGroups().getPremiumPrefix());
			spieler.setPrefix(GroupManager.getGroups().getPlayerPrefix());
			
			
			for(Player all : Bukkit.getOnlinePlayers()) {
				if(all.hasPermission("lobby.admin")) {
					admin.addPlayer(all);	       
			    	all.setDisplayName(admin.getPrefix() + all.getName());
			       	all.setPlayerListName(all.getDisplayName());
			    } else if(all.hasPermission("lobby.srdev")) {
			    	srdev.addPlayer(all);
			    	all.setDisplayName(srdev.getPrefix() + all.getName());
				    all.setPlayerListName(all.getDisplayName());
			    }  else if(all.hasPermission("lobby.dev")) {
			    	dev.addPlayer(all);
			    	all.setDisplayName(dev.getPrefix() + all.getName());
				    all.setPlayerListName(all.getDisplayName());
			    } else if(all.hasPermission("lobby.content")) {
			    	content.addPlayer(all);
			    	all.setDisplayName(content.getPrefix() + all.getName());
				    all.setPlayerListName(all.getDisplayName());
			    }  else if(all.hasPermission("lobby.srmod")) {
			    	srmod.addPlayer(all);
			    	all.setDisplayName(srmod.getPrefix() + all.getName());
				    all.setPlayerListName(all.getDisplayName());
			    } else if(all.hasPermission("lobby.mod")) {
			    	mod.addPlayer(all);
			    	all.setDisplayName(mod.getPrefix() + all.getName());
				    all.setPlayerListName(all.getDisplayName());
			    } else if(all.hasPermission("lobby.sup")) {
			    	sup.addPlayer(all);
			    	all.setDisplayName(sup.getPrefix() + all.getName());
				    all.setPlayerListName(all.getDisplayName());
			    } else if(all.hasPermission("lobby.headbuilder")) {
			    	hbuild.addPlayer(all);
			    	all.setDisplayName(hbuild.getPrefix() + all.getName());
				    all.setPlayerListName(all.getDisplayName()); 
			    } else if(all.hasPermission("lobby.build")) {
			    	build.addPlayer(all);
			    	all.setDisplayName(build.getPrefix() + all.getName());
				    all.setPlayerListName(all.getDisplayName());
			    } else if(all.hasPermission("lobby.youtuber")) {
			    	youtuber.addPlayer(all);
			    	all.setDisplayName(youtuber.getPrefix() + all.getName());
			    	all.setPlayerListName(all.getDisplayName());
			    } else if(all.hasPermission("lobby.premium+")) {
			    	premiumplus.addPlayer(all);
			    	all.setDisplayName(premiumplus.getPrefix() + all.getName());
				    all.setPlayerListName(all.getDisplayName());
			    } else if(all.hasPermission("lobby.mwler")) {
			    	mwler.addPlayer(all);
			    	all.setDisplayName(mwler.getPrefix() + all.getName());
				    all.setPlayerListName(all.getDisplayName());
			    }  else if(all.hasPermission("lobby.elite")) {
			    	all.setDisplayName(elite.getPrefix() + all.getName());
			    	elite.addPlayer(all);
			    	all.setPlayerListName(all.getDisplayName());
			    } else if(all.hasPermission("lobby.premium")) {
			    	premium.addPlayer(all);
			    	all.setDisplayName(premium.getPrefix() + all.getName());
				    all.setPlayerListName(all.getDisplayName());
			    } else {
			    	spieler.addPlayer(all);
			    	all.setDisplayName(spieler.getPrefix() + all.getName());
				    all.setPlayerListName(all.getDisplayName());
			    }
			       
				
		        players.setScoreboard(board);
			}
	        
	        if(!map.containsKey(players)) {
				map.put(players, board);
			}
		}
	}
	
	public static HashMap<Player, Scoreboard> getMap(){
		return map;
	}
	
	public static int getCoins(String uuid) {
		try {
			PreparedStatement st = coinSQL.getConnection().prepareStatement("SELECT Coins FROM CoinTable WHERE UUID = ?");
			st.setString(1, uuid);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				return rs.getInt("Coins");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	private static int getOnlineFriends(String uuid) {
		try {
			PreparedStatement st = friendSQL.getConnection().prepareStatement("SELECT Friends FROM Friend WHERE UUID = ?");
		    st.setString(1, uuid);
		    ResultSet rs = st.executeQuery();
		    if(rs.next()){
		    	String[] req = rs.getString("Friends").split(",");
		    	int ret = 0;
		    	for(int i = 0; i < req.length; i++){
		    		if(req[i] != null){
			    		if(!req[i].equals("null")){
			    			if(!req[i].equals(",")){
			    				if(!req[i].equals("")){
			    					if(isOnline(req[i])) {
			    						ret++;
			    					}
			    				}else{
			    					
			    				}
			    			}else{
			    				
			    			}
			    		}else{
			    			
			    		}
			    	}else{
			    		
			    	}
		    	}
		    	
		    	return ret;
		    }else{
		    	
		    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(e instanceof SQLException) {
				e.printStackTrace();
			}
		}
		
		return 0;
	}
	
	private static boolean isOnline(String player){
		try {
			PreparedStatement st = friendSQL.getConnection().prepareStatement("SELECT isOnline FROM Friend WHERE UUID = ?");
			st.setString(1, player);
			ResultSet rs = st.executeQuery();
			if(rs.next()){
				return rs.getBoolean("isOnline");
			}else{
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	private static int getFriendsSize(String uuid) {
		try {
			PreparedStatement st = friendSQL.getConnection().prepareStatement("SELECT Friends FROM Friend WHERE UUID = ?");
		    st.setString(1, uuid);
		    ResultSet rs = st.executeQuery();
		    if(rs.next()){
		    	String[] req = rs.getString("Friends").split(",");
		    	int ret = 0;
		    	for(int i = 0; i < req.length; i++){
		    		if(req[i] != null){
			    		if(!req[i].equals("null")){
			    			if(!req[i].equals(",")){
			    				if(!req[i].equals("")){
			    					ret ++;
			    				}else{
			    					
			    				}
			    			}else{
			    				
			    			}
			    		}else{
			    			
			    		}
			    	}else{
			    		
			    	}
		    	}
		    	
		    	return ret;
		    }else{
		    	
		    }
		} catch (Exception e) {
			if(e instanceof SQLException) {
				e.printStackTrace();
			}
		}
		
		return 0;
	}
	
	public static Player[] getFriends(String uuid) {
		Player[] array = new Player[0];
		try {
			PreparedStatement st = friendSQL.getConnection().prepareStatement("SELECT Friends FROM Friend WHERE UUID = ?");
		    st.setString(1, uuid);
		    ResultSet rs = st.executeQuery();
		    if(rs.next()){
		    	String[] req = rs.getString("Friends").split(",");
		    	for(int i = 0; i < req.length; i++){
		    		if(req[i] != null){
			    		if(!req[i].equals("null")){
			    			if(!req[i].equals(",")){
			    				if(!req[i].equals("")){
			    					UUID u = UUID.fromString(req[i]);
			    					Player p = Bukkit.getPlayer(u);
			    					if(p != null) {
			    						int length = array.length + 1;
			    						Player[] newarray = new Player[length];
			    						for(int j = 0; j < array.length; j++) {
			    							newarray[j] = array[j];
			    						}
			    						
			    						newarray[array.length] = p;
			    						array = newarray;
			    						
			    					}
			    				}else{
			    					
			    				}
			    			}else{
			    				
			    			}
			    		}else{
			    			
			    		}
			    	}else{
			    		
			    	}
		    	}
		    	
		    	return array;
		    }else{
		    	
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static boolean isNicked(String uuid) {
		try {
			PreparedStatement st = nickSQL.getConnection().prepareStatement("SELECT Nicked FROM NickTable WHERE UUID = ?");
			st.setString(1, uuid);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				return rs.getBoolean("Nicked");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static void nick(String uuid, boolean nick) {
		try {
			PreparedStatement st = nickSQL.getConnection().prepareStatement("UPDATE NickTable SET Nicked = ? WHERE UUID = ?");
			st.setBoolean(1, nick);
			st.setString(2, uuid);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
