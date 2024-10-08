***************************************
******** conformance tests def.	*******
***************************************

XACML (eXtensible Access Control Markup Language) conformance tests are standardized test suites designed to verify that implementations of XACML correctly adhere to the XACML specification. 
These tests help ensure interoperability between different XACML implementations and validate that a given implementation correctly interprets and processes XACML policies and requests.
The official XACML conformance tests are maintained by the Organization for the Advancement of Structured Information Standards (OASIS), which is the standards body responsible for XACML. 
You can find these tests in the following locations:

OASIS XACML GitHub Repository:
The most up-to-date conformance tests are typically available in the official OASIS XACML GitHub repository. You can find them at:
https://github.com/oasis-tcs/xacml-tests
OASIS XACML Technical Committee Page:
The OASIS XACML Technical Committee page may also provide links to conformance test suites for different versions of XACML. You can check their page at:
https://www.oasis-open.org/committees/tc_home.php?wg_abbrev=xacml

These conformance tests usually include a set of XACML policies, requests, and expected results. Implementers can use these tests to verify their XACML engine's compliance with the standard.
It's worth noting that different versions of XACML (e.g., 2.0, 3.0) may have different conformance test suites. Make sure to use the appropriate test suite for the XACML version you're implementing or testing against.

****************************
***PURPOSE OF THIS PROJECT***
****************************
Natual language prompts were given to 4 different GPTs (Generative Pre-trained Transformers) to get the corresponding XACML policies. The prompts were extracted from the OASIS conformance tests.
In this project, I have slightly modified the official conformance tests shipped within the Balana bundle in order to check whether the generated policies are compliant.