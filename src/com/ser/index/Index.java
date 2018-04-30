package com.ser.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ser.component.Component;
import com.ser.component.Grid;
import com.ser.component.attachable;
import com.ser.servlets.globalServelt;

public class Index implements attachable {
	private globalServelt globalServelt;
	public Map<String,String> parameters;
	public Component attachedComponent;
	public HashMap<Component, String[][]> correspondanceTable = new HashMap<Component,String[][]>();
	/**
	 * Constructeur vide de la classe Index
	 */
	public Index(){parameters = new HashMap<String,String>();}
	/**
	 * Constructeur de la class Index
	 * @param globalServelt 
	 */
	public Index(globalServelt globalServelt){
		parameters = new HashMap<String,String>();
		Grid g = new Grid(
				new String[][]{
						{"ID","color:red;"},
						{"nom","color:blue;"},
						{"ville"}
				},
				new HashMap<String, String[]>(){{
					put("1", new String[]{"1","David","Washington"});
					put("2", new String[]{"2","Pierre","Delhi"});
					put("3", new String[]{"3","Leo","Berlin"});
				}}
		);		
		this.attach(g);
		this.globalServelt=globalServelt;
	}

	/**
	 * Methode permetant d'attacher un composant à l'index
	 */
	@Override
	public boolean attach(Component c) {
		this.attachedComponent=c;
		return true;
	}
	
	/**
	 * La methode compile le jsp general
	 * @return
	 * @throws IOException
	 */
	public boolean compile() throws IOException{
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		//On recupere le contenue de base du fichier
		InputStream io = this.globalServelt.getServletContext().getResourceAsStream("/WEB-INF/Grid.jsp");
		int tmpIndex;
		String tmpFile = "";
		while((tmpIndex = io.read())!=-1) {
			tmpFile += (char)tmpIndex;
		}
		//On crée l'arborescence à partir des composants attaché
		Document dIndex = new Document("");
		Element IndexComponent = dIndex.appendElement("div");
		IndexComponent.attr("id", "indexComponent");
		//Traite l'affichage du component attaché
		System.out.println(attachedComponent.ID);
		//On recupere la classe du composant
		String tmpClassName = attachedComponent.getClass().toString().replace("class com.ser.component.", "");
		//On recupere sa correspondance JSP
		//FileInputStream f = new FileInputStream(new File("WebContent/WEB-INF/"+tmpJspComponent));
		
		String tmpJspComponent = tmpClassName+".jsp";
		InputStream tmpComponentInputStream = this.globalServelt.getServletContext().getResourceAsStream("/WEB-INF/"+tmpJspComponent);
		Document tmpJsoupDocumentComponent = Jsoup.parse(tmpComponentInputStream,"UTF-8", "");
		String tmpComposantHTML = tmpJsoupDocumentComponent.getElementById(tmpClassName+"Component").outerHtml();
		//On ajoute les parametres spécifiques
		String tmpComposantParametre = this.ajouteParametre(attachedComponent,tmpComposantHTML);
		//On ajoute le composant à l'index Component
		IndexComponent.append(tmpComposantParametre);
		//On ecris le JSP final

	    File file = new File(this.globalServelt.getServletContext().getRealPath("") +"/WEB-INF/index.jsp");
        FileOutputStream fichier = new FileOutputStream(file);
        fichier.write((tmpFile+""+dIndex.html().replace("c:foreach", "c:forEach").replace("varstatus", "varStatus")+"</body></html>").getBytes());
        fichier.close();

		return true;
	}

	/**
	 * La methode genere les parametres unique pour chaques composants
	 * @param pComponent
	 * @param pComposantHTML
	 * @return
	 */
	private String ajouteParametre(Component pComponent, String pComposantHTML) {
		String tmpComposantHTML = pComposantHTML; 
		String[][] tmpData = null;
		String[][] tmpCorrespondance = null;
		//Recupere la correspondance des parametres du composant
		switch(pComponent.getClass().toString().replace("class com.ser.component.", "")){
			case "Grid":
				tmpData = new String[][]{
						{"${ param.ID }",String.valueOf(pComponent.ID)},
						{"${ gridHead }","${ gridHead"+pComponent.ID+" }"},
						{"${gridData}","${gridData"+pComponent.ID+"}"}
				};
				tmpCorrespondance = new String[][]{
						{"gridHead"+pComponent.ID, "getParameters"},
						{"gridData"+pComponent.ID, "getData"}
				};
			break;
		}
	
		//Verifie la presence des parametres
		if(tmpData==null){
			try {
				throw new Exception("Aucun composant ne correspond à cette Class");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			//Ajoute la table à la table des correspondances
			this.correspondanceTable.put(pComponent, tmpCorrespondance);
		}
		//Remplace par la correspondance
		for(String[] tmpParametreGridData : tmpData)
			tmpComposantHTML=tmpComposantHTML.replace(tmpParametreGridData[0], tmpParametreGridData[1]);
		return tmpComposantHTML;
	}
	/**
	 * La methode passe les parametres dans la requetes
	 * @param request
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public void passeParametre(HttpServletRequest request) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		for(Map.Entry<Component, String[][]> e : this.correspondanceTable.entrySet()){
			for(String[] tmpParametre : e.getValue()){
				//On recupere la class de l'objet
				Class<?> tmpClassObject = Class.forName(e.getKey().getClass().toString().replace("class ",""));
				//Object tmpObject = tmpClassObject.newInstance(); 	
				String tmpMethodName = tmpParametre[1];
				//On invoque la methode de la class
				Method method = e.getKey().getClass().getMethod(tmpMethodName);
				Object tmpResultat = method.invoke(e.getKey());
				System.out.println(tmpResultat);
				//On passe le parametre a la JSP
				request.setAttribute(tmpParametre[0], tmpResultat);
			}
		}
		
	}



	
}
