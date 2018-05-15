package e.josephmolina.getacross2.TranslationComponent;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import e.josephmolina.getacross2.R;

public class TranslationFragment extends Fragment implements TranslationComponentInterface {

    @BindView(R.id.textToTranslateEditText)
    EditText userEnteredText;
    @BindView(R.id.translatedTextResults)
    TextView translatedTextResults;

    private Unbinder unbinder;
    private TranslationController translationController;

    public TranslationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_translation, container, false);
        unbinder = ButterKnife.bind(this, view);

        translationController = new TranslationController();
        translationController.takeView(this);
        return view;
    }


    @Override
    public void displayTranslatedText(String text) {
        // translatedTextResults.setText(text);
    }

    @Override
    public String getTranslatedText(String text) {
        return null;
    }


    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
