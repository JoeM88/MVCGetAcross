package e.josephmolina.getacross2.Model;

import android.support.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;
import rx.Observable;
import rx.Single;

public class YandexClient{
    private final static String YANDEX_BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";
    private static Retrofit retrofit;
    private static YandexApi yandexApi;

    private YandexClient() {}

    public static YandexApi getRetrofitInstance() {
        if (yandexApi != null) {
            return yandexApi;
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(YANDEX_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        yandexApi = retrofit.create(YandexApi.class);
        return yandexApi;
    }
}
