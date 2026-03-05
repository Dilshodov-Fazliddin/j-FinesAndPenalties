package com.uzum.jfinesandpenalties.repository;

import com.uzum.jfinesandpenalties.constant.enums.FineType;
import com.uzum.jfinesandpenalties.constant.enums.FinesStatus;
import com.uzum.jfinesandpenalties.constant.enums.Rank;
import com.uzum.jfinesandpenalties.entity.FineEntity;
import com.uzum.jfinesandpenalties.entity.OfficerEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
class FineRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    @Autowired
    private FineRepository fineRepository;
    @Autowired
    private OfficerRepository officerRepository;

    private final FineEntity fine = FineEntity.builder()
            .fineType(FineType.SPEEDING)
            .fineStatus(FinesStatus.CREATED)
            .articleId(5L)
            .description("Speed limit 60")
            .offenderName("Kristina")
            .offenderPersonalIdentificationNumber("00000")
            .penaltyAmount(500D)
            .passportNumber("0000000")
            .officer(new OfficerEntity())
            .build();


    private final OfficerEntity officer=OfficerEntity.builder()
            .age(40)
            .rank(Rank.CHIEF_OFFICER)
            .budgeNumber("UZB44")
            .firstName("Timur")
            .lastName("Akbarov")
            .password("$2a$10$XWU7//A4lO77H/oPU8RGTevVc1LfEyaBZv3LvktrpdN2M4cnnD5G.")
            .build();

    @Test
    void shouldReturnEmptyWhenFineNotFound() {
        Optional<FineEntity> foundFine = fineRepository.findById(23L);

        assertThat(foundFine).isEmpty();
    }

    @Test
    void shouldSaveFine() {


        officerRepository.save(officer);

        fine.setOfficer(officer);

        var save = fineRepository.save(fine);

        assertThat(save.getFineType()).isNotNull();
    }
}