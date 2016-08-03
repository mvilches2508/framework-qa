/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author mario.vilches
 */
public enum AVExceptionCode {
    
    AV_0001(1,"Error al ..."),
    AV_0002(2,"Error al ...."),
    AV_0003(3,"Error al ..."),
    AV_0004(4,"Error al ...");
    
    private int id;
    private String message;
    
    private AVExceptionCode(int id, String message){
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
