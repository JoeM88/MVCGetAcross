package e.josephmolina.saywhat.RoomDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import e.josephmolina.saywhat.Model.SavedTranslation;

/***
 *
 * Contains the database holder and serves as the main access point for the underlying connection
 * to your app's persisted, relational data.

 The class that's annotated with @Database should satisfy the following conditions:

 Be an abstract class that extends RoomDatabase.
 Include the list of entities associated with the database within the annotation.
 Contain an abstract method that has 0 arguments and returns the class that is annotated with @Dao.

 */
@Database(entities = {SavedTranslation.class}, version = 1)
public abstract class SayWhatDatabase extends RoomDatabase {
    private static SayWhatDatabase sayWhatDatabaseInstance;

    public abstract SavedTranslationDAO savedTranslationDAO();

    public static SayWhatDatabase getSayWhatDatabaseInstance(Context context) {
        if (sayWhatDatabaseInstance == null) {
            sayWhatDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                    SayWhatDatabase.class, "savedTranslation-database")
                    .build();
        }
        return sayWhatDatabaseInstance;
    }
}
