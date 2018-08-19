package net.minewars.lobby.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {

	private String host;
	private String port;
	private String database;
	private String user;
	private String password;
	
    private static Connection con;
	
	public MySQL(String host, String port, String database, String user, String password) {
		this.host = host;
		this.port = port;
		this.database = database;
		this.user = user;
		this.password = password;
		
		
	}
	
	public Connection getConnection(){
    	return con;
    }
	
	public void connect(){	    
        if(!connected()){
            try{
            	System.out.println(host + ":" + port);
            	System.out.println(user + " : " + password);
            	System.out.println(database);
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", user, password);
                System.out.println("[Lobby] MySQL Verbindung aufgebaut!");
            }catch(SQLException e){   
                System.out.println((new StringBuilder("[Lobby] ")).append(e.getMessage()).toString());
            }
            
        }
    }
 
    public void disconnect(){
        if(connected()){
            try{	            
                con.close();
                //System.out.println("[Freebuild] MySQL Verbindung geschlossen!");	            
            }catch(SQLException e){	            
                e.printStackTrace();
            }
            
        }	        
    }
 
    public boolean connected(){
        return con != null;
    }
 
    public void update(String qry){
        try{   
            PreparedStatement statement = con.prepareStatement(qry);
            statement.executeUpdate();
            statement.close();
        }catch(SQLException e){        
            e.printStackTrace();
        }
    }
 
    public ResultSet query(String qry){
        ResultSet rs = null;
        try{
            Statement st = con.createStatement();
            rs = st.executeQuery(qry);
        }catch(SQLException e){       
            e.printStackTrace();
        }
        return rs;
    }
	

}
