package e.josephmolina.getacross2.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YandexApi {

    @GET("translate")
    Call<YandexResponse> apiCallForTranslation(
            @Query("key") String API_KEY,
            @Query("text") String text,
            @Query("lang") String lang);

//    @GET("detect")
//    rx.Observable<YandexResponse> getDetectedLanguage(
//            @Query("key") String API_KEY,
//            @Query("text") String text);
//
//    @GET("detect")
//    Call<DetectLanguageResponse> apiCallForDetection(
//            @Query("key") String API_KEY,
//            @Query("text") String text);
}
