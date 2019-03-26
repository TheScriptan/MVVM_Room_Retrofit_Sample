package business.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import business.model.Repo;

@Dao
public interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRepo(Repo repo);

    @Query("SELECT * FROM repo_table WHERE name = :name")
    Repo getRepoByName(String name);

    @Query("SELECT * FROM repo_table")
    LiveData<List<Repo>> getRepos();

    @Query("SELECT EXISTS(SELECT 1 FROM repo_table WHERE name = :name)")
    boolean hasRepo(String name);
}

