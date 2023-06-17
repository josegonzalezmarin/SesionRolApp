package es.upm.sesionrol;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class CampaignEntity {



    private String name;

    private int img;

    private List<String> jugadores;

    public CampaignEntity() {}
    public CampaignEntity(String name, int img, List<String> j) {
        this.name = name;
        this.img = img;
        j = new ArrayList<>();
        this.jugadores = j;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public List<String> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<String> jugadores) {
        this.jugadores = jugadores;
    }



}
