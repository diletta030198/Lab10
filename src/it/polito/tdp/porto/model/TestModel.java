package it.polito.tdp.porto.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		model.creaGrafo();
		
		System.out.println(model.getGrafo());
		int archi= model.getGrafo().edgeSet().size();
		int vertici = model.getGrafo().vertexSet().size();
		
		System.out.println("Numero archi= "+archi+" Numero vertici= "+vertici);
		Author partenza = new Author(4096, "Martina","Maurizio");
		Author arrivo = new Author(18415, "Abate","Francesco");
		System.out.println(model.trovaCamminoMinimo(partenza, arrivo));
		System.out.println(model.listPaper(partenza, arrivo)); 
		
		
	}

}
