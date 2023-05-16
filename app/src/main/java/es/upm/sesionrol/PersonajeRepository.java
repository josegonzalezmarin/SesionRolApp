package es.upm.sesionrol;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PersonajeRepository {
    private IPersonajeDAO iItemDAO;
    private LiveData<List<PersonajeEntity>> ldList;

    /**
     * Constructor
     *
     * @param application app
     */
    public PersonajeRepository(Application application) {
        SesionRolDatabase db = SesionRolDatabase.getDatabase(application);
        iItemDAO = db.grupoPDAO();
        ldList = iItemDAO.getAll();
    }

    public LiveData<List<PersonajeEntity>> getAll() {
        return ldList;
    }

    public long insert(PersonajeEntity item) {
        return iItemDAO.insert(item);
    }

    public void deleteAll() {
        iItemDAO.deleteAll();
    }

    public void delete(PersonajeEntity item)  {
        iItemDAO.delete(item);
    }
}

