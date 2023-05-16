package es.upm.sesionrol;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import es.upm.sesionrol.CampaignEntity;
import es.upm.sesionrol.PersonajeEntity;

@Dao
public interface ICampaignDAO {
    @Query("SELECT * FROM " + CampaignEntity.TABLA)
    LiveData<List<CampaignEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(CampaignEntity grupo);

    @Query("DELETE FROM " + CampaignEntity.TABLA)
    void deleteAll();

    @Delete
    void delete(CampaignEntity grupo);
}