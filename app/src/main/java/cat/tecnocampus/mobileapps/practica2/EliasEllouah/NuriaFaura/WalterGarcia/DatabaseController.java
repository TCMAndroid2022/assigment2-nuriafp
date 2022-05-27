package cat.tecnocampus.mobileapps.practica2.EliasEllouah.NuriaFaura.WalterGarcia;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DatabaseController {
    private GameDao gameDao;
    private LiveData<List<Game>> allGames;

    public DatabaseController(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        gameDao = db.GameDao();
        allGames = gameDao.getAllGames();
    }

    public LiveData<List<Game>> fetchAll() {
        return allGames;
    }

    public void setTodo(String task) {
        Game current = new Game();
        new insertAsyncTask(gameDao).execute(current);
    }

    /*private static class insertAsyncTask extends AsyncTask<Player, Void, Void> {
        private PlayerDao asyncDao;

        insertAsyncTask(PlayerDao dao) {
            asyncDao = dao;
        }

        @Override
        protected Void doInBackground(Player... players) {
            asyncDao.insertPlayer(players[0]);
            return null;
        }
    }*/

    private static class insertAsyncTask {
        private GameDao asyncDao;
        private Executor executor = Executors.newSingleThreadExecutor();

        insertAsyncTask(GameDao dao) {
            asyncDao = dao;
        }

        public void execute(Game game) {
            this.doInBackground(game);
        }

        private void doInBackground(final Game game) {
            this.executor.execute(new Runnable() {
                @Override
                public void run() {
                    asyncDao.insertGame(game);
                }
            });
        }
    }
}
