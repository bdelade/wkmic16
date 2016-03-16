import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.NominalPrediction;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
//instance(i) pour parcourir instance
public class WekaReader {
private	String trainPath,validPath,testPath;//chemin
	
protected Instances trainData, validData, testData; //instances
private int nbattributes; 
public WekaReader() throws FileNotFoundException, IOException {
	trainPath="dataset/train_set.arff"; //chemin par default vers les différent données
	validPath="dataset/valid_set.arff";
	testPath="dataset/test_set.arff";
 
		 trainData = new Instances(new FileReader(trainPath)); //creation des instances
		

	
		 validData = new Instances(new FileReader(validPath));
		 testData = new Instances(new FileReader(testPath));	
		nbattributes=trainData.numAttributes()-1;
		

 	trainData.setClassIndex(nbattributes); // on parametre l'attribut à predire
	validData.setClassIndex(nbattributes);
	testData.setClassIndex(nbattributes);

}
//getter an setter
public  Instances getTrainData() {
	return trainData;
}
public Instances getValidData() {
	return validData;
}
public Instances getTestData() {
	return testData;
}
public int getnbAttributes() {
	return nbattributes;
}
public void setTrainData(Instances trainData) {
	this.trainData = trainData;
}
public void setValidData(Instances validData) {
	this.validData = validData;
}
public void setTestData(Instances testData) {
	this.testData = testData;
}

public Attribute getAttribute(){
	return trainData.attribute(nbattributes);
	
}
	
	
	
}
