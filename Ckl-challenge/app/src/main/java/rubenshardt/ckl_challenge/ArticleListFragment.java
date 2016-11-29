package rubenshardt.ckl_challenge;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import io.realm.Sort;

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
    RealmResults<Article> mArticles;
    ArticleListAdapter mArticleListAdapter;

    // bind the the RealmRecyclerView with its layout component using ButterKnife
    @BindView(R.id.realm_recycler_view) RealmRecyclerView realmRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_article_list, container, false);

        //bind the views
        ButterKnife.bind(this, view);

        // Sets up a realm instance
        Realm.init(getContext());
        final RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        // 'realm' is a field variable
        realm = Realm.getInstance(config);
        //realm query to get the articles ordered by date
        mArticles = realm.where(Article.class).findAllSorted("date", Sort.DESCENDING);

        //create and set the realmRecyclerView adapter
        mArticleListAdapter = new ArticleListAdapter(getActivity(), mArticles, true, true);
        realmRecyclerView.setAdapter(mArticleListAdapter);

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

    public class ArticleListAdapter extends RealmBasedRecyclerViewAdapter<Article, ArticleListAdapter.ViewHolder> {

        private Context context;

        public class ViewHolder extends RealmViewHolder {

            // bind each layout component with its layout component using ButterKnife
            @BindView(R.id.article_title) TextView titleTextView;
            @BindView(R.id.article_authors) TextView authorsTextView;
            @BindView(R.id.article_date) TextView dateTextView;
            @BindView(R.id.image_default) ImageView imgImageView;
            @BindView(R.id.checkbox) CheckBox checkBox;

            public ViewHolder(final FrameLayout container) {
                super(container);

                ButterKnife.bind(this, container);

            }
        }

        public ArticleListAdapter(
                Context context,
                RealmResults<Article> realmResults,
                boolean automaticUpdate,
                boolean animateResults) {
            super(context, realmResults, automaticUpdate, animateResults);
        }

        @Override
        public ArticleListAdapter.ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
            View v = inflater.inflate(R.layout.article_layout, viewGroup, false);
            ArticleListAdapter.ViewHolder vh = new ArticleListAdapter.ViewHolder((FrameLayout) v);
            return vh;
        }


        // bind realm article data with its respective view
        @Override
        public void onBindRealmViewHolder(ArticleListAdapter.ViewHolder viewHolder, final int position) {
            final Article mArticle = realmResults.get(position);
            viewHolder.titleTextView.setText(mArticle.getTitle());
            viewHolder.authorsTextView.setText(mArticle.getAuthors());
            viewHolder.dateTextView.setText(mArticle.getDate());

            // transform the image string into a Uri
            String mStrUri = mArticle.getImage();
            Uri mUri = Uri.parse(mStrUri);

            viewHolder.imgImageView.setImageURI(mUri);
            viewHolder.checkBox.setChecked(mArticle.getRead());

        }
    }
}