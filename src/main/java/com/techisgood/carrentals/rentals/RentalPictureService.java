package com.techisgood.carrentals.rentals;

import com.techisgood.carrentals.global.StorageService;
import com.techisgood.carrentals.model.Rental;
import com.techisgood.carrentals.model.RentalPicture;
import com.techisgood.carrentals.model.DbUser;
import com.techisgood.carrentals.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalPictureService {

    private final RentalRepository rentalRepository;
    private final RentalPictureRepository rentalPictureRepository;
    private final UserRepository userRepository; // You might need to inject the UserRepository to fetch the user who took the picture.
    private final StorageService storageService;

    @Transactional
    public RentalPictureDto createRentalPicture(CreateRentalPictureDto dto, MultipartFile file) throws IllegalArgumentException {
        // Convert DTO to Entity
        RentalPicture rentalPicture = new RentalPicture();

        Optional<Rental> rental = rentalRepository.findById(dto.getRentalId());
        rental.ifPresentOrElse(rentalPicture::setRental, () -> { throw new IllegalArgumentException("Invalid rental ID"); });

        rentalPicture.setAngle(dto.getAngle());
        rentalPicture.setIsInitialPicture(dto.getIsInitialPicture());

        Optional<DbUser> takenBy = userRepository.findById(dto.getTakenById()); // Assuming you have UserRepository injected.
        takenBy.ifPresentOrElse(rentalPicture::setTakenBy, () -> { throw new IllegalArgumentException("Invalid user ID"); });

        rentalPicture.setTakenAt(dto.getTakenAt());

        // Save the entity
        RentalPicture savedRentalPicture = rentalPictureRepository.save(rentalPicture);
        String fileName = file.getOriginalFilename();

        String key = storageService.upload(savedRentalPicture.getId() + "/" + fileName, file);
        rentalPicture.setS3Key(key);
        rentalPictureRepository.save(rentalPicture);
        // Convert entity back to DTO and return
        return RentalPictureDto.from(savedRentalPicture);
    }
}
