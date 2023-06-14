package es.upm.sesionrol;

import java.util.List;

public class User {
    private String mail;
    private List<PersonajeEntity> characters;
    private List<CampaignEntity> campanas;

    public User() {
    }

    public User(String mail, List<PersonajeEntity> characters, List<CampaignEntity> campanas) {
        this.mail = mail;
        this.characters = characters;
        this.campanas = campanas;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<PersonajeEntity> getCharacters() {
        return characters;
    }

    public void setCharacters(List<PersonajeEntity> characters) {
        this.characters = characters;
    }

    public List<CampaignEntity> getCampanas() {
        return campanas;
    }

    public void setCampanas(List<CampaignEntity> campanas) {
        this.campanas = campanas;
    }
}
