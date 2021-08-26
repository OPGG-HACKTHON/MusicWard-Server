package io.github.opgg.music_ward_server.service.comment;

import io.github.opgg.music_ward_server.dto.comment.request.CommentRequest;
import io.github.opgg.music_ward_server.dto.comment.request.EditCommentRequest;
import io.github.opgg.music_ward_server.entity.comment.Comment;
import io.github.opgg.music_ward_server.entity.comment.CommentRepository;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import io.github.opgg.music_ward_server.entity.playlist.PlaylistRepository;
import io.github.opgg.music_ward_server.entity.user.User;
import io.github.opgg.music_ward_server.entity.user.UserRepository;
import io.github.opgg.music_ward_server.exception.CommentNotFoundException;
import io.github.opgg.music_ward_server.exception.NotYourCommentException;
import io.github.opgg.music_ward_server.exception.PlaylistNotFoundException;
import io.github.opgg.music_ward_server.exception.UserNotFoundException;
import io.github.opgg.music_ward_server.utils.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;


    @Override
    public void postComment(CommentRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Playlist playlist = playlistRepository
                .findById(Long.valueOf(request.getPlaylistId()))
                .orElseThrow(PlaylistNotFoundException::new);

        commentRepository.save(
                Comment.builder()
                .user(user)
                .playlist(playlist)
                .content(request.getComment())
                .build()
        );

    }

    @Override
    public void editComment(EditCommentRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Comment comment = commentRepository.findById(Long.valueOf(request.getCommentId()))
                .orElseThrow(CommentNotFoundException::new);

        if(!user.getId().equals(comment.getUser().getId()))
            throw new NotYourCommentException();

        comment.editContent(request.getComment());

        commentRepository.save(comment);
    }
}
