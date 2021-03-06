

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;

public class WekaRunner {	//effectue les prediction, les sauvegarde et évalue le score

	int m_nb=0;
	FastVector trainPred;//conteneur pour les predictions
	FastVector validPred;
	FastVector testPred;
	 static Attribute timeInHospital; //utiliser pour faire une évalutation du score 
	//WekaReader reader;
	//WekaBuilder builder;
	
	public WekaRunner(Attribute timeInHospital){
		this.timeInHospital=timeInHospital;
	}
	
	public static FastVector predict(Classifier model, Instances dataToTrain, Instances dataToPredict) throws Exception {
		
		// fait les prediction
    	Evaluation eval = new Evaluation(dataToTrain);
    	eval.evaluateModel(model, dataToPredict);
    	return eval.predictions();
		
	}
	
	
	public static void savePredictions(FastVector predictions, String filename) throws Exception {
    	
		// ecris les predictions dans un fichier
		PrintWriter pw = new PrintWriter(filename, "UTF-8");
    	for (int i = 0; i < predictions.size(); i++) {
			double val = ((NominalPrediction) predictions.elementAt(i)).predicted();
			pw.print(timeInHospital.value((int) val) + "\n");
		}
    	pw.close();
	}
	
	public static void zipPredictions(){
		  MyMultipleFileZip mfe = new MyMultipleFileZip();
	        List<String> files = new ArrayList<String>();
	        files.add("valid.predict");
	        files.add("test.predict");
	    
	        mfe.zipFiles(files);
	}
	
	public  double getScore(FastVector predictedValues, Instances trueValues) {
		int nb=0;
		//evalue le score
		int ind = trueValues.numAttributes() - 1;
		double nbPositive = 0.;
		double nbNegative = 0.;
		double nbTruePositive = 0.;
		double nbTrueNegative = 0.;
		
		for (int i = 0; i < trueValues.numInstances(); i++) { 
			
		
			int trueValue = (int) trueValues.instance(i).value(ind);
			int trueLabel = Integer.parseInt(timeInHospital.value(trueValue));
			
			
			int predictedValue = (int) ((NominalPrediction) predictedValues.elementAt(i)).predicted();
			int predictedLabel = Integer.parseInt(timeInHospital.value(predictedValue));
			
			if(predictedLabel==1){ m_nb=m_nb+1;
			}
		
			if (trueLabel == 1) {
				nbPositive++;
				if (predictedLabel == 1) nbTruePositive++;
			} else {
				nbNegative++;
				if (predictedLabel == -1) nbTrueNegative++;
			}
			
			
		}
		
		return 1./2. * (nbTruePositive / nbPositive + nbTrueNegative / nbNegative);
		
	}
	
	
	public void affichem_nb(){
		System.out.println("nb plus de 7: " + m_nb);
	}
	
	
/*
	public static void main(String[] args) throws Exception {
		WekaReader wr=new WekaReader();
		WekaFilter wf=new WekaFilter(wr);
		WekaBuilder wb=new WekaBuilder();
		
		wb.setDefaultOption();
		wb.buildOneClassifiers("RandomForest", wf.getFTraindata());
		WekaRunner wru= new WekaRunner(wf.getAttribute());	
		FastVector trainPred = predict(wb.getOneClassifier("RandomForest"),wf.getFTraindata() , wf.getFTraindata());
		
		double score = getScore(trainPred, wf.getFTraindata()); // calcule du score
    	System.out.println("Score on the training set : " + score);
       	//FastVector validPred = predict(wb.getOneClassifier("RandomForest"),wf.getFTraindata() , wr.getValidData());
    	//FastVector testPred = predict(wb.getOneClassifier("RandomForest"),wf.getFTraindata() , wr.getTestData());
    	FastVector validPred2 = predict(wb.getOneClassifier("RandomForest"),wf.getFTraindata() , wf.getFValiddata());
    	FastVector testPred2 = predict(wb.getOneClassifier("RandomForest"),wf.getFTraindata() , wf.getFTestdata());
    	
    	
    	savePredictions(validPred2, "valid.predict");
    	savePredictions(testPred2, "test.predict");
	
	}
	
	*/
}
