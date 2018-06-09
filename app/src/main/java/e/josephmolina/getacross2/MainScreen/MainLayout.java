package e.josephmolina.getacross2.MainScreen;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import e.josephmolina.getacross2.Model.YandexClient;
import e.josephmolina.getacross2.Model.YandexResponse;
import e.josephmolina.getacross2.R;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainLayout {

    private MainLayoutListener mainLayoutListener;
    private Button translateButton;
    private Subscription subscription;
    private String TAG = "Layout";
    private String API_KEY = "trnsl.1.1.20180131T235045Z.b989ff118bdf2e59.0fe722c34ea88e447d238e4c0e8c5ac56d46522d";


    public MainLayout(final MainActivity mainActivity, MainLayoutListener mainLayoutListener) {
        mainActivity.setContentView(R.layout.activity_main);
        this.mainLayoutListener = mainLayoutListener;

        translateButton = mainActivity.findViewById(R.id.translateButton);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLayoutListener.onTranslateClicked();
            }
        });
    }

   interface MainLayoutListener {
        void onTranslateClicked();
    }
}
