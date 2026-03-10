package com.github.tests;

import com.github.tests.helpers.AttachmentHelpers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StepsWithAnnotationTest extends GitHubTestBase {

    @Test
    @DisplayName("Тест в шагами в отдельном классе")
    void testAnnotatedStep() {
        StepsWithAnnotation steps = new StepsWithAnnotation();

        steps.openGitHub();
        steps.searchForRepository(PROJECTNAME);
        steps.openClosedIssues();
        steps.shouldHaveClosedIssue();
        AttachmentHelpers.attachAll();
    }
}
