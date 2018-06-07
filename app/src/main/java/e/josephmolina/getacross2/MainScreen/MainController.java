package e.josephmolina.getacross2.MainScreen;

import android.util.Log;
import e.josephmolina.getacross2.Model.RetroFit;
import e.josephmolina.getacross2.Model.YandexResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainController implements MainLayout.MainLayoutListener{

    private String API_KEY = "trnsl.1.1.20180131T235045Z.b989ff118bdf2e59.0fe722c34ea88e447d238e4c0e8c5ac56d46522d";
    private MainLayout mainLayout;
    private MainActivity mainActivity;

    public MainController(MainActivity mainActivity) {
        mainLayout = new MainLayout(mainActivity,this);
        this.mainActivity = mainActivity;
    }

    @Override
    public void onButtonClicked() {
        Log.d("Main Controller", "Button was clicked");
        RetroFit.getRetrofitInstance().apiCallForTranslation(API_KEY, "Hello", "en-es").enqueue(new Callback<YandexResponse>() {
            @Override
            public void onResponse(Call<YandexResponse> call, Response<YandexResponse> response) {
                Log.d("OnResponse", "reached");
            }

            @Override
            public void onFailure(Call<YandexResponse> call, Throwable t) {

            }
        });
    }

}