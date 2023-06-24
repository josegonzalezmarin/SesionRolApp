package es.upm.sesionrol.interfaces;

import java.util.List;

import es.upm.sesionrol.clasesAPI.RequestAnswer;
import retrofit2.Call;
import es.upm.sesionrol.PersonajeEntity;
import retrofit2.http.GET;

public interface PersonajeAPI {

    @GET("api/races")
    Call<RequestAnswer>getRaces();
    @GET("api/alignments")
    Call<RequestAnswer>getAligm();
    @GET("api/backgrounds")
    Call<RequestAnswer>getBackgrounds();
    @GET("api/classes")
    Call<RequestAnswer>getClasses();


}
