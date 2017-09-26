package com.applicaster.feedintegrationsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.applicaster.activities.base.AppIntroActivity;
import com.applicaster.app.APProperties;
import com.applicaster.loader.json.APAccountLoader;
import com.applicaster.model.APAccount;
import com.applicaster.util.AppData;
import com.applicaster.util.UrlSchemeUtil;
import com.applicaster.util.asynctask.APAccountLoaderListener;
import com.applicaster.util.facebook.applink.APPLink;

public class SplashActivity extends AppIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Loads Applicaster's application data
        APAccountLoader applicasterAccountLoader = new APAccountLoader(new ApplicasterAccountLoaderListener(this),
                AppData.getProperty(APProperties.ACCOUNT_ID_KEY));
        applicasterAccountLoader.loadBean();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // handle returning from  Feed screen launched by Url Scheme (use if implementing Feed)
        if (requestCode == UrlSchemeUtil.FEED_REQUEST_CODE) {
            onIntroFinished();
        }
        // handle returning from  Crossmates screen launched by Url Scheme(used only if implementing Crossmates)
        if (requestCode == UrlSchemeUtil.CROSSMATES_REQUEST_CODE) {
            onIntroFinished();
        }
    }


    @Override
    protected void onIntroFinished() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void performAdditionalLoading() {

    }

    /**
     * AsyncTask listener of Applicaster initial data loading
     */
    public class ApplicasterAccountLoaderListener extends APAccountLoaderListener {

        private Activity activity;

        public ApplicasterAccountLoaderListener(Activity activity) {
            super(activity);
            this.activity = activity;
        }


        @Override
        public void handleApplicationDataLoaded(APAccount account) {
            AppData.persistAppData(activity);

            // handling launching Crossmates/Feed from web links, etc.
            APPLink appLink = new APPLink(getIntent());

            if (!UrlSchemeUtil.handleInternalUrlScheme(activity, appLink.getUrlScheme().toString())) {
                onIntroFinished();
            }
        }

        @Override
        public void handleApplicationDataLoadFailed(Exception e) {
            // if failed to load Applicaster data continue with application flow
            onIntroFinished();
        }
    }
}
