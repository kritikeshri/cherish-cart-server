package com.etohfa.service;
import java.util.List;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
@Component
public class StorageServiceImpl implements StorageService {

	@Autowired
	private final Cloudinary cloudinary;

	public StorageServiceImpl(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
}

	@Override
	public List<String> loadAll() {
        return Collections.emptyList();
    }

	@Override
	public String store(MultipartFile file) {
		try {
			Map<?, ?> uploadResult = cloudinary.uploader().upload(
					file.getBytes(),
					ObjectUtils.emptyMap()
			);
			return uploadResult.get("secure_url").toString();
		} catch (IOException e) {
			throw new RuntimeException("Image upload failed", e);
		}
	}

	@Override
    public Resource load(String fileName) {
        return null;
    }

    @Override
public void delete(String imageUrl) {

    if (imageUrl == null || imageUrl.isBlank()) {
        return;
    }
    try {

        String publicId = imageUrl.substring(
                imageUrl.indexOf("/upload/") + 8
        );

        publicId = publicId.replaceFirst("^v\\d+/", "");
        publicId = publicId.substring(0, publicId.lastIndexOf('.'));

        Map<?, ?> resource = cloudinary.api().resource(publicId, ObjectUtils.emptyMap());

        if (resource != null) {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        }

    } catch (com.cloudinary.api.exceptions.NotFound notFound) {
        // Image doesn't exist anymore. Ignore.
    } catch (Exception e) {
        throw new RuntimeException("Failed to delete image from Cloudinary", e);
    }
}

}
