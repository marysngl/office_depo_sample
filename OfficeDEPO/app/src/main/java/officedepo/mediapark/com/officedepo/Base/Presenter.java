package officedepo.mediapark.com.officedepo.Base;

/**
 * Created by Mary Songal on 03.11.2016.
 */

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}