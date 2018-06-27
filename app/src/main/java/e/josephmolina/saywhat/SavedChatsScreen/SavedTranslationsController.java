package e.josephmolina.saywhat.SavedChatsScreen;

import android.speech.tts.TextToSpeech;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import e.josephmolina.saywhat.Adapter.SayWhatAdapter;
import e.josephmolina.saywhat.Model.SavedTranslation;
import e.josephmolina.saywhat.RoomDB.SayWhatDatabase;
import e.josephmolina.saywhat.TextToSpeechManager.TextToSpeechManager;

public class SavedTranslationsController implements SavedTranslationsLayout.SavedTranslationsScreenListener {

    private SavedTranslationsScreen activity;
    private SavedTranslationsLayout savedTranslationsLayout;
    private TextToSpeech textToSpeechManager;

    public SavedTranslationsController(SavedTranslationsScreen activity) {
        this.activity = activity;
        savedTranslationsLayout = new SavedTranslationsLayout(activity, this);
        setupAdapter();

        textToSpeechManager = TextToSpeechManager.getTextToSpeechInstance(activity);

    }

    private void setupAdapter() {
        List<SavedTranslation> data = SayWhatDatabase.getSayWhatDatabaseInstance(activity).savedTranslationDAO().getSavedTranslations();
        if (data.size() == 0) {
            savedTranslationsLayout.recyclerView.setVisibility(View.GONE);
            savedTranslationsLayout.emptyRecyclerViewTextView.setVisibility(View.VISIBLE);
        } else {
            savedTranslationsLayout.recyclerView.setVisibility(View.VISIBLE);
            savedTranslationsLayout.emptyRecyclerViewTextView.setVisibility(View.GONE);
            SayWhatAdapter adapter = new SayWhatAdapter(data, this);
            savedTranslationsLayout.recyclerView.setAdapter(adapter);
            savedTranslationsLayout.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        }
    }

    public void onSpeakClicked(String text) {
        textToSpeechManager.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }
}
