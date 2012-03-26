package org.motechproject.appointments.api.dao;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.appointments.api.contract.VisitResponse;
import org.motechproject.appointments.api.model.AppointmentCalendar;
import org.motechproject.appointments.api.model.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static ch.lambdaj.Lambda.extract;
import static ch.lambdaj.Lambda.on;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.motechproject.util.DateUtil.newDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
public class AllAppointmentCalendarsIT extends AppointmentsBaseIntegrationTest {

    @Autowired
    private AllAppointmentCalendars allAppointmentCalendars;

    @Test
    public void testSaveAppointmentCalender() {
        AppointmentCalendar appointmentCalendar = new AppointmentCalendar().externalId("externalId");

        allAppointmentCalendars.saveAppointmentCalendar(appointmentCalendar);

        assertNotNull(appointmentCalendar.getId());

        markForDeletion(appointmentCalendar);
    }

    @Test
    public void testFindByExternalId() {
        Visit visit1 = new Visit().name("Visit 1");
        Visit visit2 = new Visit().name("Visit 2");

        AppointmentCalendar appointmentCalendar = new AppointmentCalendar().externalId("foo").addVisit(visit1).addVisit(visit2);
        allAppointmentCalendars.saveAppointmentCalendar(appointmentCalendar);

        AppointmentCalendar savedCalender = allAppointmentCalendars.findByExternalId("foo");
        assertNotNull(savedCalender);
        assertEquals(appointmentCalendar.getId(), savedCalender.getId());

        markForDeletion(appointmentCalendar);
    }

    @Test
    public void shouldFetchVisitsWithDueInRange(){
        Visit visit1 = new Visit().name("visit1").addAppointment(newDateTime(2011, 6, 5, 0, 0, 0), null).visitDate(newDateTime(2011, 6, 5, 0, 0, 0));
        Visit visit2 = new Visit().name("visit2").addAppointment(newDateTime(2011, 7, 1, 0, 0, 0), null);
        Visit visit3 = new Visit().name("visit3").addAppointment(newDateTime(2011, 8, 3, 0, 0, 0), null).visitDate(newDateTime(2011, 8, 3, 0, 0, 0));
        Visit visit4 = new Visit().name("visit4").addAppointment(newDateTime(2011, 10, 1, 0, 0, 0), null);
        Visit visit5 = new Visit().name("visit5").addAppointment(newDateTime(2011, 10, 2, 0, 0, 0), null);

        allAppointmentCalendars.add(new AppointmentCalendar().externalId("foo1").addVisit(visit1).addVisit(visit2));
        allAppointmentCalendars.add(new AppointmentCalendar().externalId("foo2").addVisit(visit3).addVisit(visit4).addVisit(visit5));
        DateTime start = newDateTime(2011, 7, 1, 0, 0, 0);
        DateTime end = newDateTime(2011, 10, 1, 0, 0, 0);
        List<VisitResponse> visitsWithDueInRange = allAppointmentCalendars.findVisitsWithDueInRange(start, end);

        assertEquals(asList(new String[]{ "visit2","visit3", "visit4" }), extract(visitsWithDueInRange, on(VisitResponse.class).getName()));
        assertEquals(asList(new String[]{ "foo1","foo2", "foo2" }), extract(visitsWithDueInRange, on(VisitResponse.class).getExternalId()));
    }


    @Test
    public void shouldFetchMissedVisits(){
        Visit visit1 = new Visit().name("visit1").addAppointment(newDateTime(2011, 6, 5, 0, 0, 0), null).visitDate(newDateTime(2011, 6, 5, 0, 0, 0));
        Visit visit2 = new Visit().name("visit2").addAppointment(newDateTime(2011, 7, 1, 0, 0, 0), null);
        Visit visit3 = new Visit().name("visit3").addAppointment(newDateTime(2011, 8, 3, 0, 0, 0), null).visitDate(newDateTime(2011, 8, 3, 0, 0, 0));
        Visit visit4 = new Visit().name("visit4").addAppointment(newDateTime(2011, 10, 1, 0, 0, 0), null);
        Visit visit5 = new Visit().name("visit5").addAppointment(newDateTime(2011, 10, 2, 0, 0, 0), null);

        allAppointmentCalendars.add(new AppointmentCalendar().externalId("foo1").addVisit(visit1).addVisit(visit2));
        allAppointmentCalendars.add(new AppointmentCalendar().externalId("foo2").addVisit(visit3).addVisit(visit4).addVisit(visit5));
        List<VisitResponse> visitsWithDueInRange = allAppointmentCalendars.findMissedVisits();

        assertEquals(asList(new String[]{ "visit2","visit4", "visit5" }), extract(visitsWithDueInRange, on(VisitResponse.class).getName()));
        assertEquals(asList(new String[]{ "foo1","foo2", "foo2" }), extract(visitsWithDueInRange, on(VisitResponse.class).getExternalId()));
    }

    @Test
    public void shouldFetchVisitsByExternalId(){
        Visit visit1 = new Visit().name("visit1").addAppointment(newDateTime(2011, 6, 5, 0, 0, 0), null).visitDate(newDateTime(2011, 6, 5, 0, 0, 0));
        Visit visit2 = new Visit().name("visit2").addAppointment(newDateTime(2011, 7, 1, 0, 0, 0), null);
        Visit visit3 = new Visit().name("visit3").addAppointment(newDateTime(2011, 8, 3, 0, 0, 0), null).visitDate(newDateTime(2011, 8, 3, 0, 0, 0));
        Visit visit4 = new Visit().name("visit4").addAppointment(newDateTime(2011, 10, 1, 0, 0, 0), null);
        Visit visit5 = new Visit().name("visit5").addAppointment(newDateTime(2011, 10, 2, 0, 0, 0), null);

        allAppointmentCalendars.add(new AppointmentCalendar().externalId("foo1").addVisit(visit1).addVisit(visit2));
        allAppointmentCalendars.add(new AppointmentCalendar().externalId("foo2").addVisit(visit3).addVisit(visit4).addVisit(visit5));
        List<VisitResponse> visitsWithDueInRange = allAppointmentCalendars.findVisitsByExternalId("foo2");

        assertEquals(asList(new String[]{ "visit3","visit4", "visit5" }), extract(visitsWithDueInRange, on(VisitResponse.class).getName()));
        assertEquals(asList(new String[]{ "foo2","foo2", "foo2" }), extract(visitsWithDueInRange, on(VisitResponse.class).getExternalId()));
    }

    @After
    public void tearDown()
    {
        allAppointmentCalendars.removeAll();
    }

}
