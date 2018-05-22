package e.josephmolina.getacross2.TranslationComponent;

import android.util.Log;

import e.josephmolina.getacross2.Data.DetectLanguageResponse;
import e.josephmolina.getacross2.Data.RetroFit;
import e.josephmolina.getacross2.Data.YandexAPI;
import e.josephmolina.getacross2.Data.YandexResponse;
import e.josephmolina.getacross2.Logic.BaseController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TranslationController extends BaseController<TranslationLayout> {

    private YandexAPI yandexAPI;
    private String API_KEY = "trnsl.1.1.20180131T235045Z.b989ff118bdf2e59.0fe722c34ea88e447d238e4c0e8c5ac56d46522d";

    @Override
    public void onBind() {
       yandexAPI = RetroFit.getRetrofitInstance();
    }

    public void makeAPICall(String text,String lang) {
        yandexAPI.apiCallForTranslation(API_KEY,text,lang).enqueue(new Callback<YandexResponse>() {
            @Override
            public void onResponse(Call<YandexResponse> call, Response<YandexResponse> response) {
                Log.d("TEXT--->", response.body().getTranslationText());
            }

            @Override
            public void onFailure(Call<YandexResponse> call, Throwable t) {

            }
        });
    }

    public void detectLanguage(String text) {
        yandexAPI.apiCallForDetection(API_KEY, text).enqueue(new Callback<DetectLanguageResponse>() {
            @Override
            public void onResponse(Call<DetectLanguageResponse> call, Response<DetectLanguageResponse> response) {
                Log.d("DETECT LANGUAGE", "reached");
                Log.d("Language", response.body().getLang());
                String currentLanguage = response.body().getLang();
                switch (currentLanguage) {

                    case "en":
                        Log.d("ENGLISH", "default is english");
                        String languagePair = "en-es";

                        break;
                }
            }

            @Override
            public void onFailure(Call<DetectLanguageResponse> call, Throwable t) {

            }
        });
    }

    public void displayTranslatedText(String text) {
        getView().displayTranslatedText(text);
    }
}