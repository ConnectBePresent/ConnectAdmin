import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import java.net.http.HttpResponse

interface FirebaseAPI {

//    @Headers("Content-Type: application/json")
//    @POST("/accounts:signUp")
//    suspend fun signUp(
//        @Header("email") email: String,
//        @Header("password") password: String,
//    )
//
//    @Headers("Content-Type: application/json")
//    @POST("/accounts:signInWithPassword?")
//    suspend fun signIn(
//        @Header("email") email: String,
//        @Header("password") password: String,
//    )

    @PUT("/.json/")
    suspend fun setInstitutionsList(@Body institutionList: ArrayList<Institution>)

    @GET("/.json/")
    suspend fun getInstitutionsList(): ArrayList<Institution>

    @POST("/.json/")
    suspend fun registerInstitute(@Body institution: Institution)
}