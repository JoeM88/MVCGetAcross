package e.josephmolina.getacross2.View;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import e.josephmolina.getacross2.Logic.MainController;
import e.josephmolina.getacross2.R;
import e.josephmolina.getacross2.TranslationComponent.*;
public class MainActivity extends AppCompatActivity implements ViewInterface {

    private MainController controller;
    private Fragment translationFragment = new TranslationFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new MainController();
        controller.takeView(this);
    }

    @Override
    public void displayTranslateFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, translationFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
