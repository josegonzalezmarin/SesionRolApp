package es.upm.sesionrol;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = CampaignEntity.TABLA)
public class CampaignEntity {
    static public final String TABLA = "campana";

    @PrimaryKey(autoGenerate = true)
    protected int cid;

    protected String name;

    protected String raza;

    protected String clase;

    public CampaignEntity(String name, String raza, String clase) {
        this.name = name;
        this.raza = raza;
        this.clase = clase;
    }

    public int getPid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public String getRaza() {
        return raza;
    }

    public String getClase() {
        return clase;
    }

}
