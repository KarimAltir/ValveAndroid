package be.ehb.valveandroid.model;

import androidx.lifecycle.LiveData;
import be.ehb.valveandroid.model.Platform;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlatformDAO{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlatform(List<Platform> platforms);


    @Query("SELECT * FROM platform")
    LiveData<List<Platform>> getAllPlatforms();

    @Query("SELECT * FROM platform WHERE platformId = :platformId")
    Platform getPlatformById(int platformId);

    //select everything from platform where platform = pc
    @Query("SELECT * FROM platform WHERE name = 'PC'")
    LiveData<List<Platform>> getGamesFromPC();


    //select all games from game where the name of the platform = playstation5
    @Query("SELECT * FROM platform WHERE name = 'PlayStation 5'")
    LiveData<List<Platform>> getGamesFromPlaystation5();


    //select all games from game where platform = xbox
    @Query("SELECT * FROM platform WHERE name = 'Xbox One'")
    LiveData<List<Platform>> getGamesFromXbox();

    //select all games from game where platform = nintendo
    @Query("SELECT * FROM platform WHERE name = 'Nintendo Switch'")
    LiveData<List<Platform>> getGamesFromNintendo();

    @Update
    void updatePlatform(Platform platform);

    @Delete
    void deletePlatform(Platform platform);
}
