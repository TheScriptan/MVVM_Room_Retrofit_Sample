package business.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import business.model.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUser(User user);

    @Query("SELECT * FROM user_table WHERE username = :username")
    User getUserByName(String username);

    @Query("SELECT * FROM user_table")
    LiveData<List<User>> getUsers();

    @Query("SELECT EXISTS(SELECT 1 FROM user_table WHERE username = :username)")
    boolean hasUser(String username);
}
