
import POJO.PriceChangeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PriceChangeResponseApi {

    @GET("products/pricechange")
    Call<PriceChangeResponse> getPosts(
            @Query("weightID") String weightId,
            @Query("colorID") String colorId
    );


}
