import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.FastVector;

public class JunitWeka {

	@Test
	public void testwekareader() throws Exception {
		WekaReader wr=new WekaReader();
		assertNotNull(wr);// on test si l'objet � bien �t� cr��
		
		assertNotNull(wr.getAttribute()); //on test que l'objet attribut 
		
		assertNotSame(wr.getTestData(),wr.getTrainData()); //on test si les instances sont diff�rentes
		
		
	}
	@Test
	
	public void testwekaFilter() throws Exception {
	
		WekaReader wr=new WekaReader();
		WekaFilter wf=new WekaFilter(wr);
		assertNotNull(wf);
		assertNotNull(wf.getAttribute()); //test attribut non nul 
		assertNotSame(wr.getTrainData(),wf.getFTraindata()); //test si les instance filtr� sont les m�me que non filtr�
		assertNotSame(wr.getTrainData().numInstances(),wf.getFTraindata().numInstances()); //test du nombre d'instances
	
	}
	
	@Test
	public void testwekaBuilder() throws Exception {
		WekaReader wr=new WekaReader();
		WekaFilter wf=new WekaFilter(wr);
		WekaBuilder wb=new WekaBuilder();
		assertNotNull(wb);
		//wb.setDefaultOption();
		assertNotSame(wb.getOneClassifier("RandomForest"),wb.getOneClassifier("J48"));
		wb.buildOneClassifiers("RandomForest", wf.getFTraindata());
		assertEquals(true,wb.getOneClassifier("RandomForest").getClass().isInstance(RandomForest.class.newInstance()));
		

	}
	@Test
	public void testwekaRunner() throws Exception {
		WekaReader wr=new WekaReader();
		WekaFilter wf=new WekaFilter(wr);
		WekaBuilder wb=new WekaBuilder();
		
		wb.setDefaultOption();
		wb.buildOneClassifiers("RandomForest", wf.getFTraindata());
		WekaRunner wru= new WekaRunner(wf.getAttribute());	
		assertNotNull(wru); // test si l'on a bien cr�� l'objet
		FastVector trainPred = wru.predict(wb.getOneClassifier("RandomForest"),wf.getFTraindata() , wf.getFTraindata());
		assertNotNull(trainPred); //test si la methode predic � bien fonctionn�
		double score = wru.getScore(trainPred, wf.getFTraindata());
		assertNotNull(score); //meme chose
		
		FastVector validPred = wru.predict(wb.getOneClassifier("RandomForest"),wf.getFTraindata() , wr.getValidData());
    	FastVector testPred = wru.predict(wb.getOneClassifier("RandomForest"),wf.getFTraindata() , wr.getTestData());
    	assertNotNull(validPred);
    	assertNotNull(validPred);
		wru.savePredictions(validPred, "valid.predict");// on sauvegarde les predictions
    	wru.savePredictions(testPred, "test.predict");
    	
	}
	

}
