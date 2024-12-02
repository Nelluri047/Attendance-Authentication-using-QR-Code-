package app.attendance;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import app.attendance.data.Constants;
import app.attendance.data.PreferencesManager;
import app.attendance.data.api.ApiClient;
import app.attendance.data.api.ApiManager;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppController extends Application {

    private static PreferencesManager preferencesManager;
    private static ApiManager apiManager;

    @Override
    public void onCreate() {
        super.onCreate();
        preferencesManager = new PreferencesManager(this);
        apiManager = new ApiManager(getApplicationContext(), createApiClient(), preferencesManager);
    }

    public static PreferencesManager getPreferencesManager() {
        return preferencesManager;
    }

    public static ApiManager getApiManager() {
        return apiManager;
    }

    private ApiClient createApiClient() {
        return getRetrofit().create(ApiClient.class);
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);

        if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        builder.addNetworkInterceptor(chain -> {
            Request.Builder request = chain.request().newBuilder();
            if(preferencesManager.getAccessToken() != null) {
                request.addHeader("Authorization", "Bearer " + preferencesManager.getAccessToken());
            }
            return chain.proceed(request.build());
        });

        return builder.build();
    }
}
