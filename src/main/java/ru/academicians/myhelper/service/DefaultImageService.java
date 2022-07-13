package ru.academicians.myhelper.service;

import org.springframework.web.multipart.MultipartFile;

public interface DefaultImageService {
    byte[] getUserAvatarById(long idFromToken);

    void updateUserAvatar(long idFromToken, MultipartFile file);

    void deleteUserAvatar(long id);
}
