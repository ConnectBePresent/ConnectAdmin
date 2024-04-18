import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface FirebaseDatabaseAPI {

    @PUT("/{instituteID}.json/")
    suspend fun registerInstitution(
        @Path(value = "instituteID") instituteID: String,
        @Body institution: Institution
    )

    @PUT("/{instituteID}.json/")
    suspend fun getInstituteDetails(@Path(value = "instituteID") instituteID: String): Response<Institution>

    @PUT("/.json/")
    suspend fun setInstitutionsList(@Body institutionList: ArrayList<Institution>)

    @GET("/.json/")
    suspend fun getInstitutionsList(): ArrayList<Institution>
}