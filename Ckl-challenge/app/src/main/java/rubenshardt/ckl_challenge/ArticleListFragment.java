package rubenshardt.ckl_challenge;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import io.realm.Sort;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by rubenshardtjunior on 11/28/16.
 */

public class ArticleListFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    public static ArticleListFragment newInstance() {
        return new ArticleListFragment();
    }

    public ArticleListFragment() {
        // Required empty public constructor
    }

    private Realm realm;
    RealmResults<Article> mArticles;
    ArticleListAdapter mArticleListAdapter;
    Activity activity = getActivity();
    private OnArticleSelected onArticleSelected;

    // bind the Spinner with its layout component using ButterKnife
    @BindView(R.id.articles_spinner) Spinner spinner;
    // bind the the RealmRecyclerView with its layout component using ButterKnife
    @BindView(R.id.realm_recycler_view) RealmRecyclerView realmRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_article_list, container, false);

        activity = getActivity();
        // initialize onArticleSelected
        onArticleSelected = (OnArticleSelected) getContext();

        //bind the views
        ButterKnife.bind(this, view);

        // Spinner click listener
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> SpinnerAdapter = ArrayAdapter.createFromResource(activity,
                R.array.options_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(SpinnerAdapter);

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
        mArticleListAdapter = new ArticleListAdapter(activity, mArticles, true, true);
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

    // override the onItemSelected method of the spinner adapter to treat the sort selection
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // sort by date
        if(item.equals("Date")){
            Toast.makeText(parent.getContext(), "Selected: Date", Toast.LENGTH_LONG).show();
            //realm query to get the articles ordered by date
            mArticles = realm.where(Article.class).findAllSorted("date", Sort.DESCENDING);
            // recreate and set the realmRecyclerView adapter
            mArticleListAdapter = new ArticleListAdapter(activity, mArticles, true, true);
            realmRecyclerView.setAdapter(mArticleListAdapter);
        }
        // sort by title
        if(item.equals("Title")){
            Toast.makeText(parent.getContext(), "Selected: Title", Toast.LENGTH_LONG).show();
            mArticles = realm.where(Article.class).findAllSorted("title");
            mArticleListAdapter = new ArticleListAdapter(activity, mArticles, true, true);
            realmRecyclerView.setAdapter(mArticleListAdapter);
        }
        // sort by website
        if(item.equals("Website")){
            Toast.makeText(parent.getContext(), "Selected: Website", Toast.LENGTH_LONG).show();
            mArticles = realm.where(Article.class).findAllSorted("website");
            mArticleListAdapter = new ArticleListAdapter(activity, mArticles, true, true);
            realmRecyclerView.setAdapter(mArticleListAdapter);
        }
        // sort by authors
        if (item.equals("Authors")){
            Toast.makeText(parent.getContext(), "Selected: Authors", Toast.LENGTH_LONG).show();
            mArticles = realm.where(Article.class).findAllSorted("authors");
            mArticleListAdapter = new ArticleListAdapter(activity, mArticles, true, true);
            realmRecyclerView.setAdapter(mArticleListAdapter);
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    // function to mark an article as read or unread
    public void onCheckboxSelected(boolean checked, long articleId, int position) {

        //begin a realm transaction
        realm.beginTransaction();               //query to get the selected article
        Article ArticleItem = realm.where(Article.class).equalTo("id", articleId).findFirst();
        ArticleItem.setRead(checked);           //set the passed boolean value
        realm.commitTransaction();

        //notify the adapter to update the view
        mArticleListAdapter.notifyItemChanged(position);
    }

    public class ArticleListAdapter extends RealmBasedRecyclerViewAdapter<Article, ArticleListAdapter.ViewHolder> {

        public class ViewHolder extends RealmViewHolder {

            // bind each layout component with its layout component using ButterKnife
            @BindView(R.id.article_title) TextView titleTextView;
            @BindView(R.id.article_authors) TextView authorsTextView;
            @BindView(R.id.article_date) TextView dateTextView;
            @BindView(R.id.image_default) ImageView imgImageView;
            @BindView(R.id.checkbox) CheckBox checkBox;

            public ViewHolder(final FrameLayout container) {
                super(container);

                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCheckboxSelected(true,realmResults.get(getAdapterPosition()).getId(),getAdapterPosition());
                        onDataSelected(container,getAdapterPosition());
                    }
                });

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

            // setOnClickListener to the checkbox
            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    boolean checked = ((CheckBox) v).isChecked();

                    if (checked){       //if the user check it, mark as read
                        onCheckboxSelected(true,mArticle.getId(), position);
                        Toast.makeText(v.getContext(), "Marked as read", Toast.LENGTH_LONG).show();
                    }
                    else {              //if the user uncheck it, mark as unread
                        onCheckboxSelected(false,mArticle.getId(), position);
                        Toast.makeText(v.getContext(), "Marked as unread", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        // onDataSelected calls the interface method onArticleSelected
        public void onDataSelected(FrameLayout container, int position) {

            //get the selected article
            final Article selectedItem = realmResults.get(position);

            //concatenate all the labels from the article tags
            String mLabelsString = "";
            for (int i = 0; i < selectedItem.getTags().size(); i++) {

                mLabelsString = mLabelsString + selectedItem.getTags().get(i).getLabel();
            }
            onArticleSelected.onArticleSelected(selectedItem.getTitle(), selectedItem.getWebsite(),
                    selectedItem.getAuthors(), selectedItem.getDate(), selectedItem.getContent(),
                    mLabelsString,selectedItem.getImage());
        }
    }
    // OnArticleSelected is an interface that will be implemented by the main activity
    public interface OnArticleSelected {
        // onArticleSelected is a method that, when implemented, will pass the select article
        // information to main activity, to call the article details fragment
        void onArticleSelected(String mTitle, String mWebsite, String mAuthors, String mDate, String mContent, String mLabel, String mImageStrUri);
    }
}