package e.josephmolina.getacross2.TranslationScreen;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import e.josephmolina.getacross2.R;

public class TranslationFragment extends Fragment implements TranslationLayout {

    @BindView(R.id.textToTranslateEditText)
    EditText userEnteredText;
    @BindView(R.id.translatedTextResults)
    TextView translatedTextResults;
    @BindView(R.id.translateButton)
    Button testButton;

    private Unbinder unbinder;
    private TranslationController translationController;

    public TranslationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translation, container, false);
        unbinder = ButterKnife.bind(this, view);

        translationController = new TranslationController();
        translationController.takeView(this);
        return view;
    }

    @OnTextChanged(R.id.textToTranslateEditText)
    public void onTextChanged(CharSequence charSequence) {
        Log.d("Text was changed", charSequence.toString());
    }

    @Override
    public void displayTranslatedText(String text) {
        translatedTextResults.setText(text);
    }

    @OnClick(R.id.translateButton)
    public void onTranslateButton() {
        translationController.detectLanguage(userEnteredText.getText().toString());
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
