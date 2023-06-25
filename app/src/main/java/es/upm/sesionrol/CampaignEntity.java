package es.upm.sesionrol;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class CampaignEntity {



    private String name;

    private String img;

    private String master;

    private String jugadores;

    public CampaignEntity() {}
    public CampaignEntity(String name, String master, String img, String j) {
        this.name = name;
        this.img = img;
        this.jugadores = j;
        this.master = master;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getJugadores() {
        return jugadores;
    }

    public void setJugadores(String jugadores) {
        this.jugadores = jugadores;
    }



}
