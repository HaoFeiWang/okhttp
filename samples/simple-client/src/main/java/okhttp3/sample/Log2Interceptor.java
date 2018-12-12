package okhttp3.sample;

import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

class Log2Interceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        System.out.println("request head = \n ");

        Response response = chain.proceed(request);
        System.out.println("request head = \n ");


        return response;
    }
}