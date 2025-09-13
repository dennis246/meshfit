package web.controller.scheme;

public interface WebViewScheme {

    /* String defaultPageLocation();

    String defaultStoreLocation(); */

    boolean scheduleView();

    boolean indexProps();

    boolean initiateView();

    boolean createView();

    boolean updateView();

    boolean applyProps();

    boolean applyAction(Object actionData);

    boolean cacheView();

    String prepLivePage();

    boolean disposeView();



}
