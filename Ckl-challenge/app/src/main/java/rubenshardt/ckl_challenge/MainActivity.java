package rubenshardt.ckl_challenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ArticleListFragment.OnArticleSelected {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the current screen width
        ScreenUtility utility = new ScreenUtility(this);
        //if the screen width is smaller than 500dp, create a new instance of ArticleListFragment and add it
        // to the respective the main layout (using the full screen)
        if(utility.getWidth() < 500.0) {
            if (savedInstanceState == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.root_layout, ArticleListFragment.newInstance(), "articlesList")
                        .commit();
            }
        }
        //if the screen width is bigger than 500dp, the main layout for screens bigger than 500dp will be
        // used and a new instance of the fragment will be created using half of screen
        // (called from the xml layout file)
    }
    // when an article is selected by user
    @Override
    public void onArticleSelected(String mTitle, String mWebsite, String mAuthors, String mDate, String mContent, String mLabel, String mImageStrUri) {

        // get the current screen width
        ScreenUtility utility = new ScreenUtility(this);
        //if the screen width is smaller than 500dp, replace the ArticleListFragment with the detailsFragment
        if(utility.getWidth() < 500.0) {
            final ArticleDetailsFragment detailsFragment =
                    ArticleDetailsFragment.newInstance(mTitle, mWebsite, mAuthors, mDate, mContent, mLabel, mImageStrUri);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.root_layout, detailsFragment, "articleDetails")
                    .addToBackStack(null)
                    .commit();
        }
        else {  // the layout for wider screen is in use, so the details fragment is called
                // and update his respective half of screen
            final ArticleDetailsFragment detailsFragment =
                    ArticleDetailsFragment.newInstance(mTitle, mWebsite, mAuthors, mDate, mContent, mLabel, mImageStrUri);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detailsFragment, detailsFragment, "articleDetails")
                    .commit();
        }
    }
}
