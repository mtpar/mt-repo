package it.cnr.isti.sedclab.gptcompliance;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
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
import org.wso2.balana.ctx.ResponseCtx;
import org.wso2.balana.finder.PolicyFinder;
import org.wso2.balana.finder.PolicyFinderModule;
import org.wso2.balana.finder.impl.FileBasedPolicyFinderModule;



/**
 *  This XACML 2.0 conformance test.
 */
public class SingleTestV2{

    @Rule		
    public ErrorCollector collector = new ErrorCollector();
    
    //private final  int[] GENERATORS = {TestConstants.GENERATOR_NONE,TestConstants.GENERATOR_CGPT,TestConstants.GENERATOR_CLAUDE,TestConstants.GENERATOR_COPILOT,TestConstants.GENERATOR_GEMINI};
    /**
     * Configuration store
     */
    private static ConfigurationStore store;


    /**
     * the logger we'll use for all messages
     */
	private static final Log log = LogFactory.getLog(SingleTestV2.class);

   @BeforeClass
    public static void init() {
	   try {
	    	String configFile = TestConstants.CONFIG_FILE;
	        store = new ConfigurationStore(new File(configFile));
	   }catch(Exception e) {
		   e.printStackTrace();
		   log.debug(e, e);
	   }

    }
    
    
    @Ignore
    public void conformanceTestOfficial() throws Exception {

        String shortName = "IIA016";

            log.info(" - Conformance Test " + shortName + " is started");

            String request = TestUtil.createRequest(shortName + "Request.xml");
          
            if(request != null){
                // log.info("Request that is sent to the PDP :  " + request);
                Set<String> policies = new HashSet<String>();
                policies.add(shortName);                
                ResponseCtx response = TestUtil.evaluate(getPDPNewInstance(policies,TestConstants.GENERATOR_NONE), request);
                if(response != null){
                    ResponseCtx expectedResponseCtx = TestUtil.createResponse(shortName + "Response.xml");
                    log.info("Response that is received from the PDP :  " + response.encode());
                    if(expectedResponseCtx != null){
		                    	try {
		                    		Assert.assertTrue(TestUtil.checkIfMatching(response, expectedResponseCtx));
		                    	}catch(Throwable t) {
		                    		collector.addError(t);
		                    	}
				     }else{
				        	//donothing
				        	System.out.println("expectedResponseCtx is null!!");
				        	Assert.assertTrue("Response received PDP is Null",false);
				       }
                }
            }else {
            	System.out.println("created request is null!!");
	        	Assert.assertTrue("created request is null",false);
	       }
        log.info("Test for" + shortName + " is finished");
    }
    
    
    @Test
    public void conformanceTestGpt() throws Exception {
    	
    	  String shortName = "Claude_Special1_";
    	
    	String request = TestUtil.createRequest(shortName + "Request.xml");
    	
    	if(request != null){
    		// log.info("Request that is sent to the PDP :  " + request);
    		Set<String> policies = new HashSet<String>();
    		policies.add(shortName);                
    		ResponseCtx response = TestUtil.evaluate(getPDPNewInstance(policies,TestConstants.GENERATOR_CLAUDE), request);
    		if(response != null){
    			//IF 
    			//ResponseCtx expectedResponseCtx = TestUtil.createResponse(shortName + "Response.xml");
    			// IIA001Response.xml è per quando la policy PASSA
    			ResponseCtx expectedResponseCtx = TestUtil.createResponse("IIA001" + "Response.xml");
    			log.info("****Response from the PDP :  " + response.encode());
    			if(expectedResponseCtx != null){
    				try {
    					Assert.assertTrue(TestUtil.checkIfMatching(response, expectedResponseCtx));
    				}catch(Throwable t) {
    					collector.addError(t);
    				}
    			}else{
    				//donothing
    				System.out.println("expectedResponseCtx is null!!");
    				Assert.assertTrue("Response received PDP is Null",false);
    			}
    		}
    	}else {
    		System.out.println("created request is null!!");
    		Assert.assertTrue("created request is null",false);
    	}
    	log.info("Test for" + shortName + " is finished");
    }

    /**
     * Returns a new PDP instance with new XACML policies
     *
     * @param policies  Set of XACML policy file 'SHORT' names (e.g. 'IIA001' - without 'Policy.xml' or 'xml')
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
