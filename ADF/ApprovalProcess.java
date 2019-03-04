package com.view.approval;

import com.view.utils.JSFUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.StringReader;

import java.text.SimpleDateFormat;

import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import oracle.adf.share.ADFContext;

import org.w3c.dom.Document;

import org.xml.sax.InputSource;

public class ApprovalProcess {
    
    public ApprovalProcess() {
        super();
    }
    
    public static String[] invokeWsdl(String xmlData, String wsdl) {
        String [] status=new String[2];
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("text/xml");
            xmlData = xmlData.replaceAll("&", "&amp;");
      //  System.err.println("===XML========="+xmlData);
            RequestBody body = RequestBody.create(mediaType, xmlData);
            Request request =
           new Request.Builder().url(wsdl).post(body).addHeader("content-type","text/xml").addHeader("cache-control",
                                                                                                                                                                                                                "no-cache").build();

            Response response = client.newCall(request).execute();


            InputStream isr = response.body().byteStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr));
            StringBuilder out1 = new StringBuilder();
            String resultsXml1;
            while ((resultsXml1 = reader.readLine()) != null) {
                out1.append(resultsXml1);
            }

            reader.close();

            

            String jsonVal = out1.toString();
            System.out.println("---"+jsonVal);   
            int code = response.code();
            System.err.println("code==>"+code);
            if (code == 200) {
                String ID = getID(jsonVal);

                System.out.println("ID    :" + ID);
                status[0]="True";
                status[1]=ID;
    //                return status;
    //                status="true";
    //                ADFContext.getCurrent().getSessionScope().put("errorMsg", ID);
                }else if(code==202){
                    status[0]="True";
                    status[1]="Success";
                }else {
                String fault = getFault(jsonVal);
                System.out.println("Fault    :" + fault);
    //                status="false";
    //                ADFContext.getCurrent().getSessionScope().put("errorMsg", fault);
                status[0]="False";
                status[1]=fault;
                JSFUtils.addFacesInformationMessage("status[0]"+status[0]); 
                JSFUtils.addFacesInformationMessage("status[1]"+status[1]); 
            }


        } catch (Exception e) {
            System.out.println(e);
        
        }
        return status;
    }
    
    public static String getID(String xml) {
        String ID = "";
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(xml));
            Document doc = builder.parse(src);
            ID = doc.getElementsByTagName("Id").item(0).getTextContent();

        } catch (Exception e) {

        }
        return ID;
    }
    
    public static String getFault(String xml) {
        String faultString = "";
        try {
            System.err.println("==ERROR XML===" + xml);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(xml));
            Document doc = builder.parse(src);
            faultString = doc.getElementsByTagName("faultstring").item(0).getTextContent();

        } catch (Exception e) {

        }
        return faultString;
    }
    
    public static String[] payloadHeader(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000'Z'"); //Hours:Minutes:Seconds
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        java.util.Date date = new java.util.Date();
        long t = date.getTime();
        java.util.Date expDate ;
        expDate = new java.util.Date(t + (10 * 600));

        java.util.Date plusOne;
//        String createdTS = dateFormat.format(date);
//        String expiresTS = dateFormat.format(expDate);
//        String username="praveen.c@4iapps.com";
//        String password="Welcome#1234";
         String [] headerInfo=new String[4];
        headerInfo[0]=dateFormat.format(date);
        headerInfo[1]="sainadh.d@4iapps.com";
        headerInfo[2]="Welcome89101";
        headerInfo[3]=dateFormat.format(expDate);
        return headerInfo;
    }
    
    
    
    
    public static String  milstoneXMLData(String p_AMOUNT, String p_FUNC_ID,String p_LEVEL_NO,
                                          String p_PK_COLUMN,String p_REF_ID,
                                          String p_STATUS_COLUMN,String p_TABLE_NAME,
                                          String p_ATTRIBUTE1,String p_ATTRIBUTE2){
        String[] info=ApprovalProcess.payloadHeader();
        System.err.println("Created time===>"+info[0]);
        System.err.println("User===========>"+info[1]);
        System.err.println("Password=======>"+info[2]);
        System.err.println("End time=======>"+info[3]);
       String xmlData="<soapenv:Envelope xmlns:ini=\"http://xmlns.oracle.com/bpm/forms/schemas/IniitlalWebForm\" xmlns:pay=\"http://xmlns.oracle.com/bpmn/bpmnCloudProcess/PRISMApprovalFlow/PaymentPlanProcess\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" + 
       "   <soapenv:Header>\n" + 
       "      <wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" + 
       "         <wsse:UsernameToken wsu:Id=\"UsernameToken-D2159639A45059896015499639444162\">\n" + 
       "            <wsse:Username>"+info[1]+"</wsse:Username>\n" + 
       "            <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">"+info[2]+"</wsse:Password>\n" + 
       "            <wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">gKQaFu+W6AGLfN1x+AIMzA==</wsse:Nonce>\n" + 
       "            <wsu:Created>"+info[0]+"</wsu:Created>\n" + 
       "         </wsse:UsernameToken>\n" + 
       "         <wsu:Timestamp wsu:Id=\"TS-D2159639A45059896015499638914331\">\n" + 
       "            <wsu:Created>"+info[0]+"</wsu:Created>\n" + 
       "            <wsu:Expires>"+info[3]+"</wsu:Expires>\n" + 
       "         </wsu:Timestamp>\n" + 
       "      </wsse:Security>\n" + 
       "   </soapenv:Header>\n" + 
       "   <soapenv:Body>\n" + 
       "      <pay:start>\n" + 
       "         <formArg>\n" + 
       "            <ini:p_AMOUNT>"+p_AMOUNT+"</ini:p_AMOUNT>\n" + 
       "            <ini:p_ATTRIBUTE1>"+p_ATTRIBUTE1+"</ini:p_ATTRIBUTE1>\n" + 
       "            <ini:p_ATTRIBUTE2>"+p_ATTRIBUTE2+"</ini:p_ATTRIBUTE2>\n" + 
       "            <ini:p_FUNC_ID>"+p_FUNC_ID+"</ini:p_FUNC_ID>\n" + 
       "            <ini:p_LEVEL_NO>"+p_LEVEL_NO+"</ini:p_LEVEL_NO>\n" + 
       "            <ini:p_PK_COLUMN>"+p_PK_COLUMN+"</ini:p_PK_COLUMN>\n" + 
       "            <ini:p_REF_ID>"+p_REF_ID+"</ini:p_REF_ID>\n" + 
       "            <ini:p_STATUS_COLUMN>"+p_STATUS_COLUMN+"</ini:p_STATUS_COLUMN>\n" + 
       "            <ini:p_TABLE_NAME>"+p_TABLE_NAME+"</ini:p_TABLE_NAME>\n" + 
       "         </formArg>\n" + 
       "      </pay:start>\n" + 
       "   </soapenv:Body>\n" + 
       "</soapenv:Envelope>";
      return xmlData;
    }
    
    
}
