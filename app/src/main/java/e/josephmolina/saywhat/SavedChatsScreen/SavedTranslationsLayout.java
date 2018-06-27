package e.josephmolina.saywhat.SavedChatsScreen;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import e.josephmolina.saywhat.R;

public class SavedTranslationsLayout {

    private SavedTranslationsLayout.SavedTranslationsScreenListener listener;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public SavedTranslationsLayout(final SavedTranslationsScreen activity, SavedTranslationsScreenListener listener) {
        activity.setContentView(R.layout.activity_saved_translations_screen);
        ButterKnife.bind(this, activity);
        this.listener = listener;
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    interface SavedTranslationsScreenListener {

    }
}
