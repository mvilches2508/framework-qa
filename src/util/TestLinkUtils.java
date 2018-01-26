package util;

import testlink.api.java.client.TestLinkAPIClient;
import testlink.api.java.client.TestLinkAPIException;
import testlink.api.java.client.TestLinkAPIResults;

public class TestLinkUtils {
    
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    

    // Replace Testlink API Interface access key here
    public final static String ACCESS_KEY = "15c90ca45e4b03b9fce0a567970d583c";

    // Replace your TestLink Server URL here
    public final static String TESTLINK_SERVER_URL = "http://172.20.4.81/gpd_av/test/lib/api/xmlrpc/v1/xmlrpc.php";

    // Replace Project Name here
    public final static String TESTLINK_PROJECT_NAME = "Demo";

    // Replace your Test Plan here
    public final static String TESTLINK_TESTPLAN_NAME = "plan1";

    // Replace your Build Name here
    public final static String BUILD_RELEASE_NAME = "ciclo1";

    public static void reportResult(String testCaseID, String executionNotes, String result) throws TestLinkAPIException {
        TestLinkAPIClient apiClient = new TestLinkAPIClient(ACCESS_KEY, TESTLINK_SERVER_URL);
        apiClient.reportTestCaseResult(TESTLINK_PROJECT_NAME, TESTLINK_TESTPLAN_NAME, testCaseID, BUILD_RELEASE_NAME, executionNotes, result);
    }

    public static void getProject() throws TestLinkAPIException {
        TestLinkAPIClient apiClient = new TestLinkAPIClient(ACCESS_KEY, TESTLINK_SERVER_URL);
        apiClient.createBuild(TESTLINK_PROJECT_NAME, TESTLINK_TESTPLAN_NAME, "CICLO2", "NOTA DESDE JAVITA");
        apiClient.createTestCase("JAVITA", TESTLINK_PROJECT_NAME, TESTLINK_TESTPLAN_NAME, "casoDesdeJava", "este caso es un ejemplo que viene desde java que hizo mario", "1 blabla", "Se espera que cargue en el testlink", "alta");
    }
    
    public void evidenciaPDF(String testCaseID, String executionNotes, String result){
        
        
    }
    
}


