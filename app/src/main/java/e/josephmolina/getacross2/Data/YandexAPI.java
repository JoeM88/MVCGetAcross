package e.josephmolina.getacross2.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YandexAPI {
    String API_KEY = "trnsl.1.1.20180131T235045Z.b989ff118bdf2e59.0fe722c34ea88e447d238e4c0e8c5ac56d46522d";

    @GET("translate")
    Call<YandexResponse> apiCallForTranslation(
            @Query("key") String API_KEY,
            @Query("text") String text,
            @Query("lang") String lang);

    @GET("detect")
    Call<DetectLanguageResponse> apiCallForDetection(
            @Query("key") String API_KEY,
            @Query("text") String text);
}