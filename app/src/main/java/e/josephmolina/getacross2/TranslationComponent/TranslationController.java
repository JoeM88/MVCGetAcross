package e.josephmolina.getacross2.TranslationComponent;

import e.josephmolina.getacross2.Data.YandexAPI;
import e.josephmolina.getacross2.Data.YandexService;
import e.josephmolina.getacross2.Logic.BaseController;

import static e.josephmolina.getacross2.Data.YandexAPI.translateText;

public class TranslationController extends BaseController<TranslationComponentInterface> {

    private YandexService yandexAPI;

    @Override
    public void onBind() {
        yandexAPI = YandexAPI.getRetrofitInstance();
    }

    public void makeAPICall(String text) {
        translateText(text);
    }

    public void displayTranslatedText(String text) {
        getView().displayTranslatedText(text);
    }

}
