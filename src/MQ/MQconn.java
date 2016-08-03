package MQ;


import Util.File_Propiedades;
import Util.TipoFile;
import com.ibm.mq.*;
import java.util.Properties;

/**
 *
 * @author mario.vilches
 */
public class MQconn {
    Properties propiedades = new File_Propiedades().getProperties(TipoFile.config); 
    private MQQueueManager mqconnecion = null;
    private  String qMgrName = propiedades.getProperty("qMgrName"); //BROKER MQ
    private  MQQueue myQueue; 
    
      
    public void MQconnectar() throws Exception
    {
        mqconnecion = new MQQueueManager(qMgrName);
        System.out.println("conexion establecida...");
        
    }
    public void MQDesconectar() throws Exception
    {
        mqconnecion.disconnect();
    }
    public void AbrirQueue(String cola) throws Exception
    {
        int opcionDeAbrir = MQC.MQOO_OUTPUT; // MQC.MQOO_INPUT_SHARED 
        myQueue = mqconnecion.accessQueue(cola, opcionDeAbrir); 
        System.out.println("Abriendo cola ... " + cola);                                                       
    }
    public void cerrarQueue() throws Exception
    {
        myQueue.close();
    }
    public void inyectarMSG(String mensajeMT, String cola) throws Exception
    {
        MQEnvironment.hostname = propiedades.getProperty("hostname");
        MQEnvironment.port = Integer.parseInt(propiedades.getProperty("portMQ")); 
        MQEnvironment.channel = propiedades.getProperty("channel"); 
        
        MQconnectar();
        AbrirQueue(cola);
        
        
        MQMessage mensaje = new MQMessage();
        mensaje.messageType = MQC.MQMT_DATAGRAM;
        mensaje.format = MQC.MQFMT_STRING;
        mensaje.messageId = MQC.MQMI_NONE;
        mensaje.characterSet = 819;
        
        mensaje.writeString(mensajeMT);
        System.out.println("Poniendo mensaje <" + mensajeMT.substring(0, 45) + "> en cola "); 
        int tamanoMsg = mensaje.getMessageLength(); 
        System.out.println("El tamaño del mensaje es <" + tamanoMsg + ">"); 
        MQPutMessageOptions OpcionPut = new MQPutMessageOptions(); 
        myQueue.put(mensaje,OpcionPut);
        
        System.out.println("cerrar cola" + cola);
        cerrarQueue();
        System.out.println("desconectando de MQ");
        MQDesconectar();               
    }
    
}
