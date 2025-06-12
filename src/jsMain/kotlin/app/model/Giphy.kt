package app.model

import kotlinx.serialization.Serializable

@Serializable
data class GiphyResponse(val data: List<Gif>)

@Serializable
data class Gif(val images: GifImages)

@Serializable
data class GifImages(val fixed_height: GifImage)

@Serializable
data class GifImage(val url: String)
