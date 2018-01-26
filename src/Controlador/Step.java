package Controlador;

public class Step {
    private int idStep;
    private String descripcion;
    private String comando;
    private String valor;
    private String atributo;
    private String tipoLocalizador;
    

    public Step() {
    }

    public Step(int idStep, String descripcion, String comando, String valor, String atributo, String tipoLocalizador) {
        this.idStep = idStep;
        this.descripcion = descripcion;
        this.comando = comando;
        this.valor = valor;
        this.atributo = atributo;
        this.tipoLocalizador = tipoLocalizador;
    }
    public int getIdStep() {
        return idStep;
    }
    public void setIdStep(int idStep) {
        this.idStep = idStep;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getComando() {
        return comando;
    }
    public void setComando(String comando) {
        this.comando = comando;
    }
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    public String getAtributo() {
        return atributo;
    }
    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }
    public String getTipoLocalizador() {
        return tipoLocalizador;
    }
    public void setTipoLocalizador(String tipoLocalizador) {
        this.tipoLocalizador = tipoLocalizador;
    }
    
    
}
