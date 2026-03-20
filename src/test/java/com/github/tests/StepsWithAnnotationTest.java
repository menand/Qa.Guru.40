package com.github.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("REGRESS")
@Tag("UI")
@Tag("GITHUB")
class StepsWithAnnotationTest extends GitHubTestBase {

    @Test
    @DisplayName("Тест в шагами в отдельном классе")
    void testAnnotatedStep() {
        StepsWithAnnotation steps = new StepsWithAnnotation();

        steps.openGitHub();
        steps.searchForRepository(PROJECTNAME);
        steps.openClosedIssues();
        steps.shouldHaveClosedIssue();
    }
}
