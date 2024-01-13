package be.ehb.valveandroid.view;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import be.ehb.valveandroid.R;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import be.ehb.valveandroid.model.GameDAO;
import be.ehb.valveandroid.model.GameDataBase;
import be.ehb.valveandroid.model.Platform;
import be.ehb.valveandroid.model.PlatformDAO;
import be.ehb.valveandroid.view.viewutils.GameAdapter;
import be.ehb.valveandroid.model.Game;
import be.ehb.valveandroid.viewModel.GameViewModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GamesFragment extends Fragment implements GameAdapter.OnGameItemClickListener {

    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private ArrayList<Game> gamesList = new ArrayList<>();
    private ArrayList<Platform> platformsList = new ArrayList<>();
    private GameViewModel gameViewModel;
    private SearchView searchView;


    public GamesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = rootView.findViewById(R.id.rv_games);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize ViewModel
        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);

        gameViewModel.getAllGames().observe(getViewLifecycleOwner(), games -> {
            gamesList.clear();
            gamesList.addAll(games);
            gameAdapter.notifyDataSetChanged();
        });

        gameViewModel.getAllPlatforms().observe(getViewLifecycleOwner(), platforms -> {
            platformsList.clear();
            platformsList.addAll(platforms);
            gameAdapter.notifyDataSetChanged();
        });

        searchView = rootView.findViewById(R.id.searchView); // Add this line to get reference to the SearchView
        setupSearchView(); // Call setupSearchView

        RequestManager requestManager = Glide.with(this);

        gameAdapter = new GameAdapter(gamesList, requestManager);
        recyclerView.setAdapter(gameAdapter);

        fetchGames(); // Call method to fetch games

        return rootView;
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search when the user submits the query
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter search results as the user types
                filterSearch(newText);
                return true;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set listener to this fragment
        gameAdapter.setListener(this);
    }

    private void fetchGames() {
        String apiKey = getResources().getString(R.string.api_key);
        int pageSize = 10000; // Set the desired page size
        int pageNumber = 15; // Set the page number to retrieve
        String startDate = "1980-01-01";
        String endDate = "2023-12-30";
        String platformse = "18,1,7,4";

        new Thread(() -> {
            try {
                OkHttpClient mClient = new OkHttpClient();

                String apiUrl = "https://api.rawg.io/api/games?key=" + apiKey +
                        "&dates=" + startDate + "," + endDate + "&platforms=" + platformse +
                        "&page=" + pageNumber + "&page_size=" + pageSize;

                Request mRequest = new Request.Builder()
                        .url(apiUrl)
                        .get()
                        .build();

                Response mResponse = mClient.newCall(mRequest).execute();

                // Raw payload
                String responseText = mResponse.body().string();

                // Convert to JSON
                JSONObject document = new JSONObject(responseText);

                JSONArray gamesJSON = document.getJSONArray("results");
                // Parse JSON
                int gamesJSONLength = gamesJSON.length();
                ArrayList<Game> parsedGames = new ArrayList<>(gamesJSONLength);

                for (int i = 0; i < gamesJSONLength; i++) {
                    JSONObject gameJSON = gamesJSON.getJSONObject(i);

                    // Extract game details
                    int gameId = gameJSON.getInt("id");
                    String name = gameJSON.getString("name");
                    String slug = gameJSON.getString("slug");
                    String released = gameJSON.getString("released");
                    String rating = gameJSON.getString("rating");
                    String backgroundImage = gameJSON.getString("background_image");


                    // Extract platforms array
                    JSONArray platformsJSON = gameJSON.getJSONArray("platforms");
                    ArrayList<Platform> platforms = new ArrayList<>();

                    for (int j = 0; j < platformsJSON.length(); j++) {
                        JSONObject platformJSON = platformsJSON.getJSONObject(j);
                        int platformId = platformJSON.getJSONObject("platform").getInt("id");
                        String platformName = platformJSON.getJSONObject("platform").getString("name");
                        String platformSlug = platformJSON.getJSONObject("platform").getString("slug");

                        // Create Platform object
                        Platform platform = new Platform(platformId, platformName, platformSlug);
                        platforms.add(platform);
                    }

                    // Create Game object with platforms
                    Game game = new Game(gameId, name, slug, released, rating, backgroundImage);
                    //game.setGameId(gameId);
                    parsedGames.add(game);
                }

                getActivity().runOnUiThread(() -> {
                    synchronized (gamesList) {
                        gamesList.clear();
                        gamesList.addAll(parsedGames);
                        gameAdapter.notifyDataSetChanged();
                    }
                });

                gameViewModel.insertGame(parsedGames); // Insert games into the database using ViewModel

            } catch (JSONException | IOException e) {
                getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Error fetching data", Toast.LENGTH_SHORT).show());
                e.printStackTrace();
            }

            GameDataBase gameDataBase = GameDataBase.getInstance(requireContext());

            // Inserting fetched games into Room database
            new Thread(() -> {
                    // Insert parsedGames into the database
                    gameDataBase.getGameDAO().getAllGames();
                }).start();
        }).start();

    }

    private void performSearch(String query) {
        // Filter the gamesList based on the query
        ArrayList<Game> filteredList = filterGamesByQuery(query);

        // Update the RecyclerView with the filtered list
        gameAdapter.setItems(filteredList);
        gameAdapter.notifyDataSetChanged();
    }

    private void filterSearch(String newText) {
        // Filter the gamesList based on the query
        ArrayList<Game> filteredList = filterGamesByQuery(newText);

        // Update the RecyclerView with the filtered list or original list if query is empty
        gameAdapter.newItems(filteredList.isEmpty() ? gamesList : filteredList);
    }

    // Implement your own logic to filter games based on the query
    private ArrayList<Game> filterGamesByQuery(String query) {
        ArrayList<Game> filteredList = new ArrayList<>();

        for (Game game : gamesList) {
            // Check if the name starts with the query (case-insensitive)
            if (game.getName().toLowerCase().startsWith(query.toLowerCase())) {
                filteredList.add(game);
            }
        }

        return filteredList;
    }

    @Override
    public void onGameItemClick(Game game) {
        Bundle bundle = new Bundle();

        bundle.putSerializable("game", game); // Ensure "game" is the key used to pass the Game object

        NavHostFragment.findNavController(this)
                .navigate(R.id.detailFragment, bundle);
    }

    @Override
    public void onGameItemClick(Game game, Platform platform) {
        Bundle bundle = new Bundle();

        bundle.putSerializable("game", game); // Ensure "game" is the key used to pass the Game object
        bundle.putSerializable("platform", platform); // Ensure "platform" is the key used to pass the Platform object

        NavHostFragment.findNavController(this)
                .navigate(R.id.detailFragment, bundle);
    }
}
