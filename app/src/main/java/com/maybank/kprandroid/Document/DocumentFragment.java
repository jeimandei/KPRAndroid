package com.maybank.kprandroid.Document;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.maybank.kprandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DocumentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocumentFragment extends Fragment {
    private ViewGroup viewGroup;
    String id_cust;


    public static DocumentFragment newInstance(String param1, String param2) {
        DocumentFragment fragment = new DocumentFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_document, container, false);
        id_cust = this.getArguments().getString("id_nsb");

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Customer Documents");

        WebView webView = (WebView) viewGroup.findViewById(R.id.webViewDocument);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://kprandroid.000webhostapp.com/kpr/document_webview/html/?id_nsb=" + id_cust);

        return viewGroup;
    }
}