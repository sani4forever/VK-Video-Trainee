package com.example.vkvideotrainee

import androidx.media3.exoplayer.ExoPlayer
import com.example.vkvideotrainee.fragments.VideoPlayerFragment
import io.mockk.*
import org.junit.Before
import org.junit.Test

class VideoPlayerFragmentTest {

    private lateinit var fragment: VideoPlayerFragment
    private val mockExoPlayer: ExoPlayer = mockk(relaxed = true)

    @Before
    fun setUp() {
        fragment = spyk(VideoPlayerFragment())
        every { fragment.requireContext() } returns mockk(relaxed = true)
    }

    @Test
    fun `initializePlayer does not crash on exception`() {
        every { ExoPlayer.Builder(any()) } throws RuntimeException("Ошибка инициализации плеера")

        try {
            fragment.initializePlayer("https://example.com/video.mp4", 0L)
        } catch (e: Exception) {
            assert(false) { "Метод не должен падать с ошибкой!" }
        }
    }

    @Test
    fun `initializePlayer initializes ExoPlayer correctly`() {
        every { ExoPlayer.Builder(any()).build() } returns mockExoPlayer

        fragment.initializePlayer("https://example.com/video.mp4", 1000L)

        verify { mockExoPlayer.setMediaItem(any()) }
        verify { mockExoPlayer.prepare() }
        verify { mockExoPlayer.seekTo(1000L) }
    }
}
