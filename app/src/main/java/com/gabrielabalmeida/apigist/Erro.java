package com.gabrielabalmeida.apigist;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gabriela on 03/01/18.
 */

public class Erro implements Serializable{
    public enum CODE_ERRO {
        NO_CONNECTION(-1);
        public int codigo;

        CODE_ERRO(int codigo){this.codigo = codigo;}
    }

    @SerializedName("codigo")
    private Integer code;

    @SerializedName("mensagem_erro")
    private String strMessage;

    public Erro(String strMessage)
    {
        this.strMessage = strMessage;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStrMessage() {
        return strMessage;
    }

    public void setStrMessage(String strMessage) {
        this.strMessage = strMessage;
    }
}
