package e.josephmolina.getacross2.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface YandexService {

    @GET("/translate?key=trnsl.1.1.20180131T235045Z.b989ff118bdf2e59.0fe722c34ea88e447d238e4c0e8c5ac56d46522d&text={textToTranslate}&lang=en-es")
    Call<YandexResponse> apiCallForTranslation(@Path("textToTranslate") String text );
}
