package okhttp3.guide;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        System.out.println("request head = \n " + request.headers());

        Response response = chain.proceed(request);
        System.out.println("request head = \n " + response.headers());

        return response;
    }
}