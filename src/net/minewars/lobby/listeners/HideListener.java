package net.minewars.lobby.listeners;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.minewars.lobby.main.MySQL;

public class HideListener {

	private static MySQL mysql;
	
	public HideListener(MySQL arg0) {
		/*
		 * 0 = alle anzeigen
		 * 1 = fandt
		 * 2 = f
		 * 3 = t
		 * 4 = alle verstecken
		 */
		
		
		mysql = arg0;
		mysql.update("CREATE TABLE IF NOT EXISTS HideTable(UUID VARCHAR(100), State INT(16), SilentLobby VARCHAR(100))");
	}
	
	public static void addPlayer(Player arg0) {
		try {
			PreparedStatement st = mysql.getConnection().prepareStatement("INSERT INTO HideTable(UUID,State,SilentLobby) VALUES (?,?,?)");
			st.setString(1, arg0.getUniqueId().toString());
			st.setInt(2, 0);
			st.setBoolean(3, false);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean exists(Player arg0) {
		try {
			PreparedStatement st = mysql.getConnection().prepareStatement("SELECT State FROM HideTable WHERE UUID = ?");
			st.setString(1, arg0.getUniqueId().toString());
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static void setState(Player arg0, int arg1) {
		try {
			PreparedStatement st = mysql.getConnection().prepareStatement("UPDATE HideTable SET State = ? WHERE UUID = ?");
			st.setInt(1, arg1);
			st.setString(2, arg0.getUniqueId().toString());
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int getState(Player arg0) {
		try {
			PreparedStatement st = mysql.getConnection().prepareStatement("SELECT State FROM HideTable WHERE UUID = ?");
			st.setString(1, arg0.getUniqueId().toString());
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				return rs.getInt("State");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static void setSilentLobby(Player arg0, boolean arg1) {
		try {
			PreparedStatement st = mysql.getConnection().prepareStatement("UPDATE HideTable SET SilentLobby = ? WHERE UUID = ?");
			st.setBoolean(1, arg1);
			st.setString(2, arg0.getUniqueId().toString());
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean inSilentLobby(Player arg0) {
		try {
			PreparedStatement st = mysql.getConnection().prepareStatement("SELECT SilentLobby FROM HideTable WHERE UUID = ?");
			st.setString(1, arg0.getUniqueId().toString());
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				return rs.getBoolean("SilentLobby");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static Player[] byState(int arg0) {
		List<Player> list = new ArrayList<>();
		
		try {
			PreparedStatement st = mysql.getConnection().prepareStatement("SELECT * FROM HideTable WHERE State = ?");
			st.setInt(1, arg0);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				UUID uuid = UUID.fromString(rs.getString("UUID"));
				Player p = Bukkit.getPlayer(uuid);
				if(p != null) {
					list.add(p);
				}
			}
			
			Player[] array = new Player[list.size()];
			for(int i = 0; i < list.size(); i++) {
				array[i] = list.get(i);
			}
			
			return array;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
