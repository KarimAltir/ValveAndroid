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
import be.ehb.valveandroid.model.GameDataBase;
import be.ehb.valveandroid.model.Platform;

public class GameViewModel extends AndroidViewModel {
    private ExecutorService mExecutorService;
    private GameDataBase mGameDataBase;
    public GameViewModel(@NonNull Application application) {
        super(application);
        mGameDataBase = GameDataBase.getInstance(application);
        mExecutorService = Executors.newFixedThreadPool(2);
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
        return mGameDataBase.getGameDAO().getAllGames();
    }

    public LiveData<List<Platform>> getAllPlatforms() {
        return mGameDataBase.getPlatformDAO().getAllPlatforms();
    }
}
