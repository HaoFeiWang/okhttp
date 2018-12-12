package okhttp3.ssl;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import okhttp3.*;
import okhttp3.internal.tls.OkHostnameVerifier;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SslTest {
  private static final String ENDPOINT = "https://api.github.com/repos/square/okhttp/contributors";
  private static final Moshi MOSHI = new Moshi.Builder().build();
  private static final JsonAdapter<List<Contributor>> CONTRIBUTORS_JSON_ADAPTER = MOSHI.adapter(
      Types.newParameterizedType(List.class, Contributor.class));

  static class Contributor {
    String login;
    int contributions;
  }

  //拦截器是通过递归调用
  //Log1 start!
  //Log2 start!
  //Log2 end!
  //Log1 end!
  public static void main(String... args) throws Exception {
    X509TrustManager trustManager = SslTrustFactory.initTrustManager();
    OkHttpClient client = new OkHttpClient.Builder()
            .hostnameVerifier(new HostnameVerifier() {
              @Override
              public boolean verify(String hostname, SSLSession session) {
                System.out.println("verify hostname = "+hostname);
                return OkHostnameVerifier.INSTANCE.verify("api.github.com",session);
              }
            })
            .sslSocketFactory(SslTrustFactory.initSSLSocketFactory(trustManager),trustManager)
            .build();

    // Create request for remote resource.
    Request request = new Request.Builder()
        .url(ENDPOINT)
        .build();

    // Execute the request and retrieve the response.
    Call call = client.newCall(request);
    try (Response response = call.execute()) {
      // Deserialize HTTP response to concrete type.
      ResponseBody body = response.body();
      List<Contributor> contributors = CONTRIBUTORS_JSON_ADAPTER.fromJson(body.source());

      // Sort list by the most contributions.
      Collections.sort(contributors, new Comparator<Contributor>() {
        @Override public int compare(Contributor c1, Contributor c2) {
          return c2.contributions - c1.contributions;
        }
      });

      // Output list of contributors.
      for (Contributor contributor : contributors) {
        System.out.println(contributor.login + ": " + contributor.contributions);
      }
    }
  }

  private SslTest() {
    // No instances.
  }
}
