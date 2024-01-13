package be.ehb.valveandroid.view;

//import static android.os.Build.VERSION_CODES.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import be.ehb.valveandroid.R;
import be.ehb.valveandroid.model.Game;
import be.ehb.valveandroid.model.Platform;

public class DetailFragment extends Fragment {

    private ImageView gameDetailBackground;
    private TextView gameDetailGameId;
    private TextView gameDetailName;
    private TextView gameDetailSlug;
    private TextView gameDetailReleased;
    private TextView gameDetailPlatforms;
    private TextView gameDetailRating;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        gameDetailGameId = rootView.findViewById(R.id.game_detail_gameId);
        gameDetailBackground = rootView.findViewById(R.id.game_detail_image);
        gameDetailName = rootView.findViewById(R.id.game_detail_name);
        gameDetailSlug = rootView.findViewById(R.id.game_detail_slug);
        gameDetailReleased = rootView.findViewById(R.id.game_detail_released);
        gameDetailPlatforms = rootView.findViewById(R.id.game_detail_platform);
        gameDetailRating = rootView.findViewById(R.id.game_detail_rating);


        if (getArguments() != null) {
            Game game = (Game) getArguments().getSerializable("game"); // Ensure "game" is the key used to pass the Game object
            Platform platform = (Platform) getArguments().getSerializable("platform"); // Ensure "platform" is the key used to pass the Platform object


            if (game != null) {
                gameDetailGameId.setText(String.valueOf(game.getGameId()));

                Glide.with(requireContext())
                        .load(game.getBackground_image())
                        .placeholder(R.drawable.img_placeholder)
                        .error(R.drawable.img_error)
                        .into(gameDetailBackground);

                gameDetailName.setText(game.getName());
                gameDetailSlug.setText(game.getSlug());
                gameDetailReleased.setText(game.getReleased());
                gameDetailRating.setText(game.getRating());

                if (platform != null) {
                    gameDetailPlatforms.setText(platform.getName());
                }
                else {
                    gameDetailPlatforms.setText("No platform");
                }
            }
        }

        return rootView;
    }
}
