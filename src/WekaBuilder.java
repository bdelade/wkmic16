import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesSimple;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.functions.SMO;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.ClassificationViaClustering;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.meta.Vote;
import weka.classifiers.trees.BFTree;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.REPTree;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
import weka.filters.Filter;
public class WekaBuilder {
//cette classe contiendra aussi le classifier (arbre) que nous allons implémenter
HashMap<String,Classifier> models;// plusieurs classifiers afin de pouvoir les faire voter
//un autre atribut de type option permettant stocker/créer différente option modifiable	
	
public WekaBuilder(){
	models=new HashMap<String,Classifier>();
	models.put("RandomForest", new RandomForest());// un des classifier par défaut
	models.put("J48", new J48()); //même chose
	models.put("BFTree", new BFTree()); //même chose
	
	models.put("BayesNet", new BayesNet());
	models.put("NBayes", new NaiveBayes());
	models.put("NBayesS", new NaiveBayesSimple());
	models.put("CVC", new ClassificationViaClustering());
	models.put("AdaBoostM1", new AdaBoostM1());
	models.put("REPTree",new REPTree()); 
	models.put("FC",new FilteredClassifier()); 

} 

	public void addModel(String key,Classifier classif ){
		
		models.put(key, classif); //ajout d'un classifier
		
	}
	
	public void setTreeOption(String key) {
		
		//option par defaut pour les classifiers par défaut
		((RandomForest) models.get(key)).setMaxDepth(4);
		/* String[] options = new String[4];
		 options[0] = "-depth";
		 options[1] = "4";
		 options[2] = "-I";
		 options[3] = "23";
		 try {
			models.get(key).setOptions(options);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		((RandomForest) models.get(key)).setNumTrees(10);
		
	//même chose(pas les mêmes options) pour les autres classifier par défault
		
	}
	
	public boolean setOption(String key,String[] opt) {
	//on met les option pour un classifier 
	//une classe option sera creer afin de stocker/créer différente option modifiable	
		try {
		
			models.get(key).setOptions(opt);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			
		}
		return true;
		
	}


public  void buildOneFClassifiers(String keyclassif,Instances traindata,Filter filt){
	
	
if(models.get(keyclassif).getClass().getPackage().getName().equals("weka.classifiers.trees")){
	System.out.println("trees");
	setTreeOption(keyclassif);
}

	((FilteredClassifier) models.get("FC")).setFilter(filt);
	((FilteredClassifier) models.get("FC")).setClassifier( models.get(keyclassif));
	try {
		((FilteredClassifier) models.get("FC")).buildClassifier(traindata);
	} catch (Exception e) {
	System.err.println("failed to build Filtered classifier");
		e.printStackTrace();
	}

}
	
	public void buildallClassifiers(Instances arg) {
		//on construit tout les classifier
		for(Entry<String, Classifier> entry : models.entrySet()) {
		    
		   try {
			entry.getValue().buildClassifier(arg);
		} catch (Exception e) {

			System.err.println("failed to build the "+entry.getKey()+"classifier");
			e.printStackTrace();
		}
		   
		}
		
	}
	public void buildOneClassifiers(String keyclassif,Instances arg){
		
		if((models.get(keyclassif).getClass().getPackage().getName()=="weka.classifiers.trees")){
			setTreeOption(keyclassif);
		}
			
		//on construit un classifier
		
	try {
		models.get(keyclassif).buildClassifier(arg);
	} catch (Exception e) {

		System.err.println("failed to build the "+ keyclassif+ " classifier");
		e.printStackTrace();
	}
	}
	
	public Collection<Classifier> getAllClassifier (Instances arg){
		return models.values();//récupere tout les classifier
	}
	
	public Classifier getOneClassifier(String key)
	{
		return models.get(key);
	}	

}
