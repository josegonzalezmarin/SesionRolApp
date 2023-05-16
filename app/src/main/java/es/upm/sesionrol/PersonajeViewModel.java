package es.upm.sesionrol;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PersonajeViewModel extends AndroidViewModel {

    private PersonajeRepository mRepository;

    private LiveData<List<PersonajeEntity>> ldList;

    /**
     * Constructor
     *
     * @param application app
     */
    public PersonajeViewModel(Application application) {
        super(application);
        mRepository = new PersonajeRepository(application);
        ldList = mRepository.getAll();
    }

    /**
     * Obtiene todos los grupos
     *
     * @return lista de grupos
     */
    public LiveData<List<PersonajeEntity>> getAll() {
        return ldList;
    }

    public void insert(PersonajeEntity item) {
        mRepository.insert(item);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void delete(PersonajeEntity item) {
        mRepository.delete(item);
    }


}
