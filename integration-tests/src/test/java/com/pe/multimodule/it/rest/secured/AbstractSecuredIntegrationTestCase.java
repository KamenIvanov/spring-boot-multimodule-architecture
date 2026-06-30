package com.pe.multimodule.it.rest.secured;

import com.pe.multimodule.it.rest.AbstractDockerIntegrationTestCase;
import org.junit.jupiter.api.BeforeEach;

import java.util.UUID;

class AbstractSecuredIntegrationTestCase extends AbstractDockerIntegrationTestCase {

    protected static final UUID ACCOUNT_ID = UUID.randomUUID();
    protected static final UUID ACCOUNT_ID_2 = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        setRequesterId(ACCOUNT_ID);
    }
}
