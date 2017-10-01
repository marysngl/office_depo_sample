package officedepo.mediapark.com.officedepo.Model.API;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import officedepo.mediapark.com.officedepo.ui.App;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mary Songal on 28.11.2016.
 */

public class ApiFactory {

    private static final String BASE_URL = "http://217.168.64.242:52214";

    private static final long READ_TIMEOUT = 60;
    private static final int WRITE_TIMEOUT = 120;
    private static final int CONNECT_TIMEOUT = 10;

    private static GsonBuilder gsonBuilder = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .registerTypeAdapterFactory(new ResponseTypeAdapterFactory());

    private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            //.addInterceptor(new ErrorInterceptor())
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

    private static class ErrorInterceptor implements Interceptor {

        private static final String TAG = "ErrorInterceptor";
        private static final String RESPONSE = "response";
        private static final String ERROR = "error";

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String url = request.url().toString();
            Log.i(TAG, "Interception: " + url);
            Response response = chain.proceed(request);
            try {
                Gson gson = new Gson();
                String responseBody = response.body().string().toLowerCase();
                JSONObject jsonObject = new JSONObject(responseBody);
                if (jsonObject.has(RESPONSE)) {
                    jsonObject = jsonObject.getJSONObject(RESPONSE);
                    if (jsonObject.has(ERROR)) {
                        ErrorMessage errorMessage = gson.fromJson(jsonObject.toString(), ErrorMessage.class);
                        App.bus.post(errorMessage);
                    }
                }
            } catch (JsonSyntaxException | IOException | JSONException e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    private static class ResponseTypeAdapterFactory implements TypeAdapterFactory {

        private static final String RESPONSE = "response";
        private static final String ACTION_LIST = "actionList";
        private static final String RESULT = "result";

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            final TypeAdapter<T> delegateAdapter = gson.getDelegateAdapter(this, type);
            final TypeAdapter<JsonElement> jsonElementAdapter = gson.getAdapter(JsonElement.class);
            return new TypeAdapter<T>() {
                @Override
                public void write(JsonWriter out, T value) throws IOException {
                    delegateAdapter.write(out, value);
                }

                @Override
                public T read(JsonReader in) throws IOException {
                    // Ignore extraneous data and read in only the response data when the response is a success
                    JsonElement jsonElement = jsonElementAdapter.read(in);
                    if (jsonElement.isJsonObject()) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        if (jsonObject.has(RESPONSE)) {
                            jsonElement = jsonObject.get(RESPONSE);
                            jsonObject = jsonElement.getAsJsonObject();
                            if (jsonObject.has("error") || jsonObject.has("errorno")) {
                                return null;
                            }
                            else if (jsonObject.has(ACTION_LIST)) {
                                jsonElement = jsonObject.get(ACTION_LIST);
                            } else if (jsonObject.has(RESULT)) {
                                jsonElement = jsonObject.get(RESULT);
                            }
                        }
                    }
                    try {
                        return delegateAdapter.fromJsonTree(jsonElement);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }.nullSafe();
        }
    }


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(httpClientBuilder.build());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClientBuilder.build()).build();
        return retrofit.create(serviceClass);
    }


}
