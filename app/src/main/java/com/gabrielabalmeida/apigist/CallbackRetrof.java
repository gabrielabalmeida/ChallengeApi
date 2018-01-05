package com.gabrielabalmeida.apigist;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by gabriela on 03/01/18.
 */

public abstract class CallbackRetrof<T> implements Callback<T> {
    public abstract void failure(Erro erro);

    @Override
    public void failure(RetrofitError error ) {
        Erro erro = (Erro) error.getBodyAs(Erro.class);

        if (erro != null) {
            failure(erro);
        } else {
            failure(new Erro(error.getMessage()));
        }
    }
}
