package rubenshardt.ckl_challenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rubenshardtjunior on 11/29/16.
 */

public class ArticleDetailsEmptyFragment extends Fragment {


    public static ArticleDetailsEmptyFragment newInstance() {
        return new ArticleDetailsEmptyFragment();
    }

    public ArticleDetailsEmptyFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_empty_details, container, false);

        return view;
    }

}