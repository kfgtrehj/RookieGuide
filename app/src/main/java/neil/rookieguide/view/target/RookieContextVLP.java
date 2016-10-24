package neil.rookieguide.view.target;

/**
 * Created by neil on 16/10/14.
 * Rookie View Location Protocol VLP
 */
public class RookieContextVLP {
    private int resourceId;
    private ViewGravity viewGravity;

    public RookieContextVLP(int resourceId, ViewGravity viewGravity) {
        this.resourceId = resourceId;
        this.viewGravity = viewGravity;
    }

    public ViewGravity getViewGravity() {
        return viewGravity;
    }


    public int getResourceId() {
        return resourceId;
    }


}
