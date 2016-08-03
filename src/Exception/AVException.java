
package Exception;

/**
 *
 * @author mario.vilches
 */
public class AVException extends Exception {

    
    private AVExceptionCode avExceptionCode;
   
    public AVException() {
    }

    
    public AVException(String msg) {
        super(msg);
    }
    
    
    public AVException(AVExceptionCode avExceptionCode){
        this.avExceptionCode = avExceptionCode;
    }
    
    public AVException(AVExceptionCode avExceptionCode, Throwable cause){
        super(cause);
        this.avExceptionCode = avExceptionCode;
    }

    public AVExceptionCode getIsoExceptionCode() {
        return avExceptionCode;
    }
    
    @Override
    public String getMessage(){
        return this.avExceptionCode.getMessage();
    }
    
}
