package e.josephmolina.saywhat.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/***
 * Represents a table within the database.
 */

@Entity(tableName = "savedTranslation")
public class SavedTranslation {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "column_title")
    private String title;

    @ColumnInfo(name = "column_spokenText")
    private final String spokenText;

    @ColumnInfo(name = "column_translatedText")
    private final String translatedText;

    public SavedTranslation(String title, String spokenText, String translatedText) {
        this.title = title;
        this.spokenText = spokenText;
        this.translatedText = translatedText;
    }

    public String getTitle() {
        return title;
    }

    public String getSpokenText() {
        return spokenText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
