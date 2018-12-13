package okhttp3.sample;

import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.connection.RealConnection;

import java.io.IOException;

class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        System.out.println("request head : \n " + request.headers());

        Response response = chain.proceed(request);
        System.out.println("response head = \n " + response.headers());

        return response;
    }
}