import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FirebaseAuthAPI {

    @Headers("Content-Type: application/json")
    @POST("v1/accounts:signUp?key=AIzaSyDxC52QD8BUoZBzupueO2A3pzXf70GFxjc")
    suspend fun signUp(@Body user: User): Response<UserResponse>

    @Headers("Content-Type: application/json")
    @POST("v1/accounts:signInWithPassword?key=AIzaSyDxC52QD8BUoZBzupueO2A3pzXf70GFxjc")
    suspend fun signIn(@Body user: User): Response<UserResponse>
}