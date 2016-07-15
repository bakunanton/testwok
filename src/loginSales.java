/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 import javax.xml.soap.*;
 import javax.xml.transform.*;
 import javax.xml.transform.stream.*;
 import javax.xml.parsers.*;
 import java.io.StringWriter;
/**
 *
 * @author Admin
 */
 
public class loginSales {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            String url = "https://login.salesforce.com/services/Soap/c/37.0";
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);

            // Process the SOAP Response
            printSOAPResponse(soapResponse);

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }
       
        
    }
        
        
        
        private static SOAPMessage createSOAPRequest() throws Exception{
        
      MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        
        String serverURI ="urn:enterprise.soap.sforce.com";
        
         // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("urn", serverURI);
        
        /*example soap message 
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:enterprise.soap.sforce.com">
   <soapenv:Body>
      <urn:login>
         <urn:username>?</urn:username>
         <urn:password>?</urn:password>
      </urn:login>
   </soapenv:Body>
</soapenv:Envelope>
        */
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("login", "urn");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("username");
        soapBodyElem1.addTextNode("testwork@free.com");
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("password");
        soapBodyElem2.addTextNode("123456testVxXFfdm9YS7Of6ShWC1GthIkT");

         MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI);

        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
        
        }
        
         private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        System.out.print("\nResponse SOAP Message = ");
        StreamResult result = new StreamResult(System.out);
        transformer.transform(sourceContent, result);
 
        
        
        
        
    }
        
}
    
    
