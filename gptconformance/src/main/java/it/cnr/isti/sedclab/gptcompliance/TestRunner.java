package it.cnr.isti.sedclab.gptcompliance;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class TestRunner {
	private static final Log log = LogFactory.getLog(TestRunner.class);
	
	public static void main(String[] args) {
		 Result result = JUnitCore.runClasses(ConformanceTestV2.class);					
			for (Failure failure : result.getFailures()) {							
      log.info("!Failure!...."+failure.toString());					
   }		
   
   log.info("---> TestRunner - total run count: " + result.getRunCount());	
   log.info("---> TestRunner - total failures: " + result.getFailureCount());	
  // log.info("---> TestRunner - total assumption failures: "+ result.getAssumptionFailureCount());	
   log.info("---> TestRunner was successful ? "+ result.wasSuccessful());	
}	

}
