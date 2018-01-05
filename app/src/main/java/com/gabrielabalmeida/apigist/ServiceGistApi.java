package com.gabrielabalmeida.apigist;

import java.util.List;

import retrofit.http.Query;
import retrofit.http.GET;

/**
 * Created by gabriela on 02/01/18.
 */

public interface ServiceGistApi {

    @GET("/gists/public")
    void getPaginas(@Query("page") int page, CallbackRetrof<List<Usuarios>> paginas);
}