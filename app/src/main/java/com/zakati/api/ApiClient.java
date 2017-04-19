package com.zakati.api;


import com.google.gson.GsonBuilder;
import com.ta.base.App;
import com.ta.utils.Lg;
import com.ta.utils.LocaleHelper;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.amazonaws.http.HttpHeader.AUTHORIZATION;


/**
 * Created by rahil on 9/9/15.
 */
public class ApiClient {

    private static ApiClient client;
    //private static ApiClient instance;
    private Apis apis;

    public ApiClient() {
        //code for retrofit 2.0

        //setting up client
        OkHttpClient client = getUnsafeOkHttpClient();


        //rest adapter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                        /*.serializeNulls()*/
                        .create()))
                .build();

        apis = retrofit.create(Apis.class);

    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            Interceptor HEADER_INTERCEPTOR = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    String lang = LocaleHelper.getLanguage(App.getInstance());
                    Request.Builder builder = chain.request().newBuilder()
                            .addHeader("language", lang.equalsIgnoreCase("en") ? "en" : "ar");
                    builder.addHeader(AUTHORIZATION, JWTHelper.getJWTToken());

                    Response response = chain.proceed(builder.build());

                    if (!response.isSuccessful() && isForbidden(response.code())) {
                        Request newRequest = chain.request();
                        newRequest = newRequest.newBuilder()
                                .addHeader(AUTHORIZATION, JWTHelper.generateJwtToken())
                                .build();

                        response = chain.proceed(newRequest);
                    }
                    return response;
                }
            };


            //for logging
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

            loggingInterceptor.setLevel(Lg.ISDEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            // Create a trust manager that does not validate certificate chains
            /*final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


            //builder.sslSocketFactory(sslSocketFactory);
            builder.sslSocketFactory(sslSocketFactory, new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            });

            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });*/
            builder.addInterceptor(loggingInterceptor)
                    .addInterceptor(HEADER_INTERCEPTOR)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS);

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isForbidden(int code) {
        return code == HttpURLConnection.HTTP_FORBIDDEN;
    }

    public static ApiClient getClient() {
        if (client == null)
            client = new ApiClient();
        return client;
    }

    public Apis getApis() {
        return apis;
    }
}
