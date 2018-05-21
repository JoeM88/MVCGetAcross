package e.josephmolina.getacross2.Data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFit implements TranslationSourceInterface {
    private final static String YANDEX_BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";
    private static Retrofit retrofit;
    private static YandexAPI yandexService;

    private RetroFit() {
    }

    public static YandexAPI getRetrofitInstance() {
        if (yandexService != null) {
            return yandexService;
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(YANDEX_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        yandexService = retrofit.create(YandexAPI.class);
        return yandexService;
    }

    @Override
    public String getTranslateText(String text) {
        return null;
    }
}
