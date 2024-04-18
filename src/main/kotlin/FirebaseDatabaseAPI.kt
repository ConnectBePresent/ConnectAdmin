import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface FirebaseDatabaseAPI {

    @PUT("/.json/")
    suspend fun setInstitutionsList(@Body institutionList: ArrayList<Institution>)

    @GET("/.json/")
    suspend fun getInstitutionsList(): ArrayList<Institution>

    @POST("/.json/")
    suspend fun registerInstitute(@Body institution: Institution)
}