package com.gabrielabalmeida.apigist;

import android.content.Context;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Created by gabriela on 04/01/18.
 */

public class ErroApi implements ErrorHandler {

    Context mContext;

    public ErroApi(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Throwable handleError(RetrofitError cause) {

        if(cause.getKind() == RetrofitError.Kind.NETWORK){
            return new ApiException(mContext.getString(R.string.sem_internet));
        } else if((int) Math.floor(cause.getResponse().getStatus() / 100.00) == 4){
            return new ApiException(mContext.getString(R.string.sem_conexao));
        } else if((int) Math.floor(cause.getResponse().getStatus() / 100.00) == 5){
            return new ApiException(mContext.getString(R.string.interno));
        } else {
            return cause;
        }
    }
}
