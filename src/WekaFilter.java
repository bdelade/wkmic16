import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.ExhaustiveSearch;
import weka.attributeSelection.GreedyStepwise;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.NominalPrediction;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
import weka.filters.*;
import weka.filters.supervised.attribute.AttributeSelection;
public class WekaFilter {
	//cette classe a pour but de retirer les attributs insignifiants/les évaluer /trouver un ordre d'importance..
	
@SuppressWarnings("unused")
private Instances filteredtrainData,nonfiltered, filteredvalidData, filteredtestData; //instances filtré
private WekaReader wr; //reader contenant les instances non filtré
private ASEvaluation  eval; //permet d'évaluer les attribut afin de guider la recherche		
private ASSearch search; //permet d'obtenir un rang ou des classe d'attribut
private AttributeSelection ASfilter;  //filtre d'attribut
private Filter filter; //filtre generale
private int nbattributes; //nombre attributs
public WekaFilter(WekaReader wekaread) throws Exception{
	//construction et selection attribut
	wr=wekaread;
	ASfilter = new AttributeSelection();
	 eval=new CfsSubsetEval(); // evaluation par default
	 search=new GreedyStepwise(); // search par défaut  par default
	 ((GreedyStepwise) search).setSearchBackwards(true);
	ASfilter.setEvaluator(eval);
	ASfilter.setSearch(search);
	
	ASfilter.setInputFormat(new Instances(wr.getTrainData()));
	 //filtrage
	filteredtrainData=Filter.useFilter((Instances)wr.getTrainData(), ASfilter);
	filteredvalidData=Filter.useFilter((Instances)wr.getTrainData(), ASfilter);
	filteredtestData=Filter.useFilter((Instances)wr.getTrainData(), ASfilter);
	nbattributes= filteredtrainData.numAttributes()-1;//l'attribut qui nous interesse
	filteredtrainData.setClassIndex(nbattributes);
	filteredvalidData.setClassIndex(nbattributes);
	filteredtestData.setClassIndex(nbattributes);
}

public boolean RemoveUseless(){//suppression des attribut insignifiant
	return false;
}

public Instances getFTraindata(){
	return filteredtrainData;
}

public Instances getFValiddata(){
	return filteredvalidData;
}
public Instances getFTestdata(){
	return filteredtestData;
}
 
public Attribute getAttribute(){
	return filteredtrainData.attribute(nbattributes);
	
}



}
