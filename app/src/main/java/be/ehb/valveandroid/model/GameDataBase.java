package be.ehb.valveandroid.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(version = 1, entities = {Game.class, Platform.class})
public abstract class GameDataBase extends RoomDatabase {

    private static GameDataBase instance;
    public static GameDataBase getInstance(Context context) {
        if (instance == null) {
            //make database connection
            instance = Room.databaseBuilder(context, GameDataBase.class, "game_db").build();
        }
        return instance;
    }

    public abstract GameDAO getGameDAO();
    public abstract PlatformDAO getPlatformDAO();
}
/*@Database(entities = {Game.class, Platform.class}, version = 2, exportSchema = false)
public abstract class GameDataBase extends RoomDatabase {
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Perform migration logic here
            database.execSQL("CREATE TABLE IF NOT EXISTS `platform` (`platformId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `slug` TEXT)");
        }
    };

    public abstract GameDAO getGameDAO(); // Add this line if it's not already there
    public abstract PlatformDAO getPlatformDAO(); // Add this line if it's not already there

    public static final String DATABASE_NAME = "game_database";

    private static volatile GameDataBase INSTANCE;

    public static GameDataBase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (GameDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    GameDataBase.class, DATABASE_NAME)
                            .addMigrations(MIGRATION_1_2) // Add this line to include the migration
                            .build();
                }
            }
        }
        return INSTANCE;
    }*/