import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.FastVector;

public class JunitWeka {

	//@Test
	public void testwekareader() throws Exception {
		WekaReader wr=new WekaReader();
		
		assertNotNull(wr);// on test si l'objet à bien été créé
		
		assertNotNull(wr.getAttribute()); //on test que l'objet attribut 
		
		assertNotSame(wr.getTestData(),wr.getTrainData()); //on test si les instances sont différentes
		
		
	}
//	@Test
	
	public void testwekaFilter() throws Exception {
	
		WekaReader wr=new WekaReader();
		WekaFilter wf=new WekaFilter(wr);
		assertNotNull(wf);
		assertNotNull(wf.getAttribute());//test attribut non nul 
		/* 
		assertNotSame(wr.getTrainData(),wf.getFTraindata()); //test si les instance filtré sont les méme que non filtré
		assertNotSame(wr.getTrainData().numInstances(),wf.getFTraindata().numInstances()); //test du nombre d'instances
	*/
	}
	
	//@Test
	public void testwekaBuilder() throws Exception {
		WekaReader wr=new WekaReader();
		WekaFilter wf=new WekaFilter(wr);
		WekaBuilder wb=new WekaBuilder();
		assertNotNull(wb);
		assertNotSame(wb.getOneClassifier("RandomForest"),wb.getOneClassifier("J48"));
		wb.buildOneClassifiers("RandomForest", wr.getTrainData());
		assertEquals(true,wb.getOneClassifier("RandomForest").getClass().isInstance(RandomForest.class.newInstance()));
		

	}
	@Test
	public void testwekaRunner() throws Exception {
		WekaReader wr=new WekaReader();
			WekaFilter wf=new WekaFilter(wr); 
			WekaBuilder wb=new WekaBuilder();
			String[] option={"-do-not-check-capabilities"};
		
			wb.buildOneFClassifiers("BayesNet", wr.getTrainData(),wf.getFilter() );
			//wb.buildOneClassifiers("BayesNet", wr.getTrainData());
			WekaRunner wru= new WekaRunner(wr.getAttribute());
			assertNotNull(wru); // test si l'on a bien créé l'objet
			//FastVector trainPred = wru.predict(wb.getOneClassifier("NBayesS"),wf.getFTraindata() , wf.getFTraindata());
			FastVector trainPred = wru.predict(wb.getOneClassifier("FC"),wr.getTrainData() , wr.getTrainData());
			
			assertNotNull(trainPred); //test si la methode predic à bien fonctionné
			double score = wru.getScore(trainPred, wr.getTrainData());
			System.out.println("score train: "+score);
			//double score = wru.getScore(trainPred, wf.getFTraindata());
			
			assertNotNull(score); //meme chose
			FastVector validPred = wru.predict(wb.getOneClassifier("FC"),wr.getTrainData() , wr.getValidData());
	    	FastVector testPred = wru.predict(wb.getOneClassifier("FC"),wr.getTrainData() , wr.getTestData());

		  /*FastVector validPred = wru.predict(wb.getOneClassifier("NBayesS"),wf.getFTraindata() , wr.getValidData());
	    	FastVector testPred = wru.predict(wb.getOneClassifier("NBayesS"),wf.getFTraindata() , wr.getTestData());

			FastVector validPred = wru.predict(wb.getOneClassifier("NBayesS"),wf.getFTraindata() , wf.getFValiddata());
	    	FastVector testPred = wru.predict(wb.getOneClassifier("NBayesS"),wf.getFTraindata() , wf.getFTestdata());
	    */
	    	assertNotNull(validPred);
	    	assertNotNull(validPred);
			wru.savePredictions(validPred, "valid.predict");// on sauvegarde les predictions
	    	wru.savePredictions(testPred, "test.predict");
	    	wru.zipPredictions(); //
	}
	

}
