package it.cnr.isti.sedclab.gptcompliance;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.wso2.balana.Balana;
import org.wso2.balana.ConfigurationStore;
import org.wso2.balana.PDP;
import org.wso2.balana.PDPConfig;
import org.wso2.balana.ctx.AbstractResult;
import org.wso2.balana.ctx.ResponseCtx;
import org.wso2.balana.finder.PolicyFinder;
import org.wso2.balana.finder.PolicyFinderModule;
import org.wso2.balana.finder.impl.FileBasedPolicyFinderModule;



/**
 *  This XACML 2.0 conformation test.
 */
@RunWith(Parameterized.class)
public class Wso2TestSuite{

    @Rule		
    public ErrorCollector collector = new ErrorCollector();
    
    @Parameters
    public static Iterable<? extends Object> testPolicies() {
    	return Arrays.asList("wso2_1","wso2_2"/*,"wso2_3","wso2_4","wso2_5","wso2_6"*/);
    	//return Arrays.asList("IIA016","IIB004");
    }

    @Parameter 
	public String testPolicy; //la policy su cui testiamo
	
	
    /**
     * Configuration store
     */
    private static ConfigurationStore store;

    /**
     * the logger we'll use for all messages
     */
	private static final Log log = LogFactory.getLog(Wso2TestSuite.class);

   @BeforeClass
    public static void init() throws Exception {
try {
    	String configFile = TestConstants.CONFIG_FILE;
        store = new ConfigurationStore(new File(configFile));
}catch(Throwable t) {
	log.error(t.getCause().getMessage());
	t.getCause().getStackTrace();
}
    }
    
    

    @Test
    public void testPolicyNoGPT() throws Exception {

            String request = TestUtil.createRequest("request_"+testPolicy + "_1.xml");
          
            if(request != null){
            	log.info("**********************************");
            	log.info("request_"+testPolicy + "_1.xml");
                log.info("Request that is sent to the PDP :  " + request);
                log.info("**********************************");
                Set<String> policies = new HashSet<String>();
                policies.add(testPolicy);                
                ResponseCtx response = TestUtil.evaluate(getPDPNewInstance(policies,TestConstants.GENERATOR_NONE), request);
                if(response != null){
                	/*
                	while (response.getResults().iterator().hasNext()) {
                    	AbstractResult ar = response.getResults().iterator().next();
                    	log.info(ar.getDecision());
                    }*/
                	 log.info("Response that is received from the PDP :  " + response.encode());
                	/*
                    ResponseCtx expectedResponse = TestUtil.createExpectedResponse(testPolicy+ "_Response.xml");
                    if(expectedResponse != null){
                    	expectedResponse
		                    	try {
		                    		Assert.assertTrue(TestUtil.checkIfMatching(response, expectedResponse));
		                    	}catch(Throwable t) {
		                    		collector.addError(t);
		                    	}
				     }else{
				        	//donothing
				        	System.out.println("expectedResponse is null!!");
				        	Assert.assertTrue("Response received PDP is Null",false);
				       }
                    */
                }
                
            }else {
            	System.out.println("created request is null!!");
	        	Assert.assertTrue("created request is null",false);
	       }
        log.info("Test Using Generator: NONE for " + testPolicy + " is finished");
    }
    
    

    /**
     * Returns a new PDP instance with new XACML policies
     *
     * @param policies  Set of XACML policy file names
     * @return a  PDP instance
     */
    private static PDP getPDPNewInstance(Set<String> policies, int type){

        PolicyFinder finder= new PolicyFinder();
        Set<String> policyLocations = new HashSet<String>();
        
            for(String policy : policies){
                try {
                	 if(type == TestConstants.GENERATOR_NONE){
                		 policy = policy+"Policy.xml";
                	 }else {
                		 policy = policy+".xml";
                	 }
                	 
                    String policyPath = TestConstants.POLICYPATH_MAP.get(type) +policy;
                    policyLocations.add(policyPath);
                    FileBasedPolicyFinderModule testPolicyFinderModule = new FileBasedPolicyFinderModule(policyLocations);
                    Set<PolicyFinderModule> policyModules = new HashSet<PolicyFinderModule>();
                    policyModules.add(testPolicyFinderModule);
                    finder.setModules(policyModules);
                    
                } catch (Exception e) {
                   log.error("*** ERROR WHILE GETTING POLICY FILE");
                   log.error(e);
                }
            }

        Balana balana = Balana.getInstance();
        PDPConfig pdpConfig = balana.getPdpConfig();
        pdpConfig = new PDPConfig(pdpConfig.getAttributeFinder(), finder, pdpConfig.getResourceFinder(), false);
        return new PDP(pdpConfig);
        
    }
    
}
