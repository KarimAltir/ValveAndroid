package be.ehb.valveandroid.model;
import be.ehb.valveandroid.model.Game;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GameDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGame(List<Game> games);

    @Query("SELECT * FROM game WHERE gameId = :gameId")
    Game getGameById(int gameId);

    @Query("SELECT * FROM game ORDER BY name ASC")
    LiveData<List<Game>> getAllGames();

    //select everything from game where the year of release is 2020
    @Query("SELECT * FROM game WHERE released = '2020'")
    LiveData<List<Game>> getGamesFrom2020();

    //select everything from game where the year of release is 2019
    @Query("SELECT * FROM game WHERE released = '2019'")
    LiveData<List<Game>> getGamesFrom2019();

    //select everything from game where the year of release is 2018
    @Query("SELECT * FROM game WHERE released = '2018'")
    LiveData<List<Game>> getGamesFrom2018();


    @Update
    void updateGame(Game game);

    @Delete
    void deleteGame(Game game);
}
