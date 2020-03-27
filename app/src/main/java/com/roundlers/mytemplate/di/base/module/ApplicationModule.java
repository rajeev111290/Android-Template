package com.roundlers.mytemplate.di.base.module;

import android.app.Application;
import android.content.Context;

import com.roundlers.mytemplate.BuildConfig;
import com.roundlers.mytemplate.constants.BuildConstants;

import java.util.TimeZone;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    public static OkHttpClient getClient(final Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(getRequestInterceptor(context));
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConstants.DEBUG ? HttpLoggingInterceptor.Level.BODY :
                HttpLoggingInterceptor.Level.NONE);
        return builder.addInterceptor(interceptor).build();
    }

    private static Interceptor getRequestInterceptor(final Context context) {
        return chain -> {

            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder();
//            String cookie = SharedPreferencesHelper.getCookie();
//            if (cookie != null) {
//                builder.header("Cookie",
//                        SharedPreferencesHelper.getCookie() + ";statefree=true");
//            }
            Request request = builder
                    .header("isMobile", "true")
                    .header("appVersion", String.valueOf(BuildConfig.VERSION_CODE))
                    .header("appType", "revamped")
                    .header("deviceType", "android_gradeup")
                    .header("timezone", TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT))
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .method(originalRequest.method(), originalRequest.body())
                    .url(originalRequest.url())
                    .build();
            return chain.proceed(request);
        };
    }

    @Provides
    @Singleton
    public static Retrofit getRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BuildConstants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

//    @Provides
//    @Singleton
//    MyDatabase database(Context context) {
//        return Room.databaseBuilder(context,
//                MyDatabase.class, "hades-db")
//                .fallbackToDestructiveMigration()
//                .build();
//    }
//
//    @Provides
//    static GoogleSignInOptions googleSignInOptions() {
//        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .requestProfile()
//                .requestIdToken(AuthConstants.GOOGLE_LOGIN_REQUEST_ID_TOKEN)
//                .build();
//    }
//
//    @Provides
//    static GoogleApiClient googleApiClient(GoogleSignInOptions googleSignInOptions, Context context) {
//        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
//                .build();
//        googleApiClient.connect();
//        return googleApiClient;
//    }
//
//    @Provides
//    @Singleton
//    static CredentialRequest getCredentialRequest() {
//        return new CredentialRequest.Builder()
//                .setPasswordLoginSupported(true)
//                .setIdTokenRequested(true)
//                .setAccountTypes(IdentityProviders.GOOGLE, IdentityProviders.FACEBOOK)
//                .build();
//    }
//
//
//    @Provides
//    @Named("json")
//    @Singleton
//    static OkHttpClient getJsonContentClient(final Context context) {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(getJsonContentRequestInterceptor(context));
////        builder.addInterceptor(getResponseInterceptor(context));
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(BuildConstants.DEBUG ? HttpLoggingInterceptor.Level.BODY :
//                HttpLoggingInterceptor.Level.NONE);
//        return builder.addInterceptor(interceptor).build();
//    }
//
//    @Provides
//    @Named("graph")
//    @Singleton
//    static OkHttpClient getGraphClient(final Context context) {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addNetworkInterceptor(getGraphRequestInterceptor(context));
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(BuildConstants.DEBUG ? HttpLoggingInterceptor.Level.BODY :
//                HttpLoggingInterceptor.Level.NONE);
//        return builder.addNetworkInterceptor(interceptor).build();
//    }
//
//    @Provides
//    @Named("youtube")
//    @Singleton
//    static OkHttpClient getYoutubeContentClient(final Context context) {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(getRequestInterceptor(context));
////        builder.addInterceptor(getResponseInterceptor(context));
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(BuildConstants.DEBUG ? HttpLoggingInterceptor.Level.BODY :
//                HttpLoggingInterceptor.Level.NONE);
//        return builder.addInterceptor(interceptor).build();
//    }
//
//    @Provides
//    @Named("GTM")
//    @Singleton
//    static OkHttpClient getGTMContentClient(final Context context) {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(getRequestInterceptor(context));
////        builder.addInterceptor(getResponseInterceptor(context));
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(BuildConstants.DEBUG ? HttpLoggingInterceptor.Level.BODY :
//                HttpLoggingInterceptor.Level.NONE);
//        return builder.addInterceptor(interceptor).build();
//    }
//
//    @Provides
//    @Singleton
//    public static Retrofit getRetrofit(OkHttpClient client) {
//        return new Retrofit.Builder()
//                .baseUrl(Endpoints.BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//    }
//
//    @Provides
//    @Singleton
//    @Named("json")
//    static Retrofit getJsonRetrofit(@Named("json") OkHttpClient client) {
//        return new Retrofit.Builder()
//                .baseUrl(Endpoints.BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//    }
//
//    @Provides
//    @Singleton
//    @Named("images")
//    static Retrofit getImagesRetrofit(@Named("images") OkHttpClient client) {
//        return new Retrofit.Builder()
//                .baseUrl(Endpoints.IMAGE_UPLOAD_ENDPOINT)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//    }
//
//    @Provides
//    @Singleton
//    @Named("liveClass")
//    static Retrofit getLiveRetrofit(@Named("liveClass") OkHttpClient client) {
//        return new Retrofit.Builder()
//                .baseUrl(Endpoints.IMAGE_UPLOAD_ENDPOINT)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//    }
//
//
//    @Provides
//    @Singleton
//    @Named("youtube")
//    static Retrofit getYoutubeRetrofit(@Named("youtube") OkHttpClient client) {
//        return new Retrofit.Builder()
//                .baseUrl(Endpoints.YOUTUBE_ROOT)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//    }
//
//    @Provides
//    @Singleton
//    @Named("GTM")
//    static Retrofit getGTMRetrofit(@Named("GTM") OkHttpClient client) {
//        return new Retrofit.Builder()
//                .baseUrl(Endpoints.BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//    }
//
//    @Provides
//    @Singleton
//    @Named("login")
//    public static Retrofit getLogInRetrofit(@Named("login") OkHttpClient client) {
//        return new Retrofit.Builder()
//                .baseUrl(Endpoints.BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//    }
//
//    @Provides
//    @Singleton
//    @Named("login")
//    public static OkHttpClient getLogInClient(final Context context) {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(getRequestInterceptor(context));
//        builder.addInterceptor(getResponseInterceptorLogin(context));
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(BuildConstants.DEBUG ? HttpLoggingInterceptor.Level.BODY :
//                HttpLoggingInterceptor.Level.NONE);
//        return builder.addInterceptor(interceptor).build();
//    }
//
//
//    @Provides
//    @Singleton
//    @Named("images")
//    public static OkHttpClient getImagesClient(final Context context) {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(getRequestInterceptor(context));
//        builder.addInterceptor(getResponseInterceptorImages(context));
//
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(BuildConstants.DEBUG ? HttpLoggingInterceptor.Level.BODY :
//                HttpLoggingInterceptor.Level.NONE);
//        return builder.addInterceptor(interceptor).build();
//    }
//
//
//    @Provides
//    @Singleton
//    @Named("liveClass")
//    public static OkHttpClient getLiveClient(final Context context) {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(getRequestInterceptor(context));
//        builder.addInterceptor(getResponseInterceptorImages(context));
//
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(BuildConstants.DEBUG ? HttpLoggingInterceptor.Level.BODY :
//                HttpLoggingInterceptor.Level.NONE);
//        return builder.addInterceptor(interceptor).build();
//    }
//
//    @Provides
//    @Singleton
//    @Named("logout")
//    public static OkHttpClient getLogoutClient(final Context context) {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(getRequestInterceptor(context));
//        builder.addInterceptor(getResponseInterceptorLogout(context));
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(BuildConstants.DEBUG ? HttpLoggingInterceptor.Level.BODY :
//                HttpLoggingInterceptor.Level.NONE);
//        return builder.addInterceptor(interceptor).build();
//    }
//
//    @Provides
//    @Singleton
//    @Named("logout")
//    public static Retrofit getLogoutRetrofit(@Named("logout") OkHttpClient client) {
//        return new Retrofit.Builder()
//                .baseUrl(Endpoints.BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//    }
//
//
//    @Provides
//    @Singleton
//    public static ApolloClient getApolloClient(final Context context, @Named("graph") OkHttpClient client) {
//
//        File file = context.getApplicationContext().getCacheDir().getAbsoluteFile();
//        int size = 1024 * 1024 * 100;
//
//        DiskLruHttpCacheStore cacheStore = new DiskLruHttpCacheStore(file, size);
////        ApolloSqlHelper apolloSqlHelper = ApolloSqlHelper.create(context, "db_name");
////        NormalizedCacheFactory cacheFactory = new SqlNormalizedCacheFactory(apolloSqlHelper);
////
////        CacheKeyResolver resolver = new CacheKeyResolver() {
////            @NotNull
////            @Override
////            public CacheKey fromFieldRecordSet(@NotNull ResponseField field, @NotNull Map<String, Object> recordSet) {
////                if (recordSet.containsKey("id")) {
////                    String typeNameAndIDKey = recordSet.get("__typename") + "." + recordSet.get("id");
////                    return CacheKey.from(typeNameAndIDKey);
////                }
////                return CacheKey.NO_KEY;
////            }
////
////            @NotNull
////            @Override
////            public CacheKey fromFieldArguments(@NotNull ResponseField field, @NotNull Operation.Variables variables) {
////                return CacheKey.NO_KEY;
////            }
////
////            private CacheKey formatCacheKey(String id) {
////                if (id == null || id.length() == 0) {
////                    return CacheKey.NO_KEY;
////                } else {
////                    return CacheKey.from(id);
////                }
////            }
////        };
//
//        //.normalizedCache(cacheFactory, resolver)
//        return ApolloClient.builder()
//                .serverUrl(Endpoints.GRAPHQL_BASE_URL)
//                .httpCache(new ApolloHttpCache(cacheStore))
//                .defaultHttpCachePolicy(HttpCachePolicy.NETWORK_ONLY)
//                .addCustomTypeAdapter(CustomType.DATE, getCustomDateAdapter())
//                .addCustomTypeAdapter(CustomType.CURSOR, getCustomCursorAdapter())
//                .okHttpClient(client)
//                .build();
//    }
//
//
////    @Provides
////    @Singleton
////    public static ApolloClient getApolloClient(@Named("graph") OkHttpClient client) {
////        return ApolloClient.builder()
////                .serverUrl(Endpoints.GRAPHQL_BASE_URL)
////                .addCustomTypeAdapter(CustomType.DATE, getCustomDateAdapter())
////                .addCustomTypeAdapter(CustomType.CURSOR, getCustomCursorAdapter())
////                .okHttpClient(client)
////                .build();
////    }
//
//    @Provides
//    @Singleton
//    @Named("graphSocket")
//    public static ApolloClient getApolloSocketClient(@Named("graph") OkHttpClient client) {
//
//        ApolloClient build = ApolloClient.builder()
//                .serverUrl(Endpoints.GRAPHQL_BASE_URL)
//                .defaultHttpCachePolicy(HttpCachePolicy.NETWORK_ONLY)
//                .subscriptionTransportFactory(new WebSocketSubscriptionTransport.Factory(Endpoints.GRAPHQL_SOCKET_BASE_URL, client))
//                .subscriptionHeartbeatTimeout(20, TimeUnit.SECONDS)
//                .addCustomTypeAdapter(CustomType.DATE, getCustomDateAdapter())
//                .addCustomTypeAdapter(CustomType.CURSOR, getCustomCursorAdapter())
//                .defaultHttpCachePolicy(HttpCachePolicy.NETWORK_ONLY)
//                .okHttpClient(client)
//                .build();
//        return build;
//    }
//
//
//    public static CustomTypeAdapter<String> getCustomDateAdapter() {
//        return new CustomTypeAdapter<String>() {
//            @Override
//            public String decode(CustomTypeValue value) {
//                try {
//                    return value.value.toString();
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//            @Override
//            public CustomTypeValue encode(String value) {
//                return new CustomTypeValue.GraphQLString(value);
//            }
//        };
//
//    }
//
////    private static CustomTypeAdapter<LocalDateTime> getCustomDateTimeAdapter() {
////        return new CustomTypeAdapter<LocalDateTime>() {
////            @Override
////            public LocalDateTime decode(CustomTypeValue value) {
////                try {
////                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////                    return sdf.parse(value.value.toString());
////                } catch (ParseException e) {
////                    throw new RuntimeException(e);
////                }
////            }
////
////            @Override
////            public CustomTypeValue encode(LocalDateTime value) {
////                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////                return new CustomTypeValue.GraphQLString(sdf.format(value));
////            }
////        };
////
////    }
//
//    public static CustomTypeAdapter<String> getCustomCursorAdapter() {
//        return new CustomTypeAdapter<String>() {
//            @Override
//            public String decode(CustomTypeValue value) {
//                try {
//                    return value.value.toString();
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//            @Override
//            public CustomTypeValue encode(String value) {
//                return new CustomTypeValue.GraphQLString(value);
//            }
//        };
//
//    }
//
//    private static Interceptor getResponseInterceptorLogout(final Context context) {
//        return chain -> {
//            Response response = chain.proceed(chain.request());
//            SharedPreferencesHelper.setCookie(null);
//            return response;
//        };
//    }
//
//    private static Interceptor getResponseInterceptorLogin(final Context context) {
//        return chain -> {
//            Response response = chain.proceed(chain.request());
//
//            if (SharedPreferencesHelper.getCookie() == null) {
//                List<String> headers = response.headers("set-cookie");
//
//                if (headers.size() > 0) {
//                    String cookie = headers.get(headers.size() - 1);
//                    if (!cookie.startsWith("__cfduid"))
//                        SharedPreferencesHelper.setCookie(cookie);
//                }
//            }
//            return response;
//        };
//    }
//
//    private static Interceptor getResponseInterceptorImages(final Context context) {
//        return chain -> {
//            Request originalRequest = chain.request();
//            Request.Builder builder = originalRequest.newBuilder();
//            Request request = builder
//                    .header("X-API-KEY", "lEKZGJrVkw9w8jzaklcYu91RK4Hf4Am1v1Q1kL85")
//                    .method(originalRequest.method(), originalRequest.body())
//                    .url(originalRequest.url())
//                    .build();
//            return chain.proceed(request);
//        };
//    }
//

//
//    private static Interceptor getJsonContentRequestInterceptor(final Context context) {
//        return chain -> {
//            Request originalRequest = chain.request();
//            Request.Builder builder = originalRequest.newBuilder();
//            String cookie = SharedPreferencesHelper.getCookie();
//            if (cookie != null) {
//                builder.header("Cookie", SharedPreferencesHelper.getCookie());
//            }
//            Request request = builder.header("isMobile", "true")
//                    .header("appVersion", String.valueOf(BuildConfig.VERSION_CODE))
//                    .header("timezone", TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT))
//                    .header("appType", "revamped")
//                    .header("deviceType", "android_gradeup")
//                    .header("Content-Type", "application/json")
//                    .header("accept", "application/json")
//                    .method(originalRequest.method(), originalRequest.body())
//                    .url(originalRequest.url())
//                    .build();
//            return chain.proceed(request);
//        };
//    }
//
//    public static Interceptor getGraphRequestInterceptor(final Context context) {
//        return chain -> {
//            Request originalRequest = chain.request();
//            Request.Builder builder = originalRequest.newBuilder();
//            String cookie = SharedPreferencesHelper.getCookie();
//            if (cookie != null) {
//                builder.header("Cookie", SharedPreferencesHelper.getCookie());
//            }
//            Request request = builder.header("isMobile", "true")
//                    .header("appVersion", String.valueOf(BuildConfig.VERSION_CODE))
//                    .header("timezone", TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT))
//                    .header("appType", "revamped")
//                    .header("deviceType", "android_gradeup")
//                    .header("Content-Type", "application/json")
//                    .header("accept", "application/json")
//                    .header("authorization", "Basic bmluamE6aW1wZXJmZWN0bw==")
//                    .header("authority", Endpoints.BASE_URL)
//                    .method(originalRequest.method(), originalRequest.body())
//                    .url(originalRequest.url())
//                    .build();
//            return chain.proceed(request);
//        };
//    }

}
