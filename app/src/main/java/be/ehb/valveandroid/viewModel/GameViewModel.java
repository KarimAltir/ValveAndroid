package be.ehb.valveandroid.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import be.ehb.valveandroid.model.Game;
import be.ehb.valveandroid.model.GameDAO;
import be.ehb.valveandroid.model.GameDataBase;
import be.ehb.valveandroid.model.Platform;
import be.ehb.valveandroid.view.GamesFragment;

public class GameViewModel extends AndroidViewModel {
    private ExecutorService mExecutorService;
    private GameDataBase mGameDataBase;
    private final GameDAO mGameDAO;
    private final LiveData<List<Game>> mAllGames;

    public GameViewModel(@NonNull Application application) {
        super(application);
        mGameDataBase = GameDataBase.getInstance(application);
        mExecutorService = Executors.newFixedThreadPool(2);
        mGameDAO = mGameDataBase.getGameDAO();
        mAllGames = mGameDAO.getAllGames();
    }

    public void insertGame(List<Game> games) {
        mExecutorService.execute(() -> {
            mGameDataBase.getGameDAO().insertGame(games);
        });
    }

    public void insertPlatform(List<Platform> platforms) {
        mExecutorService.execute(() -> {
            mGameDataBase.getPlatformDAO().insertPlatform(platforms);
        });
    }

    public LiveData<List<Game>> getAllGames() {
        return mAllGames;
    }

    public LiveData<List<Platform>> getAllPlatforms() {
        return mGameDataBase.getPlatformDAO().getAllPlatforms();
    }


    // filter games by rating and release date
    public LiveData<List<Game>> getFilteredGames(String minRating, String startDate, String endDate) {
        return mGameDataBase.getGameDAO().getFilteredGames(minRating, startDate, endDate);
    }

}
