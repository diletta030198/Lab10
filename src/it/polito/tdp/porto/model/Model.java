package it.polito.tdp.porto.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private Graph <Author,DefaultEdge> grafo; 
	private Map <Integer,Author> idMapAutori; 
	
	public Model() {
		idMapAutori= new HashMap<Integer,Author>(); 
		grafo= new SimpleGraph<>(DefaultEdge.class);
		PortoDAO dao= new PortoDAO();
		dao.getAllAutori(idMapAutori);
	}
	
	public void creaGrafo() {
		
		PortoDAO dao= new PortoDAO();
		dao.getAllAutori(idMapAutori);
		
		//aggiungo i vertici
		Graphs.addAllVertices(grafo, idMapAutori.values());
		
		//aggiungo gli archi
		List<Adiacenza> adj= dao.listAdiacenze();
		
		for(Adiacenza a : adj) {
			Author source= idMapAutori.get(a.getAutore1());
			Author dest= idMapAutori.get(a.getAutore2());
			this.grafo.addEdge(source, dest);
		}
	}

	public Graph<Author, DefaultEdge> getGrafo() {
		return grafo;
	}

	public Map<Integer, Author> getIdMapAutori() {
		return idMapAutori;
	}
	
	public Set<Author>trovaVicini(Author a){
		Set<Author> vicini= new HashSet<Author>( );
		
		vicini= Graphs.neighborSetOf(grafo, a);
		
		 return vicini;
	}
	
	public List <Author> nonVicini(Author a){
		List<Author> res= new LinkedList<Author>(); 
		for(Author b: this.getIdMapAutori().values()) {
			if(!this.trovaVicini(a).contains(b) && !b.equals(a)) {
				res.add(b);
			}
		}
		Collections.sort(res);
		return res;
	}
	
	public List <Author> trovaCamminoMinimo(Author partenza, Author arrivo){
		DijkstraShortestPath<Author,DefaultEdge> dijkstra = new DijkstraShortestPath<>(this.grafo);
		
         GraphPath<Author,DefaultEdge> path = dijkstra.getPath(partenza,arrivo);
         
         return path.getVertexList();
	}
	
	public List <Paper> listPaper(Author partenza, Author arrivo){
		PortoDAO dao= new PortoDAO(); 
		List <Paper> res= new LinkedList<Paper>(); 
		for (Author a: this.trovaCamminoMinimo(partenza,arrivo)) {
			res.add(dao.prossimoLibro(a));
		}
		return res; 
	}
	

}
