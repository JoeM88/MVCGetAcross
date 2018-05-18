package e.josephmolina.getacross2.Data;

import android.support.annotation.Nullable;
import android.util.Log;

import e.josephmolina.getacross2.TranslationComponent.TranslationComponentInterface;
import e.josephmolina.getacross2.TranslationComponent.TranslationController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YandexAPI implements TranslationSourceInterface {
    private final static String YANDEX_BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";
    private final static String TRANSLATION_KEY_PARAMETER = "translate?key=";
    private final static String DETECTION_KEY_PARAMETER = "detect?key=";
    private final static String TEXT_QUERY_PARAMETER = "&text=";
    private final static String LANGUAGE_QUERY_PARAMETER = "&lang=";

    private static Retrofit retrofit;
    private static YandexService yandexService;
    private static String results;

    private YandexAPI() {
    }

    public static YandexService getRetrofitInstance() {
        if (yandexService != null) {
            return yandexService;
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(YANDEX_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        yandexService = retrofit.create(YandexService.class);
        return yandexService;
    }

    @Nullable
    public static String translateText(String text) {
        Call<YandexResponse> call = yandexService.apiCallForTranslation(text);
        call.enqueue(new Callback<YandexResponse>() {
            @Override
            public void onResponse(Call<YandexResponse> call, Response<YandexResponse> response) {
                Log.d("Response", response.body().getTranslationText());
            }

            @Override
            public void onFailure(Call<YandexResponse> call, Throwable t) {

            }
        });
        return null;
    }

    @Override
    public String getTranslateText(String text) {
        return null;
    }
}
