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
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
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
    public void run() {
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

    public static void getData (int productId) {
        Document document;
        Product product = new Product();
        product.setId(productId);

        String url = "https://www.khanoumi.com/newproduct?thisID=" + productId;



        try {
            Connection.Response response = Jsoup.connect(url).ignoreHttpErrors(true).execute();

            if (response.statusCode() == 500){
                System.out.println(ANSI_YELLOW + productId + "- response code = 500" + ANSI_RESET);
                return;
            }
            document = response.parse();

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String title = document.select("div.subTitleContainer").select("h2").first().text().replaceAll("'", "");
        String brand = document.select("h3.mokamelTitle").first().select("a").attr("href");
        System.err.println(brand);


        product.setName(title);
        System.out.println(title);

        ArrayList<String> weights = new ArrayList<String>();
        ArrayList<String> weightsDesc = new ArrayList<String>();

        Elements weightElements = document.select("span.vazn");
        if (weightElements.size() > 0) {
            for (Element weight : weightElements) {
                String weightId = weight.attributes().get("data-weightid");
                String desc = weight.text();
                System.out.println(ANSI_PURPLE + desc + ANSI_RESET);
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
        if (colorElements.size() > 0) {
            for (Element color : colorElements) {
                String colorId = color.attributes().get("data-colorid");
                String colorDesc = color.attributes().get("data-colorname");
                colors.add(colorId);
                colorsDesc.add(colorDesc);

            }
        } else if (document.select("div.colrosPallet").first().select("div").first().select("script").first() != null){
            String script = document.select("div.colrosPallet").first().select("div").first().select("script").first().toString();
            colors.add(matchTheShits(script));
        } else { //TODO: fast fix
            String script = document.select("div.colrosPallet").first().select("div").first().select("span").attr("data-colorid");
            colors.add(script);
            System.err.println(script);
        }
        product.setColors(colors);
        product.setColorDescs(colorsDesc);


//        for (String weight : product.getSizes()){
        for (int i = 0; i < product.getSizes().size(); i++){
//            for (String color : product.getColors()){
            for (int j = 0; j < product.getColors().size(); j++){
                System.out.println("weight = " + product.getSizes().get(i) + " && color = " + product.getColors().get(j));

                PriceChangeResponse response = priceChangeRequest(product.getSizes().get(i),product.getColors().get(j));
                int result = (response.getBasePrice() + response.getColorPrice() + response.getWeightPrice());
                SQLiteJDBC.insert(new CompleteProduct(
                        product.getId(),
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
//                .client(okHttpClient)
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
            return response.body();
        } catch (Exception e){
            e.printStackTrace();
        }
        return priceChangeRequest(weightId, colorId);
    }


}
