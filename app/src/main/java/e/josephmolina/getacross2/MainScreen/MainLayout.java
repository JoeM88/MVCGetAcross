package e.josephmolina.getacross2.MainScreen;

import android.view.View;
import android.widget.Button;

import e.josephmolina.getacross2.R;

public class MainLayout {

    private MainLayoutListener mainLayoutListener;
    private Button translateButton;

    public MainLayout(final MainActivity mainActivity, MainLayoutListener mainLayoutListener) {
        mainActivity.setContentView(R.layout.activity_main);
        this.mainLayoutListener = mainLayoutListener;

        translateButton = mainActivity.findViewById(R.id.translateButton);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLayoutListener.onButtonClicked();
            }
        });
    }



   interface MainLayoutListener {
        void onButtonClicked();
    }
}
