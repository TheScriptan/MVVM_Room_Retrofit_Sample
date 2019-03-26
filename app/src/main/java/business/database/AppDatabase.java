package business.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import business.database.dao.RepoDao;
import business.database.dao.UserDao;
import business.model.Repo;
import business.model.User;

@Database(entities = {User.class, Repo.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract RepoDao repoDao();

    private static volatile AppDatabase sInstance;

        public static AppDatabase getInstance(Context context){
            if(sInstance == null){
                synchronized (AppDatabase.class){
                    if(sInstance == null){
                        sInstance = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "github_db")
                                .fallbackToDestructiveMigration()
                                .build();
                    }
                }
            }
            return sInstance;
    }
}
