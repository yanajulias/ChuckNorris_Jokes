package test.app.cnjokes.data


import com.google.gson.annotations.SerializedName

data class JokesResponse(
    @SerializedName("result")
    val result: List<Result>? = null,
    @SerializedName("total")
    val total: Int
) {
    data class Result(
        @SerializedName("categories")
        val categories: List<String>? = null,
        @SerializedName("created_at")
        val createdAt: String? = null,
        @SerializedName("icon_url")
        val iconUrl: String? = null,
        @SerializedName("id")
        val id: String? = null,
        @SerializedName("updated_at")
        val updatedAt: String? = null,
        @SerializedName("url")
        val url: String? = null,
        @SerializedName("value")
        val value: String? = null
    )
}