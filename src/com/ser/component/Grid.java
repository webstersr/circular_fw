package com.ser.component;

import java.util.HashMap;

public class Grid extends Component {
	//======================================
	//======================================
	//			ATTRIBUTS DU GRID
	//======================================
	//======================================
	public String[][] parameters;
	public HashMap<String,String[]> data;
	//======================================
	//======================================
	//			  CONSTRUCTEURS
	//======================================
	//======================================
	public Grid(){
		this.ID=System.currentTimeMillis();
		this.parameters = new String[][]{};
		this.data = new HashMap<String,String[]>();
	}
	
	public Grid(String[][] parameters){
		this.ID=System.currentTimeMillis();
		this.parameters = parameters;
		this.data = new HashMap<String,String[]>();
	}
	
	public Grid(String[][] parameters,HashMap<String, String[]> data){
		this.ID=System.currentTimeMillis();
		this.parameters = parameters;
		this.data = data;
	}
	//======================================
	//======================================
	//			 FONCTIONNALITEES
	//======================================
	//======================================
	
	//======================================
	//======================================
	//			 GETTERS / SETTERS
	//======================================
	//======================================
		
	/**
	 * Fonction qui recuperer le tableau de parametre de la grid 
	 * @return HashMap<String, String> : le tableau de parametre
	 */
	public String[][] getParameters() {
		return parameters;
	}
	/**
	 * Fonction qui ajouter le tableau de parametre de la grid
	 * @param  HashMap<String, String> : le tableau de parametre
	 * @return void
	 */
	public void setParameters(String[][] parameters) {
		this.parameters = parameters;
	}
	/**
	 * Fonction qui recupere le tableau de donnée
	 * @return HashMap<String, String[]> : le tableau de donnée
	 */
	public HashMap<String, String[]> getData() {
		return data;
	}
	/**
	 * Fonction qui ajoute le tableau de donnée
	 * @param  HashMap<String, String[]> : le tableau de donnée
	 * @return void
	 */
	public void setData(HashMap<String, String[]> data) {
		this.data = data;
	}
	
	
}
