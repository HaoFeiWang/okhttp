package okhttp3.sample;

import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Connection connection = chain.connection();
        System.out.println("request head: "+connection.protocol());
        System.out.println("request head: "+request.headers());

        Response response = chain.proceed(request);

        System.out.println("response head: "+response.protocol());
        System.out.println("response head: "+response.headers());

        return response;
    }
}