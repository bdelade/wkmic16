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
@SuppressWarnings("unused")
private Instances filteredtrainData,nonfiltered, filteredvalidData, filteredtestData;
private WekaReader wr;
private ASEvaluation  eval;
private ASSearch search;
private AttributeSelection ASfilter;
private int nbattributes;
public WekaFilter(WekaReader wekaread) throws Exception{
	wr=wekaread;
	ASfilter = new AttributeSelection();
	 eval=new CfsSubsetEval();
	 search=new GreedyStepwise();
	 ((GreedyStepwise) search).setSearchBackwards(true);
	ASfilter.setEvaluator(eval);
	ASfilter.setSearch(search);
	
	ASfilter.setInputFormat(new Instances(wr.getTrainData()));
	 
	filteredtrainData=Filter.useFilter((Instances)wr.getTrainData(), ASfilter);
	filteredvalidData=Filter.useFilter((Instances)wr.getTrainData(), ASfilter);
	filteredtestData=Filter.useFilter((Instances)wr.getTrainData(), ASfilter);
	nbattributes= filteredtrainData.numAttributes()-1;
	filteredtrainData.setClassIndex(nbattributes);
	filteredvalidData.setClassIndex(nbattributes);
	filteredtestData.setClassIndex(nbattributes);
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
