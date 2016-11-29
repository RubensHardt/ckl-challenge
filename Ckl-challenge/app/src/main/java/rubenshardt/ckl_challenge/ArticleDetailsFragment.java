package rubenshardt.ckl_challenge;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rubenshardtjunior on 11/29/16.
 */

public class ArticleDetailsFragment extends Fragment {

    //declare the constants that will be used to set and get the bundle data
    private static final String KEY_INFO_TITLE = "mTitle";
    private static final String KEY_INFO_WEBSITE = "mWebsite";
    private static final String KEY_INFO_AUTHORS = "mAuthors";
    private static final String KEY_INFO_DATE = "mDate";
    private static final String KEY_INFO_CONTENT = "mContent";
    private static final String KEY_INFO_LABEL = "mLabel";
    private static final String KEY_INFO_IMAGE = "mImageStrUri";

    // bind the component instance with its layout component using ButterKnife
    @BindView(R.id.article_details_date) TextView mDateTextView; //
    @BindView(R.id.article_details_label) TextView mLabelTextView;
    @BindView(R.id.article_details_title) TextView mTitleTextView;
    @BindView(R.id.image_details_default) ImageView mImgImageView;
    @BindView(R.id.article_details_content) TextView mContentTextView;
    @BindView(R.id.article_details_authors) TextView mAuthorsTextView;
    @BindView(R.id.article_details_website) TextView mWebsiteTextView;

    // get the default image Uri and convert it to a string type
    Uri uriImageDefault = Uri.parse("android.resource://rubenshardt.cheesecakechallenge/drawable/news_default");
    String stringUriImageDefault = uriImageDefault.toString();

    public static ArticleDetailsFragment newInstance(String mTitle, String mWebsite, String mAuthors, String mDate, String mContent, String mLabel, String mImageStrUri) {

        //when the ArticleDetailsFragment is created, put the selected article data in a bundle
        final Bundle args = new Bundle();
        args.putString(KEY_INFO_TITLE, mTitle);
        args.putString(KEY_INFO_WEBSITE, mWebsite);
        args.putString(KEY_INFO_AUTHORS, mAuthors);
        args.putString(KEY_INFO_DATE, mDate);
        args.putString(KEY_INFO_CONTENT, mContent);
        args.putString(KEY_INFO_LABEL, mLabel);
        args.putString(KEY_INFO_IMAGE, mImageStrUri);
        final ArticleDetailsFragment fragment = new ArticleDetailsFragment();
        fragment.setArguments(args);        //set the bundle in the new fragment instance
        return fragment;
    }

    public ArticleDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_article_details, container, false);

        //bind the views
        ButterKnife.bind(this, view);

        //retrieve the bundle data
        final Bundle args = getArguments();
        String mDate = args.getString(KEY_INFO_DATE);
        String mLabel = args.getString(KEY_INFO_LABEL);
        String mTitle = args.getString(KEY_INFO_TITLE);
        String mContent = args.getString(KEY_INFO_CONTENT);
        String mImage = args.getString(KEY_INFO_IMAGE);
        String mAuthors = args.getString(KEY_INFO_AUTHORS);
        String mWebsite = args.getString(KEY_INFO_WEBSITE);

        //set the article details information
        mDateTextView.setText(mDate);
        mLabelTextView.setText(mLabel);
        mTitleTextView.setText(mTitle);
        mContentTextView.setText(mContent);
        mAuthorsTextView.setText(mAuthors);
        mWebsiteTextView.setText(mWebsite);

        //if the image is the default image (which means the image in the JSON file is null),
        // doesn't show it in the details
        if (!mImage.equals(stringUriImageDefault)){
            Uri mUri = Uri.parse(mImage);
            mImgImageView.setImageURI(mUri);
        }
        return view;
    }
}