package be.ehb.valveandroid.model;

import androidx.room.Entity;

@Entity(primaryKeys = {"gameId", "platformId"})
public class GamePlatform {

        private int gameId;
        private int platformId;

        public GamePlatform() {
        }

        public GamePlatform(int gameId, int platformId) {
            this.gameId = gameId;
            this.platformId = platformId;
        }

        public int getGameId() {
            return gameId;
        }

        public void setGameId(int gameId) {
            this.gameId = gameId;
        }

        public int getPlatformId() {
            return platformId;
        }

        public void setPlatformId(int platformId) {
            this.platformId = platformId;
        }
}
