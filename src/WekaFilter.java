import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.BestFirst;
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
import weka.filters.unsupervised.attribute.Remove;
public class WekaFilter {
	//cette classe a pour but de retirer les attributs insignifiants/les évaluer /trouver un ordre d'importance..
	
@SuppressWarnings("unused")
private Instances m_filteredtrainData, m_filteredvalidData, m_filteredtestData; //instances filtré
private WekaReader m_wr; //reader contenant les instances non filtré
private ASEvaluation  eval; //permet d'évaluer les attribut afin de guider la recherche		
private ASSearch search; //permet d'obtenir un rang ou des classe d'attribut
//private AttributeSelection ASfilter;  //filtre d'attribut
private Filter m_filter; //filtre generales
private int m_nbattributes; //nombre attributs
//14,13,16,15,17,43,20,19,18,12,11,10,3,2,9,4,5,6,7,8,21,22,23,
//14,13,16,15,17,43,20,19,18,12,11,10,3,2,9,4,5,6,7,8,21,22,23,
int[] indicesOfColumnsToUse = {14,13,16,15,17,43,20,19,18,12,11,10,3,2,9,4,5,6,44}; // determiner à l'aide de weka
public WekaFilter(WekaReader wekaread) throws Exception{
	for(int i=0;i<indicesOfColumnsToUse.length-1;i++){
		indicesOfColumnsToUse[i]=indicesOfColumnsToUse[i]-1;  //on décremente les élément
	System.out.println(indicesOfColumnsToUse[i]);
	}
	//construction et selection attribut
	m_wr=wekaread;
	m_filter = new Remove();
	((Remove)m_filter).setAttributeIndicesArray(indicesOfColumnsToUse);
((Remove)m_filter).setInvertSelection(true);
	
/*		
	m_filter = new weka.filters.unsupervised.attribute.Center();
	 eval=new CfsSubsetEval(); // evaluation par default
	 search=new BestFirst(); // search  
	search=new GreedyStepwise(); // search par défaut  par default
	 ((GreedyStepwise) search).setSearchBackwards(true);
	 ((AttributeSelection)m_filter).setEvaluator(eval);
	 ((AttributeSelection)m_filter).setSearch(search);
	*/
	 m_filter.setInputFormat(new Instances(m_wr.getTrainData()));
	 
	m_filteredtrainData=Filter.useFilter((Instances)m_wr.getTrainData(),m_filter );
	m_filteredvalidData=Filter.useFilter((Instances)m_wr.getTrainData(), m_filter);
	m_filteredtestData=Filter.useFilter((Instances)m_wr.getTrainData(), m_filter);
	m_nbattributes= m_filteredtrainData.numAttributes()-1;//l'attribut qui nous interesse
	m_filteredtrainData.setClassIndex(m_nbattributes);
	m_filteredvalidData.setClassIndex(m_nbattributes);
	m_filteredtestData.setClassIndex(m_nbattributes);
}

public boolean RemoveUseless(){//suppression des attribut insignifiant
	return false;
}

public Instances getFTraindata(){
	return m_filteredtrainData;
}

public Instances getFValiddata(){
	return m_filteredvalidData;
}
public Instances getFTestdata(){
	return m_filteredtestData;
}
 
public Attribute getAttribute(){
	return m_filteredtrainData.attribute(m_nbattributes);
	
}

public Filter getFilter(){
	return m_filter;
} 

}
