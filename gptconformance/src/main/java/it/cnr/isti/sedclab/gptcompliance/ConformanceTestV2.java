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
 *  This XACML 2.0 conformation test.
 */
@RunWith(Parameterized.class)
public class ConformanceTestV2{

    @Rule		
    public ErrorCollector collector = new ErrorCollector();
    
    @Parameters
    public static Iterable<? extends Object> gpts() {
    	return Arrays.asList(TestConstants.GENERATOR_NONE,TestConstants.GENERATOR_CGPT,TestConstants.GENERATOR_CLAUDE,TestConstants.GENERATOR_GEMINI);
    	//return Arrays.asList("IIA016","IIB004");
    }
    

	@Parameter 
	public int gpt;
	   
    
    //private final  int[] GENERATORS = {TestConstants.GENERATOR_NONE,TestConstants.GENERATOR_CGPT,TestConstants.GENERATOR_CLAUDE,TestConstants.GENERATOR_COPILOT,TestConstants.GENERATOR_GEMINI};
    /**
     * Configuration store
     */
    private static ConfigurationStore store;

    /**
     * directory name that states the test type
     */
    private final static String ROOT_DIRECTORY  = "conformance";

    /**
     * the logger we'll use for all messages
     */
	private static final Log log = LogFactory.getLog(ConformanceTestV2.class);

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
    public void conformanceTestA() throws Exception {

        String policyNumber = "00";

        for(int i = 1; i < 22 ; i++){     

            //Some test are skipped due to errors (comportamento di default della suite di Balana)
            if(i == 2 || i == 4 || i == 14){
            //    log.info("Conformance Test IIA00" + i + " not started As required " + "attribute finder is not defined");
                continue;
            }
            if(i < 10){
                policyNumber = "00" + i;
            } else if(9 < i && i < 100) {
                policyNumber = "0" + i;
            } else {
                policyNumber = Integer.toString(i);
            }

            log.info("Using Generator: "+TestConstants.GENTYPE_MAP.get(gpt)+" - Conformance Test IIA" + policyNumber + " is started");

            String request = TestUtil.createRequest("IIA" + policyNumber + "Request.xml");
          
            if(request != null){
                // log.info("Request that is sent to the PDP :  " + request);
                Set<String> policies = new HashSet<String>();
                policies.add(policyNumber);                
                ResponseCtx response = TestUtil.evaluate(getPDPNewInstance(policies,gpt), request);
                if(response != null){
                    ResponseCtx expectedResponseCtx = TestUtil.createResponse("IIA" + policyNumber + "Response.xml");
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
            }
        log.info("Using Generator: "+TestConstants.GENTYPE_MAP.get(gpt)+" - Test IIA" + policyNumber + " is finished");
    }

@Ignore
    public void testConformanceTestB() throws Exception {

        String policyNumber;

        for(int i = 1; i < 54 ; i++){

            //Some test has been skipped due to errors
            if(i == 28 || i == 29){
                log.info("Conformance Test IIB00" + i + " does not started As required " +
                                                                "attribute finder is not defined");
                continue;
            }

            if(i < 10){
                policyNumber = "00" + i;
            } else if(9 < i && i < 100) {
                policyNumber = "0" + i;
            } else {
                policyNumber = Integer.toString(i);
            }

            log.info("Conformance Test IIB" + policyNumber + " is started");

            String request = TestUtil.createRequest("IIB" + policyNumber + "Request.xml");
            if(request != null){
                log.info("Request that is sent to the PDP :  " + request);
                Set<String> policies = new HashSet<String>();
                policies.add("IIB" + policyNumber + "Policy.xml");
                ResponseCtx response = TestUtil.evaluate(getPDPNewInstance(policies,gpt), request);
                if(response != null){
                    ResponseCtx expectedResponseCtx = TestUtil.createResponse("IIB" + policyNumber + "Response.xml");
                    log.info("Response that is received from the PDP :  " + response.encode());
                    if(expectedResponseCtx != null){
                        Assert.assertTrue(TestUtil.checkIfMatching(response, expectedResponseCtx));
                    } else {
                        Assert.assertTrue("Response read from file is Null",false);
                    }
                } else {
                    Assert.assertFalse("Response received PDP is Null",false);
                }
            } else {
                Assert.assertTrue("Request read from file is Null", false);
            }

            log.info("Conformance Test IIB" + policyNumber + " is finished");
        }
    }

    @Ignore
    public void testConformanceTestC() throws Exception {

        String policyNumber;

        for(int i = 1; i < 233 ; i++){
            //Some test has been skipped due to errors 54,55,88,89,92,93,98,99
            // 105 issue
            if(i == 3 || i==12 || i==14 || i ==23 || i==54 || i == 55 || i == 88  ||
                    i == 89 || i == 92 || i==93 || i == 98 || i == 99 || i == 105){
                log.info("Conformance Test IIC00" + i + " does not started As required " +
                                                                "attribute finder is not defined");
                continue;
            }

            if(i < 10){
                policyNumber = "00" + i;
            } else if(9 < i && i < 100) {
                policyNumber = "0" + i;
            } else {
                policyNumber = Integer.toString(i);
            }

            log.info("Conformance Test IIC" + policyNumber + " is started");

            String request = TestUtil.createRequest("IIC" + policyNumber + "Request.xml");
            if(request != null){
                log.info("Request that is sent to the PDP :  " + request);
                Set<String> policies = new HashSet<String>();
                policies.add("IIC" + policyNumber + "Policy.xml");
                ResponseCtx response = TestUtil.evaluate(getPDPNewInstance(policies,gpt), request);
                if(response != null){
                    ResponseCtx expectedResponseCtx = TestUtil.createResponse("IIC" + policyNumber + "Response.xml");
                    log.info("Response that is received from the PDP :  " + response.encode());
                    if(expectedResponseCtx != null){
                        Assert.assertTrue(TestUtil.checkIfMatching(response, expectedResponseCtx));
                    } else {
                        Assert.assertTrue("Response read from file is Null",false);
                    }
                } else {
                    Assert.assertFalse("Response received PDP is Null",false);
                }
            } else {
                Assert.assertTrue("Request read from file is Null", false);
            }

            log.info("Conformance Test IIC" + policyNumber + " is finished");
        }
    }

    @Ignore
    public void testConformanceTestD() throws Exception {

        String policyNumber;
        
        for(int i = 1; i < 29 ; i++){
            if(i < 10){
                policyNumber = "00" + i;
            } else if(9 < i && i < 100) {
                policyNumber = "0" + i;
            } else {
                policyNumber = Integer.toString(i);
            }

            log.info("Conformance Test IID" + policyNumber + " is started");

            String request = TestUtil.createRequest("IID" + policyNumber + "Request.xml");
            if(request != null){
                log.info("Request that is sent to the PDP :  " + request);
                Set<String> policies = new HashSet<String>();
                policies.add("IID" + policyNumber + "Policy.xml");
                //TestUtil.evaluate(getPDPNewInstance(policies), request);
                ResponseCtx response = TestUtil.evaluate(getPDPNewInstance(policies, gpt), request);
                if(response != null){
                    ResponseCtx expectedResponseCtx = TestUtil.createResponse("IID" + policyNumber + "Response.xml");
                    log.info("Response that is received from the PDP :  " + response.encode());
                    if(expectedResponseCtx != null){
                        Assert.assertTrue(TestUtil.checkIfMatching(response, expectedResponseCtx));
                    } else {
                        Assert.assertTrue("Response read from file is Null",false);
                    }
                } else {
                    Assert.assertFalse("Response received PDP is Null",false);
                }
            } else {
                Assert.assertTrue("Request read from file is Null", false);
            }

            log.info("Conformance Test IID" + policyNumber + " is finished");
        }
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
