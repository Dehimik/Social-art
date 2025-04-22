package com.dehimik.art.dto.user;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class UpdateProfileDto {
    @Size(max = 500, message = "Bio cannot exceed 500 characters")
    private String bio;

    @URL(message = "Profile picture URL must be valid")
    private String profilePictureUrl;
}
