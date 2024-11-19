/*
 *  Copyright (c) WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package it.cnr.isti.sedclab.gptcompliance;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Constants related to tests are there.
 */
public class TestConstants {

   // public static final String RESOURCE_PATH =  "src" + File.separator + "test" + File.separator + "resources" + File.separator;
   public static final String RESOURCE_PATH =  "src" + File.separator + "main" + File.separator + "resources" + File.separator;
   //official policies
   public static final String OFFICIAL_POLICIES_PATH =  RESOURCE_PATH+"policies-official"+File.separator;
   public static final String W2O_OFFICIAL_POLICIES_PATH =  RESOURCE_PATH+"policies-WSO2"+File.separator;
   public static final String GEN_POLICIES_PATH =  RESOURCE_PATH+"policies-gpt"+File.separator;
   public static final String CHATGPT_POLICY_PATH = GEN_POLICIES_PATH+"ChatGPT"+File.separator;
   public static final String CLAUDE_POLICY_PATH = GEN_POLICIES_PATH+"Claude"+File.separator;
   public static final String COPILOT_POLICY_PATH = GEN_POLICIES_PATH+"CoPilot"+File.separator;
   public static final String GEMINI_POLICY_PATH = GEN_POLICIES_PATH+"Gemini"+File.separator;

    public static final String REQUESTS_PATH =  RESOURCE_PATH+"requests"+File.separator;
    public static final String RESPONSES_PATH =  RESOURCE_PATH+"responses"+File.separator;
    
    public static final int GENERATOR_NONE =  0;
    public static final int GENERATOR_CGPT =  1;
    public static final int GENERATOR_GEMINI =  2;
    public static final int GENERATOR_COPILOT =  3;
    public static final int GENERATOR_CLAUDE =  4;
    
    public static Map<Integer, String> GENTYPE_MAP;
    static {
    	GENTYPE_MAP = new HashMap<>();
    	for(int i=0; i<5; i++) {
    		if (i==GENERATOR_NONE)GENTYPE_MAP.put(i, "REFERENCE");
    		if (i==GENERATOR_CGPT) GENTYPE_MAP.put(i, "GENERATOR_CGPT");
    		if (i==GENERATOR_GEMINI) GENTYPE_MAP.put(i, "GENERATOR_GEMINI");
    		if (i==GENERATOR_COPILOT) GENTYPE_MAP.put(i, "GENERATOR_COPILOT");
    		if (i==GENERATOR_CLAUDE) GENTYPE_MAP.put(i, "GENERATOR_CLAUDE");
    	}
    }
    
    public static Map<Integer, String> POLICYPATH_MAP;
    static {
    	POLICYPATH_MAP = new HashMap<>();
    	for(int i=0; i<5; i++) {
    		if (i==GENERATOR_NONE)POLICYPATH_MAP.put(i, OFFICIAL_POLICIES_PATH);
    		if (i==GENERATOR_CGPT) POLICYPATH_MAP.put(i, CHATGPT_POLICY_PATH);
    		if (i==GENERATOR_GEMINI) POLICYPATH_MAP.put(i, GEMINI_POLICY_PATH);
    		if (i==GENERATOR_COPILOT) POLICYPATH_MAP.put(i, COPILOT_POLICY_PATH);
    		if (i==GENERATOR_CLAUDE) POLICYPATH_MAP.put(i, CLAUDE_POLICY_PATH);
    	}
    }
    
    //TODO: aggiustare questo (se serve)
    /*
    public static Map<Integer, String> W2O_POLICYPATH_MAP;
    static {
    	W2O_POLICYPATH_MAP = new HashMap<>();
    	for(int i=0; i<5; i++) {
    		if (i==GENERATOR_NONE)POLICYPATH_MAP.put(i, OFFICIAL_POLICIES_PATH);
    		if (i==GENERATOR_CGPT) POLICYPATH_MAP.put(i, CHATGPT_POLICY_PATH);
    		if (i==GENERATOR_GEMINI) POLICYPATH_MAP.put(i, GEMINI_POLICY_PATH);
    		if (i==GENERATOR_COPILOT) POLICYPATH_MAP.put(i, COPILOT_POLICY_PATH);
    		if (i==GENERATOR_CLAUDE) POLICYPATH_MAP.put(i, CLAUDE_POLICY_PATH);
    	}
    }
    */

    public static final String CONFIG_FILE =  RESOURCE_PATH + "config.xml";
}
