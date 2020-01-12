package eu.brokoko.thymely.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import eu.brokoko.thymely.web.rest.TestUtil;

public class WorkperiodTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Workperiod.class);
        Workperiod workperiod1 = new Workperiod();
        workperiod1.setId(1L);
        Workperiod workperiod2 = new Workperiod();
        workperiod2.setId(workperiod1.getId());
        assertThat(workperiod1).isEqualTo(workperiod2);
        workperiod2.setId(2L);
        assertThat(workperiod1).isNotEqualTo(workperiod2);
        workperiod1.setId(null);
        assertThat(workperiod1).isNotEqualTo(workperiod2);
    }
}
