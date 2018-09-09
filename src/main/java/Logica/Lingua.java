package Logica;

public class Lingua {

    private String lMouseClicker;
    private String lClicker;
    private String lNumerosDeClicker;
    private String bAtivar;
    private String taTutorialAreaTempo;
    private String taTutorialAreaClicker;
    private String lStatus;
    private String miAtualizacoes;
    private String miPaginaInicial;
    private String miPrograma;
    private String miOpcoes;
    private String lIniciado;
    private String lPausado;

    @Override
    public String toString() {
        return "Lingua{" +
                "lMouseClicker='" + lMouseClicker + '\'' +
                ", lClicker='" + lClicker + '\'' +
                ", lNumerosDeClicker='" + lNumerosDeClicker + '\'' +
                ", bAtivar='" + bAtivar + '\'' +
                ", taTutorialAreaTempo='" + taTutorialAreaTempo + '\'' +
                ", taTutorialAreaClicker='" + taTutorialAreaClicker + '\'' +
                ", lStatus='" + lStatus + '\'' +
                ", miAtualizacoes='" + miAtualizacoes + '\'' +
                ", miPaginaInicial='" + miPaginaInicial + '\'' +
                ", miPrograma='" + miPrograma + '\'' +
                ", miOpcoes='" + miOpcoes + '\'' +
                ", lIniciado='" + lIniciado + '\'' +
                ", lPausado='" + lPausado + '\'' +
                ", lAguardando='" + lAguardando + '\'' +
                ", lExecutado='" + lExecutado + '\'' +
                ", taAreaAtualizacao='" + taAreaAtualizacao + '\'' +
                ", taAreaHotfix='" + taAreaHotfix + '\'' +
                ", lcomecaEm='" + lcomecaEm + '\'' +
                ", cbCtrl='" + cbCtrl + '\'' +
                ", cbTempoDeClicker='" + cbTempoDeClicker + '\'' +
                ", lParar='" + lParar + '\'' +
                ", lPausadContinua='" + lPausadContinua + '\'' +
                ", lcomeca='" + lcomeca + '\'' +
                ", bReseta='" + bReseta + '\'' +
                '}';
    }

    public Lingua(String lMouseClicker, String lClicker, String lNumerosDeClicker, String bAtivar, String taTutorialAreaTempo, String taTutorialAreaClicker, String lStatus, String miAtualizacoes, String miPaginaInicial, String miPrograma, String miOpcoes, String lIniciado, String lPausado, String lAguardando, String lExecutado, String taAreaAtualizacao, String taAreaHotfix, String lcomecaEm, String cbCtrl, String cbTempoDeClicker, String lParar, String lPausadContinua, String lcomeca, String bReseta) {
        this.lMouseClicker = lMouseClicker;
        this.lClicker = lClicker;
        this.lNumerosDeClicker = lNumerosDeClicker;
        this.bAtivar = bAtivar;
        this.taTutorialAreaTempo = taTutorialAreaTempo;
        this.taTutorialAreaClicker = taTutorialAreaClicker;
        this.lStatus = lStatus;
        this.miAtualizacoes = miAtualizacoes;
        this.miPaginaInicial = miPaginaInicial;
        this.miPrograma = miPrograma;
        this.miOpcoes = miOpcoes;
        this.lIniciado = lIniciado;
        this.lPausado = lPausado;
        this.lAguardando = lAguardando;
        this.lExecutado = lExecutado;
        this.taAreaAtualizacao = taAreaAtualizacao;
        this.taAreaHotfix = taAreaHotfix;
        this.lcomecaEm = lcomecaEm;
        this.cbCtrl = cbCtrl;
        this.cbTempoDeClicker = cbTempoDeClicker;
        this.lParar = lParar;
        this.lPausadContinua = lPausadContinua;
        this.lcomeca = lcomeca;
        this.bReseta = bReseta;
    }

    private String lAguardando;

    public String getlMouseClicker() {
        return lMouseClicker;
    }

    public void setlMouseClicker(String lMouseClicker) {
        this.lMouseClicker = lMouseClicker;
    }

    public String getlClicker() {
        return lClicker;
    }

    public void setlClicker(String lClicker) {
        this.lClicker = lClicker;
    }

    public String getlNumerosDeClicker() {
        return lNumerosDeClicker;
    }

    public void setlNumerosDeClicker(String lNumerosDeClicker) {
        this.lNumerosDeClicker = lNumerosDeClicker;
    }

    public String getbAtivar() {
        return bAtivar;
    }

    public void setbAtivar(String bAtivar) {
        this.bAtivar = bAtivar;
    }

    public String getTaTutorialAreaTempo() {
        return taTutorialAreaTempo;
    }

    public void setTaTutorialAreaTempo(String taTutorialAreaTempo) {
        this.taTutorialAreaTempo = taTutorialAreaTempo;
    }

    public String getTaTutorialAreaClicker() {
        return taTutorialAreaClicker;
    }

    public void setTaTutorialAreaClicker(String taTutorialAreaClicker) {
        this.taTutorialAreaClicker = taTutorialAreaClicker;
    }

    public String getlStatus() {
        return lStatus;
    }

    public void setlStatus(String lStatus) {
        this.lStatus = lStatus;
    }

    public String getMiAtualizacoes() {
        return miAtualizacoes;
    }

    public void setMiAtualizacoes(String miAtualizacoes) {
        this.miAtualizacoes = miAtualizacoes;
    }

    public String getMiPaginaInicial() {
        return miPaginaInicial;
    }

    public void setMiPaginaInicial(String miPaginaInicial) {
        this.miPaginaInicial = miPaginaInicial;
    }

    public String getMiPrograma() {
        return miPrograma;
    }

    public void setMiPrograma(String miPrograma) {
        this.miPrograma = miPrograma;
    }

    public String getMiOpcoes() {
        return miOpcoes;
    }

    public void setMiOpcoes(String miOpcoes) {
        this.miOpcoes = miOpcoes;
    }

    public String getlIniciado() {
        return lIniciado;
    }

    public void setlIniciado(String lIniciado) {
        this.lIniciado = lIniciado;
    }

    public String getlPausado() {
        return lPausado;
    }

    public void setlPausado(String lPausado) {
        this.lPausado = lPausado;
    }

    public String getlAguardando() {
        return lAguardando;
    }

    public void setlAguardando(String lAguardando) {
        this.lAguardando = lAguardando;
    }

    public String getlExecutado() {
        return lExecutado;
    }

    public void setlExecutado(String lExecutado) {
        this.lExecutado = lExecutado;
    }

    public String getTaAreaAtualizacao() {
        return taAreaAtualizacao;
    }

    public void setTaAreaAtualizacao(String taAreaAtualizacao) {
        this.taAreaAtualizacao = taAreaAtualizacao;
    }

    public String getTaAreaHotfix() {
        return taAreaHotfix;
    }

    public void setTaAreaHotfix(String taAreaHotfix) {
        this.taAreaHotfix = taAreaHotfix;
    }

    public String getLcomecaEm() {
        return lcomecaEm;
    }

    public void setLcomecaEm(String lcomecaEm) {
        this.lcomecaEm = lcomecaEm;
    }

    public String getCbCtrl() {
        return cbCtrl;
    }

    public void setCbCtrl(String cbCtrl) {
        this.cbCtrl = cbCtrl;
    }

    public String getCbTempoDeClicker() {
        return cbTempoDeClicker;
    }

    public void setCbTempoDeClicker(String cbTempoDeClicker) {
        this.cbTempoDeClicker = cbTempoDeClicker;
    }

    public String getlParar() {
        return lParar;
    }

    public void setlParar(String lParar) {
        this.lParar = lParar;
    }

    public String getlPausadContinua() {
        return lPausadContinua;
    }

    public void setlPausadContinua(String lPausadContinua) {
        this.lPausadContinua = lPausadContinua;
    }

    public String getLcomeca() {
        return lcomeca;
    }

    public void setLcomeca(String lcomeca) {
        this.lcomeca = lcomeca;
    }

    public String getbReseta() {
        return bReseta;
    }

    public void setbReseta(String bReseta) {
        this.bReseta = bReseta;
    }

    private String lExecutado;
    private String taAreaAtualizacao;
    private String taAreaHotfix;
    private String lcomecaEm;
    private String cbCtrl;
    private String cbTempoDeClicker;
    private String lParar;
    private String lPausadContinua;
    private String lcomeca;
    private String bReseta;

}
