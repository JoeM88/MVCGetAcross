package e.josephmolina.saywhat.Model;

public class LanguagePair {
    private String spokenLanguage;
    private String translateToLanguage;

    public LanguagePair(String spokenLanguage) {
        this.spokenLanguage = spokenLanguage;
    }

    public String getSpokenLanguage() {
        return spokenLanguage;
    }

    public String getTranslateToLanguage() {
        return translateToLanguage;
    }


    public void setSpokenLanguage(String spokenLanguage) {
        this.spokenLanguage = spokenLanguage;
    }

    public void setTranslateToLanguage(String translateToLanguage) {
        this.translateToLanguage = translateToLanguage;
    }
}
