import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CheckRepositoryApi {

    @GET("products/checkrepository")
    Call<checkRepositoryResponse> getPosts(
            @Query("productID") String productId,
            @Query("Count") String count,
            @Query("colorID") String colorId,
            @Query("weightID") String weightId
    );

}
