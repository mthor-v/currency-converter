import dto.RequestDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {

    private final HttpClient client;

    public Client() {
        client = HttpClient.newHttpClient();
    }

    public String sendConversionRequest(RequestDTO requestData, String key) throws IOException, InterruptedException {

        String baseURL = String.format(
                "https://v6.exchangerate-api.com/v6/%s/pair/%s/%s/%d",
                key, requestData.getFromCurrency(),requestData.getToCurrency(),requestData.getAmount());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String sendCodesRequest(String key) throws IOException, InterruptedException{

        String baseURL = String.format(
                "https://v6.exchangerate-api.com/v6/%s/codes", key);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

}
