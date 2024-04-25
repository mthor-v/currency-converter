import com.google.gson.Gson;
import dto.CodeResponseDTO;
import dto.ConversionResponseDTO;
import dto.RequestDTO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class MainClass {

    private static final String welcomeFilePath = "src/utils/usual-currencies.txt";
    private static final String supportCodesFilePath = "src/utils/currencies.txt";
    private static final String configFilePath = "src/utils/config.json";
    // Instances
    private static final Client client = new Client();
    private static final Gson gson = new Gson();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException, InterruptedException {

        String APIKey = null;
        try {
            Config config = gson.fromJson(new FileReader(configFilePath), Config.class);
            APIKey = config.getApiKey();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> referenceCodes = new ArrayList<>();
        try {
            String response = client.sendCodesRequest(APIKey);
            CodeResponseDTO responseObject = gson.fromJson(response, CodeResponseDTO.class);
            referenceCodes = getCodes(responseObject);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Request failed.");
        }

        displayFiles(welcomeFilePath);

        boolean endFlag = true;
        while (endFlag) {
            String from, to;
            int amount;
            while (true){
                System.out.println("**************************************************");
                System.out.print("Ingresa el código de la moneda a convertir: ");
                String choice = sc.next();
                if (choice.equals("code")){
                    displayFiles(supportCodesFilePath);
                } else if (referenceCodes.contains(choice.toUpperCase())){
                    from = choice;
                    break;
                } else System.out.println("Código no identificado.");
            }
            while (true){
                System.out.println("**************************************************");
                System.out.print("Ingresa el código de la moneda final: ");
                String choice = sc.next();
                if (choice.equals("code")){
                    displayFiles(supportCodesFilePath);
                } else if (referenceCodes.contains(choice.toUpperCase())){
                    to = choice;
                    break;
                } else System.out.println("Código no identificado.");
            }
            while (true){
                while (true){
                    try{
                        System.out.println("*************************************************************");
                        System.out.print("Ingresa el valor a convertir sin puntos ni espacios: ");
                        amount = sc.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Ingresa un número valido.");
                        sc.nextLine();
                    }
                }
                RequestDTO requestData = new RequestDTO(from,to,amount);
                String result = printResult(requestData,APIKey);
                if (result.equals("2")) break;
                if (result.equals("3")){
                    endFlag = false;
                    break;
                }
            }
        }
        System.out.println("¡Programa finalizado!");
    }

     private static void displayFiles(String filePath){
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    private static List<String> getCodes(CodeResponseDTO info){
        List<String> codes = new ArrayList<>();
        for(String[] pair: info.getSupportedCodes()){
            codes.add(pair[0]);
        }
        return codes;
    }

    private static String printResult(RequestDTO requestData,String key){
        try {
            String response = client.sendConversionRequest(requestData,key);
            ConversionResponseDTO responseObject = gson.fromJson(response, ConversionResponseDTO.class);
            Instant instant = Instant.ofEpochSecond(responseObject.getTimeLastUpdateUnix());
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate localDate = instant.atZone(zoneId).toLocalDate();
            String from = requestData.getFromCurrency().toUpperCase();
            String to = requestData.getToCurrency().toUpperCase();
            System.out.println("*************************************************************");
            System.out.printf("Tipo de cambio: De [%s] a [%s]%n", from, to);
            System.out.printf("Precio por %s: %s %.2f - Actualizado a: %s%n",
                    from, to, responseObject.getConversionRate(), localDate.toString());
            System.out.printf("%s %d corresponden a: %s %.2f%n",
                    from, requestData.getAmount(), to, responseObject.getConversionResult());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Request faliled.");
            return "3";
        }
        List<String> referenceValues = Arrays.asList("1", "2", "3");
        while (true){
            System.out.println("*************************************************************");
            System.out.println("¿Qué deseas hacer ahora?: ");
            System.out.println("1-Nuevo valor, 2-Cambiar divisas, 3-Salir");
            String choice = sc.next();
            if (referenceValues.contains(choice)){
                return choice;
            }else System.out.println("Ingresa una opción valida");
        }
    }

    static class Config {
        private String apiKey;

        public String getApiKey() {
            return apiKey;
        }
    }
}
