package e.josephmolina.saywhat.RoomDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import e.josephmolina.saywhat.Model.SavedTranslation;

/***
 * Contains the methods used for accessing the database.
 */

@Dao
public interface SavedTranslationDAO {

    @Query("SELECT * FROM savedTranslation")
    List<SavedTranslation> getSavedTranslations();

    @Insert
    void insertTranslation(SavedTranslation savedtranslation);

}
