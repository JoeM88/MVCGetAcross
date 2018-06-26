package e.josephmolina.saywhat.Model;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YandexApi {

    @GET("detect")
    Single<DetectLanguageResponse> getSpokenLanguage(
            @Query("key") String API_KEY,
            @Query("text") String text);

    @GET("translate")
    Single<YandexResponse> getTranslation(
            @Query("key") String API_KEY,
            @Query("text") String text,
            @Query("lang") String lang);
}
