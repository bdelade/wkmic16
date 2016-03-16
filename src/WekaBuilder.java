import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.functions.SMO;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
public class WekaBuilder {
//cette classe contiendra aussi le classifier (arbre) que nous allons impl�menter
HashMap<String,Classifier> models;// plusieurs classifiers afin de pouvoir les faire voter
//un autre atribut de type option permettant stocker/cr�er diff�rente option modifiable	
	
public WekaBuilder(){
	models=new HashMap<String,Classifier>();
	models.put("RandomForest", new RandomForest());// un des classifier par d�faut
	models.put("J48", new J48()); //m�me chose

	
} 

	public void addModel(String key,Classifier classif ){
		
		models.put(key, classif); //ajout d'un classifier
		
	}
	
	public void setDefaultOption(){
		
		//option par defaut pour les classifiers par d�faut
		((RandomForest) models.get("RandomForest")).setMaxDepth(5);
			
		((RandomForest) models.get("RandomForest")).setNumTrees(25);
	//m�me chose(pas les m�mes options) pour les autres classifier par d�fault
		
	}
	
	public boolean setOption(String key,String[] opt) {
	//on met les option pour un classifier 
	//une classe option sera creer afin de stocker/cr�er diff�rente option modifiable	
		try {
		
			models.get(key).setOptions(opt);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			
		}
		return true;
		
	}
	
	public void buildallClassifiers(Instances arg) throws Exception{
		//on construit tout les classifier
		for(Entry<String, Classifier> entry : models.entrySet()) {
		    
		   entry.getValue().buildClassifier(arg);
		   
		}
		
	}
	public void buildOneClassifiers(String keyclassif,Instances arg){
		//on construit un classifier
		
	try {
		models.get(keyclassif).buildClassifier(arg);
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	}
	
	public Collection<Classifier> getAllClassifier (Instances arg){
		return models.values();//r�cupere tout les classifier
	}
	
	public Classifier getOneClassifier(String key)
	{
		return models.get(key);
	}	

}
