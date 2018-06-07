package e.josephmolina.getacross2.Model;

public class YandexResponse {

    private String title;

    private String originalText;

    private String translationText;

    public YandexResponse(String title, String originalText, String translationText) {
        this.title = title;
        this.originalText = originalText;
        this.translationText = translationText;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalText() {
        return originalText;
    }

    public String getTranslationText() {
        return translationText;
    }
}
