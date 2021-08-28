package io.github.opgg.music_ward_server.service.comment;

import io.github.opgg.music_ward_server.dto.comment.request.CommentRequest;
import io.github.opgg.music_ward_server.dto.comment.request.EditCommentRequest;
import io.github.opgg.music_ward_server.dto.comment.request.RemoveCommentRequest;
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
        User user = getCurrentUser();

        Playlist playlist = playlistRepository
                .findById(request.getPlaylistId())
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
        User user = getCurrentUser();

        Comment comment = getComment(request.getCommentId());

        if(!user.getId().equals(comment.getUser().getId()))
            throw new NotYourCommentException();

        comment.editContent(request.getComment());

        commentRepository.save(comment);
    }

    @Override
    public void removeComment(RemoveCommentRequest request) {
        User user = getCurrentUser();

        Comment comment = getComment(request.getCommentId());

        if(!user.getId().equals(comment.getUser().getId()))
            throw new NotYourCommentException();

        commentRepository.delete(comment);
    }

    private User getCurrentUser() {
        return userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(UserNotFoundException::new);
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }

}
