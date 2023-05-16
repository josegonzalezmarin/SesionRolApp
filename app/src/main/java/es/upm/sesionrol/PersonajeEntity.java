package es.upm.sesionrol;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = PersonajeEntity.TABLA)
public class PersonajeEntity {
    static public final String TABLA = "personaje";

    @PrimaryKey(autoGenerate = true)
    protected int pid;

    protected String name;

    protected String race;

    protected String dndclass;

    protected int lvl;
    protected int exp;



    protected String aligm;
    protected String backg;

    protected int str;
    protected int dex;
    protected int constit;
    protected int intel;
    protected int wisd;
    protected int charism;
    protected String competences;
    protected String equipment;
    protected String ideal;
    protected String bond;
    protected String feature;
    protected String personality;
    protected String flaws;

    //protected int money[];

    public PersonajeEntity(String name, String race, String dndclass, int lvl, int str, int dex, int constit, int intel, int wisd, int charism) {
        this.name = name;
        this.race = race;
        this.dndclass = dndclass;
        //money =new int[5];
        lvl= str= dex= constit= intel= wisd= charism =0;
    }

    public int getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getRace() {
        return race;
    }

    public String getDndclass() {
        return dndclass;
    }

    public void setName(String nombre) {
        this.name = name;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setDndclass(String dndclass) {
        this.dndclass = dndclass;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getAligm() {
        return aligm;
    }

    public void setAligm(String aligm) {
        this.aligm = aligm;
    }

    public String getBackg() {
        return backg;
    }

    public void setBackg(String backg) {
        this.backg = backg;
    }

    public int getStr() {
        return str;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public int getDex() {
        return dex;
    }

    public void setDex(int dex) {
        this.dex = dex;
    }

    public int getConstit() {
        return constit;
    }

    public void setConstit(int constit) {
        this.constit = constit;
    }

    public int getIntel() {
        return intel;
    }

    public void setIntel(int intel) {
        this.intel = intel;
    }

    public int getWisd() {
        return wisd;
    }

    public void setWisd(int wisd) {
        this.wisd = wisd;
    }

    public int getCharism() {
        return charism;
    }

    public void setCharism(int charism) {
        this.charism = charism;
    }

    public String getCompetences() { return competences; }

    public void setCompetences(String competences) {this.competences = competences; }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getIdeal() {
        return ideal;
    }

    public void setIdeal(String ideal) {
        this.ideal = ideal;
    }

    public String getBond() {
        return bond;
    }

    public void setBond(String bond) {
        this.bond = bond;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public String getFlaws() {
        return flaws;
    }

    public void setFlaws(String flaws) {
        this.flaws = flaws;
    }
}
