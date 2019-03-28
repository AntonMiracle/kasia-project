package com.kasia.model;

import com.kasia.ModelTestData;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import javax.persistence.Id;
import java.math.BigDecimal;

public class BudgetTest {
    @Test
    public void equalsAndHashCode() throws Exception {
        Balance balance1 = ModelTestData.balance();
        Balance balance2 = ModelTestData.balance();
        balance2.setAmount(balance1.getAmount().add(BigDecimal.TEN));

        EqualsVerifier.forClass(Budget.class)
                .usingGetClass()
                .withPrefabValues(Balance.class, balance1, balance2)
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }

}