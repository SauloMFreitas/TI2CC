package maven.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicDAO extends DAO {
	
	public MusicDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}
	
	
	public boolean insert(Music music) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO Music (Faixa, Album, Artistas, Views, Genero) "
				       + "VALUES ("+music.getFaixa()+ ", '" + music.getAlbum() + "', '"  
				       + music.getArtistas() + "', '" + music.getViews() + "', '" + music.getGenero() + "');";
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Music get(String Faixa, String Album) {
		Music music = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Music WHERE Faixa=" + Faixa + "AND Album="+Album;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 music = new Music(rs.getString("Faixa"), rs.getString("Album"), rs.getString("Artistas"), rs.getInt("Views"), rs.getString("Genero"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return music;
	}
	
	
	public List<Music> get() {
		return get("");
	}

	
	public List<Music> getOrderByFaixa() {
		return get("Faixa");		
	}
	
	
	public List<Music> getOrderByAlbum() {
		return get("Album");		
	}
	
	
	public List<Music> getOrderByArtistas() {
		return get("Artistas");		
	}
	
	public List<Music> getOrderByViews() {
		return get("Views");		
	}
	public List<Music> getOrderByGenero() {
		return get("Genero");		
	}
	
	private List<Music> get(String orderBy) {	
	
		List<Music> musics = new ArrayList<Music>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Music" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Music u = new Music(rs.getString("Faixa"), rs.getString("Album"), rs.getString("Artistas"), rs.getInt("Views"), rs.getString("Genero"));
	            musics.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return musics;
	}


	public List<Music> getGeneroPOP() {
		List<Music> musics = new ArrayList<Music>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Music WHERE Music.Genero LIKE POP";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Music u = new Music(rs.getString("Faixa"), rs.getString("Album"), rs.getString("Artistas"), rs.getInt("Views"), rs.getString("Genero"));
	            musics.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return musics;
	}
	
	
	public boolean update(Music music) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE Music SET Album = '" + music.getAlbum() + "', Artistas = '"  
				       + music.getArtistas() + "', Views = '" + music.getViews() + "', Genero = '" + music.getGenero() + "'"
					   + " WHERE Faixa = " + music.getFaixa();
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean delete(String faixa) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM Music WHERE Faixa = " + faixa;
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean autenticar(String Faixa, String Album) {
		boolean resp = false;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Music WHERE Faixa LIKE '" + Faixa + "' AND Album LIKE '" + Album  + "'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			resp = rs.next();
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resp;
	}	
}
