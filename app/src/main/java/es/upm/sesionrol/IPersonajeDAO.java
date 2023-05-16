package es.upm.sesionrol;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IPersonajeDAO {
    @Query("SELECT * FROM " + PersonajeEntity.TABLA)
    LiveData<List<PersonajeEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(PersonajeEntity grupo);

    @Query("DELETE FROM " + PersonajeEntity.TABLA)
    void deleteAll();

    @Delete
    void delete(PersonajeEntity grupo);
}
