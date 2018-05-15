package e.josephmolina.getacross2.Logic;

public abstract class BaseController<T> {
    private T view = null;

    public abstract void onBind();

    public void takeView(T view) {
        this.view = view;
        onBind();
    }

    public T getView() {
        return view;
    }
}
