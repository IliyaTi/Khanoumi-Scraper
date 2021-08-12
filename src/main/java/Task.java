import POJO.CompleteProduct;
import POJO.PriceChangeResponse;
import POJO.Product;
import okhttp3.OkHttpClient;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task implements Runnable{

    public static int i;
    static java.sql.Connection c;

    public Task(int i, java.sql.Connection c){
        Task.i = i;
        Task.c = c;
    }

    @Override
    public void run () {
            getData(i);
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static Document docMaker(String url,int productId) throws IOException {
//        try {
            Connection.Response response = Jsoup.connect(url).timeout(99999999).ignoreHttpErrors(true).execute();

            if (response.statusCode() == 500){
                System.out.println(ANSI_YELLOW + productId + "- response code = 500" + ANSI_RESET);
                return null;
            }
            return response.parse();
//        } catch (IOException e) {
//            System.err.println("FUCK");
//            getData(productId);
////            return;
//        }
    }

    public static void getData (int productId) {

//        System.out.println(ANSI_CYAN + i +"- " + Thread.currentThread().getName() +" getting product " + "-------------------------------------"+ ANSI_RESET);

        Document document = null;
        Product product = new Product();
        product.setId(productId);

        String url = "https://www.khanoumi.com/newproduct?thisID=" + productId;


        try {
            document = docMaker(url,productId);
            if (document == null) return;
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            Connection.Response response = Jsoup.connect(url).ignoreHttpErrors(true).execute();
//
//            if (response.statusCode() == 500){
//                System.out.println(ANSI_YELLOW + productId + "- response code = 500" + ANSI_RESET);
//                return;
//            }
//            document = response.parse();
//        } catch (IOException e) {
//            System.err.println("FUCK");
//            getData(productId);
////            return;
//        }

        String title = document.select("div.subTitleContainer").select("h2").first().text().replaceAll("'", "");
        String brand = document.select("div.brand").select("a[href]").attr("href").replaceAll("/", "");
        product.setBrand(brand);
        SQLiteJDBC.insertIntoBrands(brand,c);

        product.setName(title);
        System.out.println(title);

        ArrayList<String> weights = new ArrayList<String>();
        ArrayList<String> weightsDesc = new ArrayList<String>();

        Elements weightElements = document.select("span.vazn");
        if (weightElements.size() > 0) {
            for (Element weight : weightElements) {
                String weightId = weight.attributes().get("data-weightid");
                String desc = weight.text();
//                System.out.println(ANSI_PURPLE + desc + ANSI_RESET);
                weights.add(weightId);
                weightsDesc.add(desc);
            }
        } else {
            weights.add(document.select("#vazn").first().val());
        }
        product.setSizes(weights);
        product.setSizeDescs(weightsDesc);




        ArrayList<String> colors = new ArrayList<>();
        ArrayList<String> colorsDesc = new ArrayList<>();

        Elements colorElements = document.select("span[class=clr cunselect]");


        if (!document.select("div.colrosPallet").isEmpty()){
            if (colorElements.size() > 0) {
                for (Element color : colorElements) {
                    String colorId = color.attributes().get("data-colorid");
                    String colorDesc = color.attributes().get("data-colorname");
                    colors.add(colorId);
                    colorsDesc.add(colorDesc);
                }

//        Elements colorElements = document.select("span[class=clr cunselect]");
//        if (colorElements.size() > 0) {
//            for (Element color : colorElements) {
//                String colorId = color.attributes().get("data-colorid");
//                String colorDesc = color.attributes().get("data-colorname");
//                colors.add(colorId);
//                colorsDesc.add(colorDesc);

            } else if (document.select("div.colrosPallet").first().select("div").first().select("script").first() != null){
                String script = document.select("div.colrosPallet").first().select("div").first().select("script").first().toString();
                colors.add(matchTheShits(script));
            } else { //TODO: fast fix
                String script = document.select("div.colrosPallet").first().select("div").first().select("span").attr("data-colorid");
                colors.add(script);
//            System.err.println(script);
            }
//        } else if (document.select("div.colrosPallet").first().select("div").first().select("script").first() != null){
//            String script = document.select("div.colrosPallet").first().select("div").first().select("script").first().toString();
//            colors.add(matchTheShits(script));
//        } else { //TODO: fast fix
//            String script = document.select("div.colrosPallet").first().select("div").first().select("span").attr("data-colorid");
//            colors.add(script);
//            System.err.println(script);
        }
        product.setColors(colors);
        product.setColorDescs(colorsDesc);


//        for (String weight : product.getSizes()){
        for (int i = 0; i < product.getSizes().size(); i++){
//            for (String color : product.getColors()){
            for (int j = 0; j < product.getColors().size(); j++){
//                System.out.println("weight = " + product.getSizes().get(i) + " && color = " + product.getColors().get(j));

                PriceChangeResponse response = priceChangeRequest(product.getSizes().get(i),product.getColors().get(j));
                int result = (response.getBasePrice() + response.getColorPrice() + response.getWeightPrice());
                SQLiteJDBC.insert(new CompleteProduct(
                        product.getId(),
                        product.getBrand(),
                        product.getName(),
                        result,
                        response.getDiscount(),
                        product.getSizeDescs().isEmpty()? null: product.getSizeDescs().get(i),
                        product.getColorDescs().isEmpty()? null: product.getColorDescs().get(j),
                        product.getSizes().get(i),
                        product.getColors().get(j)

//                        product.getId(),
//                        product.getName(),
//                        result,
//                        response.getDiscount(),
//                        weight,
//                        color
                ), c);
            }
        }
    }

    public static String matchTheShits(String input) {
        String line = input.replaceAll(" ","").replaceAll("\n", "").replaceAll("\r", "");
        // Create a Pattern object
        Pattern r = Pattern.compile("\\d+");

        // Now create matcher object.
        Matcher m = r.matcher(line);
        m.find();
        return m.group();
    }

    public static PriceChangeResponse priceChangeRequest(String weightId, String colorId){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.khanoumi.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PriceChangeResponseApi priceChangeResponseApi = retrofit.create(PriceChangeResponseApi.class);

        Call<PriceChangeResponse> call = priceChangeResponseApi.getPosts(weightId, colorId);

//        call.enqueue(new Callback<PriceChangeResponse>() {
//            @Override
//            public void onResponse(Call<PriceChangeResponse> call, Response<PriceChangeResponse> response) {
//                PriceChangeResponse priceResponse = response.body();
//
//            }
//
//            @Override
//            public void onFailure(Call<PriceChangeResponse> call, Throwable t) {
//
//            }
//        });
        try {
            Response<PriceChangeResponse> response = call.execute();
            if (response.body() != null){
                return response.body();
            } else {return new PriceChangeResponse();}
        } catch (Exception e){
            e.printStackTrace();
//            return new PriceChangeResponse();
        }
        return priceChangeRequest(weightId, colorId);
    }

    public static checkRepositoryResponse checkRepository (String productId, String colorId, String weightId, String count) {
        String baseUrl = "https://www.khanoumi.com/";

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CheckRepositoryApi checkRepositoryApi = retrofit.create(CheckRepositoryApi.class);

        Call<checkRepositoryResponse> call = checkRepositoryApi.getPosts(productId, count, colorId, weightId);

        try {
            Response<checkRepositoryResponse> response = call.execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return checkRepository(productId, colorId, weightId, count);

    }

    public static int binarySearch (String productId, String colorId, String weightId) throws Throwable {

        int min = 0;
        int max = 500;
        int middle = 250;
        String[] triple;
        int target;

        while (max - min >= 2) {
            middle = (min + max)/2;
            System.out.println(middle);
            switch (checkRepository(productId, colorId, weightId, String.valueOf(middle)).getStatus()) {
                case "true":
                    min = middle;
                    break;
                case "false":
                    max = middle;
                    break;
            }
        }

        triple = new String[]{
                checkRepository(productId, colorId, weightId, String.valueOf(middle - 1)).getStatus(),
                checkRepository(productId, colorId, weightId, String.valueOf(middle)).getStatus(),
                checkRepository(productId, colorId, weightId, String.valueOf(middle + 1)).getStatus()
        };

//        System.out.println(triple[0] + triple[1] + triple[2]);

        switch (triple[0] + triple[1] + triple[2]){
            case "truefalsefalse":
                target = middle - 1;
                break;
            case "truetruefalse":
                target = middle;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + triple[0] + triple[1] + triple[2]);
        }
//        System.out.println("target is: " + target);
        return target;
    }


}
