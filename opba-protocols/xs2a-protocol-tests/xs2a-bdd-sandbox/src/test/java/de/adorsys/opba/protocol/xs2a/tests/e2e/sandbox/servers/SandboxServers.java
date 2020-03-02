package de.adorsys.opba.protocol.xs2a.tests.e2e.sandbox.servers;

import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import de.adorsys.opba.db.repository.jpa.BankProfileJpaRepository;
import de.adorsys.opba.protocol.xs2a.tests.e2e.stages.CommonGivenStages;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.config.RedirectConfig.redirectConfig;

@Slf4j
@JGivenStage
public class SandboxServers<SELF extends SandboxServers<SELF>> extends CommonGivenStages<SELF> {

    private static final String ASPSP_PROFILE_BASE_URI = "http://localhost:20010";

    @Autowired
    private BankProfileJpaRepository profiles;

    @BeforeStage
    void prepareRestAssured() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.config = RestAssured.config().redirect(redirectConfig().followRedirects(false));
    }

    public SELF enabled_embedded_sandbox_mode() {
        RestAssured
                .given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body("[\"EMBEDDED\",\"REDIRECT\",\"DECOUPLED\"]")
                .when()
                    .put(ASPSP_PROFILE_BASE_URI + "/api/v1/aspsp-profile/for-debug/sca-approaches")
                .then()
                    .statusCode(HttpStatus.OK.value());

        return self();
    }

    public SELF enabled_redirect_sandbox_mode() {
        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("[\"REDIRECT\",\"EMBEDDED\",\"DECOUPLED\"]")
                .when()
                .put(ASPSP_PROFILE_BASE_URI + "/api/v1/aspsp-profile/for-debug/sca-approaches")
                .then()
                .statusCode(HttpStatus.OK.value());

        return self();
    }
}
