package be.ehb.valveandroid.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GameDAO {
    @Insert
    void insertGame(Game game);

    @Query("SELECT * FROM game")
    LiveData<List<Game>> getAllGames();

    //select all games from game where platform = pc
    @Query("SELECT * FROM game WHERE platform = 'pc'")
    LiveData<List<Game>> getAllGamesFromPC();

    //select all games from game where the name of the platform = playstation5
    @Query("SELECT platform FROM game WHERE name = 'PlayStation 5'")
    LiveData<List<Game>> getGamesFromPlaystation5();


    //select all games from game where platform = xbox
    @Query("SELECT platform FROM game WHERE name = 'Xbox Store'")
    LiveData<List<Game>> getGamesFromXbox();

    //select all games from game where platform = nintendo
    @Query("SELECT platform FROM game WHERE name = 'Nintendo Store'")
    LiveData<List<Game>> getGamesFromNintendo();

    @Update
    void updateGame(Game game);

    @Delete
    void deleteGame(Game game);
}
