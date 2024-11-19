package it.cnr.isti.sedclab.gptcompliance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import org.wso2.balana.ParsingException;
import org.wso2.balana.ctx.AbstractResult;
import org.wso2.balana.ctx.AttributeAssignment;
import org.wso2.balana.ctx.ResponseCtx;
import org.wso2.balana.finder.PolicyFinder;
import org.wso2.balana.finder.PolicyFinderModule;
import org.wso2.balana.finder.impl.FileBasedPolicyFinderModule;
import org.wso2.balana.xacml3.Advice;



/**
 *  Pattern per i risultati: 1-Permit/2-Permit/3-Deny/4-Deny
 */
@RunWith(Parameterized.class)
public class Wso2TestSuite{

    @Rule		
    public ErrorCollector collector = new ErrorCollector();
    
    @Parameters
    public static Iterable<? extends Object> testPolicies() {
    	return Arrays.asList(/*"wso2_1","wso2_2","wso2_3","wso2_4","wso2_5",*/"wso2_6");
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
//PERMIT
    	   String reqName = "request_"+testPolicy + "_1.xml";
            String request = TestUtil.createRequest(reqName);
          
            if(request != null){
            	log.info("**********************************");
            	log.info(reqName);
              //  log.info("Request that is sent to the PDP :  " + request);
                log.info("**********************************");
                Set<String> policies = new HashSet<String>();
                policies.add(testPolicy);   

                ResponseCtx responseCtx = TestUtil.evaluate(getPDPNewInstance(policies,TestConstants.GENERATOR_NONE), request);
                AbstractResult result  = responseCtx.getResults().iterator().next();
               ////watchResult(result);
                assertEquals(AbstractResult.DECISION_PERMIT , result.getDecision());
                if(responseCtx != null){
                	 //log.info("Response that is received from the PDP :  " + responseCtx.encode());
                }
                
            }else {
            	System.out.println("created request is null!!");
	        	Assert.assertTrue("created request is null",false);
	       }
        log.info("Test Using Generator: NONE for " + testPolicy + " is finished");
    }
    

    @Test
    public void testPolicyNoGPT2() throws Exception {
    	//PERMIT
            String reqName = "request_"+testPolicy + "_2.xml";
            String request = TestUtil.createRequest(reqName);
          
            if(request != null){
            	log.info("**********************************");
            	log.info(reqName);
                log.info("**********************************");
                Set<String> policies = new HashSet<String>();
                policies.add(testPolicy);                
                ResponseCtx responseCtx = TestUtil.evaluate(getPDPNewInstance(policies,TestConstants.GENERATOR_NONE), request);
                if(responseCtx != null){
                    AbstractResult result  = responseCtx.getResults().iterator().next();
                    //watchResult(result);
                    assertEquals(AbstractResult.DECISION_PERMIT , result.getDecision());
                    if(responseCtx != null){
                    	 //log.info("Response that is received from the PDP :  " + responseCtx.encode());
                    }
                }
                
            }else {
            	System.out.println("created request is null!!");
	        	Assert.assertTrue("created request is null",false);
	       }
        log.info("Test Using Generator: NONE for " + testPolicy + " is finished");
    }
  
    @Test
    public void testPolicyNoGPT3() throws Exception {
    	//DENY

        String reqName = "request_"+testPolicy + "_3.xml";
        String request = TestUtil.createRequest(reqName);
            
        if(request != null){
            	log.info("**********************************");
            	log.info(reqName);
                log.info("**********************************");
                Set<String> policies = new HashSet<String>();
                policies.add(testPolicy);                
                ResponseCtx responseCtx = TestUtil.evaluate(getPDPNewInstance(policies,TestConstants.GENERATOR_NONE), request);
                if(responseCtx != null){
                    AbstractResult result  = responseCtx.getResults().iterator().next();
                    //watchResult(result);
                    assertEquals(AbstractResult.DECISION_DENY , result.getDecision());
                    if(responseCtx != null){
                    	 //log.info("Response that is received from the PDP :  " + responseCtx.encode());
                    }
                }
                
            }else {
            	System.out.println("created request is null!!");
	        	Assert.assertTrue("created request is null",false);
	       }
        log.info("Test Using Generator: NONE for " + testPolicy + " is finished");
    }
    
    @Test
    public void testPolicyNoGPT4() throws Exception {
    	//DENY
        String reqName = "request_"+testPolicy + "_4.xml";
        String request = TestUtil.createRequest(reqName);
          
            if(request != null){
                Set<String> policies = new HashSet<String>();
                policies.add(testPolicy);                
                ResponseCtx responseCtx = TestUtil.evaluate(getPDPNewInstance(policies,TestConstants.GENERATOR_NONE), request);
                if(responseCtx != null){
                    AbstractResult result  = responseCtx.getResults().iterator().next();
                    //watchResult(result);
                    assertEquals(AbstractResult.DECISION_DENY , result.getDecision());
                }
                
            }else {
	        	log.error("request is null!");
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
                	/*
                	 if(type == TestConstants.GENERATOR_NONE){
                		 policy = policy+"Policy.xml";
                	 }else {
                		 policy = policy+".xml";
                	 }
                	 */
                	 policy = policy+".xml";
                	 
                   // String policyPath = TestConstants.POLICYPATH_MAP.get(type) +policy;
                   String policyPath = TestConstants.W2O_OFFICIAL_POLICIES_PATH +policy;
                    policyLocations.add(policyPath);
                    FileBasedPolicyFinderModule testPolicyFinderModule = new FileBasedPolicyFinderModule(policyLocations);
                    Set<PolicyFinderModule> policyModules = new HashSet<PolicyFinderModule>();
                    policyModules.add(testPolicyFinderModule);
                    finder.setModules(policyModules);
                    
                } catch (Exception e) {
                   System.out.println("*** ERROR WHILE GETTING POLICY FILE");
                   System.out.println(e);
                }
            }

        Balana balana = Balana.getInstance();
        PDPConfig pdpConfig = balana.getPdpConfig();
        pdpConfig = new PDPConfig(pdpConfig.getAttributeFinder(), finder, pdpConfig.getResourceFinder(), false);
        return new PDP(pdpConfig);
        
    }
    
    private static void watchResult(AbstractResult result) {
    	 System.out.println(result.getStatus().encode()); 
         
         if(AbstractResult.DECISION_PERMIT == result.getDecision()){
         	System.out.println("DECISION_PERMIT");
         }
         if(AbstractResult.DECISION_DENY == result.getDecision()){
         	System.out.println("DECISION_DENY");
         }
         if(AbstractResult.DECISION_INDETERMINATE == result.getDecision()){
         	System.out.println("DECISION_INDETERMINATE");
         }
         if(AbstractResult.DECISION_INDETERMINATE_DENY== result.getDecision()){
         	System.out.println("DECISION_INDETERMINATE_DENY");
         }
         if(AbstractResult.DECISION_INDETERMINATE_PERMIT == result.getDecision()){
         	System.out.println("DECISION_INDETERMINATE_PERMIT");
         }
         if(AbstractResult.DECISION_INDETERMINATE_DENY_OR_PERMIT == result.getDecision()){
         	System.out.println("DECISION_INDETERMINATE_DENY_OR_PERMIT");
         }
         if(AbstractResult.DECISION_NOT_APPLICABLE == result.getDecision()){
         	System.out.println("AbstractResult.DECISION_NOT_APPLICABLE");
         }
    }
}
