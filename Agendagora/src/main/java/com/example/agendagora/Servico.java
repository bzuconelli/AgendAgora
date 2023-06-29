package com.example.agendagora;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

public class Servico {
    public Date datadoservico;
    public String tipodoservico;

    public Cliente cliente ;
    public String estadodoservico;

    public int codigo;
    public Time hora;
    public double valorhora;
    public double valorfinal;
    public Usuario usuario;


    public Date getDatadoservico() {
        return datadoservico;
    }

    public void setDatadoservico(Date datadoservico) {
        this.datadoservico = datadoservico;
    }

    public String getTipodoservico() {
        return tipodoservico;
    }

    public void setTipodoservico(String tipodoservico) {
        this.tipodoservico = tipodoservico;
    }

    public String getEstadodoservico() {
        return estadodoservico;
    }

    public void setEstadodoservico(String estadodoservico) {
        this.estadodoservico = estadodoservico;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public double getValorhora() {
        return valorhora;
    }

    public void setValorhora(double valorhora) {
        this.valorhora = valorhora;
    }

    public double getValorfinal() {
        return valorfinal;
    }

    public void setValorfinal(double valorfinal) {
        this.valorfinal = valorfinal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


}