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

    @GET("/{instituteID}.json/")
    suspend fun getInstituteDetails(
        @Path(value = "instituteID") instituteID: String
    ): Response<Institution>

    @GET("/{instituteID}/classList.json/")
    suspend fun getClassList(
        @Path(value = "instituteID") instituteID: String,
    ): Response<List<Class>>

    @PUT("/{instituteID}/classList.json/")
    suspend fun setClassList(
        @Path(value = "instituteID") instituteID: String,
        @Body classList: List<Class>
    )
}