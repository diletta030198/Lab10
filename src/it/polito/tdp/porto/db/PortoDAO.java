package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.porto.model.Adiacenza;
import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Author> getAllAutori(Map<Integer,Author> idMap) {

		final String sql = "SELECT * FROM author";
		List<Author> result= new LinkedList<Author>();

		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
		

			ResultSet rs = st.executeQuery();
			
             while(rs.next()) {
			if (idMap.get(rs.getInt("id"))==null) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				idMap.put(autore.getId(), autore);
				result.add(autore);
				
			}
			else {
				result.add(idMap.get(rs.getInt("id")));
			}
             }
			conn.close();
			return result;
			
             
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	
	
	
	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	
	public List <Adiacenza> listAdiacenze(){
		String sql= "SELECT c1.authorid, c2.authorid " + 
				"FROM creator c1, creator c2 " + 
				"WHERE c1.eprintid=c2.eprintid AND c1.authorid>c2.authorid " + 
				"GROUP BY c1.authorid, c2.authorid"; 
		List <Adiacenza> res = new LinkedList<Adiacenza>(); 
		
try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
		

			ResultSet rs = st.executeQuery();
			
             while(rs.next()) {
			

				Adiacenza ad = new Adiacenza(rs.getInt("c1.authorid"), rs.getInt("c2.authorid"));
				res.add(ad); 
				
				
			
			
				
             }
			conn.close();
			return res;
} catch (SQLException e) {
	// e.printStackTrace();
	throw new RuntimeException("Errore Db");
}
		
	}
	
	public Paper prossimoLibro(Author a1) {
		int cod= a1.getId();
		
		final String sql = "SELECT p.eprintid,p.title,p.issn,p.publication,p.type,p.types " + 
				"FROM creator c,paper p, creator c2 " + 
				"WHERE c.eprintid=p.eprintid AND " + 
				" c.eprintid=c2.eprintid AND c.authorid like ? AND " + 
				" c2.authorid NOT LIKE ? " + 
				"GROUP BY p.title";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, cod);
			st.setInt(2, cod);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("p.eprintid"), rs.getString("p.title"), rs.getString("p.issn"),
						rs.getString("p.publication"), rs.getString("p.type"), rs.getString("p.types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}

		
		
		
	}
	
	
}