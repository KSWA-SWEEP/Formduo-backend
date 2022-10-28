package com.sweep.formduo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AuthServiceTest.class, MemberServiceTest.class, QboxServiceTest.class, SurveyServiceTest.class, SurveyRespServiceTest.class})
public class IntegrationTest {
}
