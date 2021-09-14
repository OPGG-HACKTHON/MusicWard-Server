package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.BaseIntegrationTest;
import io.github.opgg.music_ward_server.dto.comment.request.CommentRequest;
import io.github.opgg.music_ward_server.dto.comment.request.EditCommentRequest;
import io.github.opgg.music_ward_server.dto.comment.request.RemoveCommentRequest;
import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.champion.ChampionRepository;
import io.github.opgg.music_ward_server.entity.comment.Comment;
import io.github.opgg.music_ward_server.entity.comment.CommentRepository;
import io.github.opgg.music_ward_server.entity.playlist.Image;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import io.github.opgg.music_ward_server.entity.playlist.PlaylistRepository;
import io.github.opgg.music_ward_server.entity.playlist.Provider;
import io.github.opgg.music_ward_server.entity.user.User;
import io.github.opgg.music_ward_server.entity.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentControllerTest extends BaseIntegrationTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ChampionRepository championRepository;

	@Autowired
	private PlaylistRepository playlistRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Test
	@DisplayName("POST playlists/comment")
	@WithUserDetails(value = "1")
	void postComment() throws Exception {
		//given
		User user = userRepository.findById(1L).get();

		Champion champion = championRepository.save(generateChampion());

		Playlist playlist = playlistRepository.save(generatePlaylist(user, champion));

		//when
		CommentRequest commentRequest = CommentRequest.builder()
				.playlistId(playlist.getId())
				.comment("Test comment.")
				.build();

		//then
		mockMvc.perform(post("/playlists/comment")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(commentRequest)))
				.andDo(print())
				.andExpect(status().isCreated());

	}

	@Test
	@DisplayName("PUT playlists/comment")
	@WithUserDetails(value = "1")
	void editComment() throws Exception {
		//given
		User user = userRepository.findById(1L).get();

		Champion champion = championRepository.save(generateChampion());

		Playlist playlist = playlistRepository.save(generatePlaylist(user, champion));

		Comment comment = commentRepository.save(generateComment(user, playlist));

		//when
		System.out.println("playlistId = " + comment.getPlaylist().getId());
		EditCommentRequest editCommentRequest = EditCommentRequest.builder()
				.comment("Edit comment.")
				.commentId(comment.getId())
				.build();

		//then
		mockMvc.perform(patch("/playlists/comment")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(editCommentRequest)))
				.andDo(print())
				.andExpect(status().isNoContent());

	}

	@Test
	@DisplayName("DELETE playlists/comment")
	@WithUserDetails(value = "1")
	void removeComment() throws Exception {

		//given
		User user = userRepository.findById(1L).get();

		Champion champion = championRepository.save(generateChampion());

		Playlist playlist = playlistRepository.save(generatePlaylist(user, champion));

		Comment comment = commentRepository.save(generateComment(user, playlist));

		//when
		System.out.println("playlistId = " + comment.getPlaylist().getId());
		RemoveCommentRequest removeCommentRequest = RemoveCommentRequest.builder()
				.commentId(comment.getId())
				.build();

		//then
		mockMvc.perform(delete("/playlists/comment")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(removeCommentRequest)))
				.andDo(print())
				.andExpect(status().isNoContent());

	}

	static Champion generateChampion() {
		return Champion.builder()
				.name("가렌")
				.title("데마시아의 힘")
				.englishName("Garen")
				.story("가렌은 불굴의 선봉대를 이끄는 고결하고 자긍심 강한 군인이다.")
				.position("전사")
				.profileImageUrl("/images/profile/garen.jpg")
				.imageUrl("/images/garen.jpg")
				.build();
	}

	static Playlist generatePlaylist(User user, Champion champion) {
		return Playlist.builder()
				.originalId("1234")
				.provider(Provider.YOUTUBE)
				.title("테스트 플레이 리스트")
				.description("테스트 플레이 리스트 설명")
				.image(new Image("url", "640", "640"))
				.externalUrl("외부 url")
				.user(user)
				.champion(champion)
				.build();
	}

	static Comment generateComment(User user, Playlist playlist) {
		return Comment.builder()
				.playlist(playlist)
				.user(user)
				.content("test")
				.build();
	}

}