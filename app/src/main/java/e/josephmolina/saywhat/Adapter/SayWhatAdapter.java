package e.josephmolina.saywhat.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import e.josephmolina.saywhat.Model.SavedTranslation;
import e.josephmolina.saywhat.R;
import e.josephmolina.saywhat.SavedChatsScreen.SavedTranslationsController;

public class SayWhatAdapter extends RecyclerView.Adapter<SayWhatAdapter.ViewHolder> {
    private List<SavedTranslation> listOfTranslations;
    private SavedTranslationsController controller;

    public SayWhatAdapter(List<SavedTranslation> listOfTranslations, SavedTranslationsController controller) {
        this.listOfTranslations = listOfTranslations;
        this.controller = controller;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View cardView = inflater.inflate(R.layout.card_view, parent, false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SavedTranslation currentTranslation = listOfTranslations.get(position);

        holder.savedTranslationTitle.setText(currentTranslation.getTitle());
        holder.savedSpokenText.setText(currentTranslation.getSpokenText());
        holder.savedTranslatedText.setText(currentTranslation.getTranslatedText());
    }

    @Override
    public int getItemCount() {
        return listOfTranslations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.saveTranslation_title)
        TextView savedTranslationTitle;

        @BindView(R.id.savedSpokenText)
        TextView savedSpokenText;

        @BindView(R.id.savedTranslatedText)
        TextView savedTranslatedText;

        @BindView(R.id.savedSpeakButton)
        ImageView speakButton;

        @BindView(R.id.remove_savedTranslation)
        ImageView removeButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.savedSpeakButton)
        public void onSavedSpeakButton() {
            controller.onSpeakClicked(savedTranslatedText.getText().toString());
        }

        @OnClick(R.id.remove_savedTranslation)
        public void setRemoveButton() {
            removeItem(this.getAdapterPosition());
        }

        private void removeItem(int position) {
            controller.removeSavedTranslation(listOfTranslations.get(position));
            listOfTranslations.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listOfTranslations.size());
            if (listOfTranslations.size() == 0) {
                controller.displayEmptyRecyclerViewText();
            }
        }
    }
}
