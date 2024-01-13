package be.ehb.valveandroid.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

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
