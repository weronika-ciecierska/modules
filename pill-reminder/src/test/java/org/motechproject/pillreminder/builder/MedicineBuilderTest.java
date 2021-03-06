package org.motechproject.pillreminder.builder;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.motechproject.commons.date.util.DateUtil;
import org.motechproject.pillreminder.contract.MedicineRequest;
import org.motechproject.pillreminder.domain.Medicine;

import static org.junit.Assert.assertEquals;

public class MedicineBuilderTest {
    private MedicineBuilder builder = new MedicineBuilder();

    @Test
    public void shouldBuildAMedicineFromRequest() {
        String medicineName = "paracetamol";
        LocalDate startDate = DateUtil.today();
        LocalDate endDate = startDate.plusDays(2);
        MedicineRequest medicineRequest = new MedicineRequest(medicineName, startDate, endDate);

        Medicine medicine = builder.createFrom(medicineRequest);

        assertEquals(startDate, medicine.getStartDate());
        assertEquals(endDate, medicine.getEndDate());
        assertEquals(medicineName, medicine.getName());
    }
}
