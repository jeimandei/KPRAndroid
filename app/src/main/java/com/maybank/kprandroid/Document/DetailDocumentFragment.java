package com.maybank.kprandroid.Document;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.maybank.kprandroid.Configuration.ConfigLogin;
import com.maybank.kprandroid.Configuration.ConfigManager;
import com.maybank.kprandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailDocumentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailDocumentFragment extends Fragment {

    private ViewGroup viewGroup;
    String id_cust;
    SwipeRefreshLayout refresh;
    public DetailDocumentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailDocumentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailDocumentFragment newInstance(String param1, String param2) {
        DetailDocumentFragment fragment = new DetailDocumentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_detail_document, container, false);
        id_cust = this.getArguments().getString("id_nsb");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Documents Detail");
        refresh = (SwipeRefreshLayout) viewGroup.findViewById(R.id.refresh);
        WebView webView = (WebView) viewGroup.findViewById(R.id.webViewDocumentDetail);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);


        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(ConfigManager.IP + "kpr/document_webview/html/approval_manager.php?id_nsb=" + id_cust);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });

        return viewGroup;
    }
}