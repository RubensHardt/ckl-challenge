package rubenshardt.ckl_challenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by rubenshardtjunior on 11/28/16.
 */

public class ArticleListFragment extends Fragment {

    public static ArticleListFragment newInstance() {
        return new ArticleListFragment();
    }

    public ArticleListFragment() {
        // Required empty public constructor
    }

    private Realm realm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_article_list, container, false);

        // Sets up a realm instance
        Realm.init(getContext());
        final RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        // 'realm' is a field variable
        realm = Realm.getInstance(config);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //close the real instance before onDestroy
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }
}