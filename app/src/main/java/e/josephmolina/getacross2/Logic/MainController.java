package e.josephmolina.getacross2.Logic;

import e.josephmolina.getacross2.View.ViewInterface;

public class MainController extends BaseController<ViewInterface> {

    @Override
    public void onBind() {
        displayDefaultLayout();
    }

    private void displayDefaultLayout() {
        getView().displayTranslateFragment();
    }
}