package e.josephmolina.getacross2.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Single;

public interface YandexApi {

    @GET("translate")
    Call<YandexResponse> getTranslation(
            @Query("key") String API_KEY,
            @Query("text") String text,
            @Query("lang") String lang);

    @GET("detect")
    Call<DetectLanguageResponse> getDetectedLanguage(
            @Query("key") String API_KEY,
            @Query("text") String text);


    @GET("detect")
    Single<DetectLanguageResponse> testDetection(
            @Query("key") String API_KEY,
            @Query("text") String text);

    @GET("translate")
    Single<YandexResponse> testTranslation(
            @Query("key") String API_KEY,
            @Query("text") String text,
            @Query("lang") String lang);

}
