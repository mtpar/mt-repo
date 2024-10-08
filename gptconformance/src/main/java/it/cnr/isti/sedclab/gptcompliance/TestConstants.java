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

/**
 * Constants related to tests are there.
 */
public class TestConstants {

   // public static final String RESOURCE_PATH =  "src" + File.separator + "test" + File.separator + "resources" + File.separator;
   public static final String RESOURCE_PATH =  "src" + File.separator + "main" + File.separator + "resources" + File.separator;
   public static final String GEN_POLICIES_PATH =  RESOURCE_PATH+"policies-gpt"+File.separator;
   public static final String CHATGPT_POLICY_PATH = GEN_POLICIES_PATH+"ChatGPT"+File.separator;
   public static final String CLAUDE_POLICY_PATH = GEN_POLICIES_PATH+"Claude"+File.separator;
   public static final String COPILOT_POLICY_PATH = GEN_POLICIES_PATH+"CoPilot"+File.separator;
   public static final String GEMINI_POLICY_PATH = GEN_POLICIES_PATH+"Gemini"+File.separator;

    public static final String REQUESTS_PATH =  RESOURCE_PATH+"requests"+File.separator;
    public static final String RESPONSES_PATH =  RESOURCE_PATH+"responses"+File.separator;

    public static final String CONFIG_FILE =  RESOURCE_PATH + "config.xml";
}
