package com.gabrielabalmeida.apigist;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gabriela on 02/01/18.
 */

public class Dono implements Serializable {

    @SerializedName("login")
    public String login;
    @SerializedName("avatar_url")
    public String avatar;
}