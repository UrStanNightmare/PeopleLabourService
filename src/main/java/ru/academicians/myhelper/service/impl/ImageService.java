package ru.academicians.myhelper.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.academicians.myhelper.exception.ItemNotFoundException;
import ru.academicians.myhelper.service.DefaultImageService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static ru.academicians.myhelper.defaults.DefaultKeys.AVATAR_NOT_FOUND;
import static ru.academicians.myhelper.defaults.DefaultRequirements.AVATAR_FOLDER_PATH;

@Service
public class ImageService implements DefaultImageService {

    private final ResourceLoader resourceLoader;

    @Autowired
    public ImageService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;

        File avatarsFolder = Paths.get(AVATAR_FOLDER_PATH).toFile();
        if (!avatarsFolder.exists()){
            avatarsFolder.mkdir();
        }
    }

    @Override
    public byte[] getUserAvatarById(long idFromToken) {
        Resource resource = resourceLoader.getResource("file:" + AVATAR_FOLDER_PATH + this.generateFileName(idFromToken));
        if (!resource.exists()){
            throw new ItemNotFoundException(AVATAR_NOT_FOUND);
        }

        try(
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream resourceAsStream = resource.getInputStream()
        ) {
            if (resourceAsStream == null){
                throw new IOException("Avatar not found!");
            }

            BufferedImage image = ImageIO.read(resourceAsStream);
            if (image == null){
                throw new IOException("Avatar not found!");
            }

            ImageIO.write(image, "png", baos);

            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUserAvatar(long userId, MultipartFile file) {
        Path path = Paths.get(AVATAR_FOLDER_PATH + this.generateFileName(userId));

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUserAvatar(long id){
        Path deletePath = Paths.get(AVATAR_FOLDER_PATH + this.generateFileName(id));

        if (deletePath.toFile().exists()){
            deletePath.toFile().delete();
        }
    }

    private String generateFileName(long id){
        return id + ".png";
    }
}
