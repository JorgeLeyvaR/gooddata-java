/*
 * Copyright (C) 2004-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.dataload.processes;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.gooddata.JsonMatchers.serializesToJson;
import static com.gooddata.util.ResourceUtils.readObjectFromResource;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class ScheduleExecutionTest {

    @Test
    public void testEmptyScheduleExecutionSerialization() throws Exception {
        final ScheduleExecution scheduleExecution = new ScheduleExecution();

        assertThat(scheduleExecution, serializesToJson("/dataload/processes/emptyScheduleExecution.json"));
    }

    @Test
    public void testDeserialization() throws Exception {
        final ScheduleExecution scheduleExecution = readObjectFromResource("/dataload/processes/scheduleExecution.json", ScheduleExecution.class);

        assertThat(scheduleExecution, notNullValue());
        assertThat(scheduleExecution.getLinks(), is(equalTo(new HashMap<String, String>() {{
            put("self", "/gdc/projects/PROJECT_ID/schedules/SCHEDULE_ID/executions/EXECUTION_ID");
        }})));
        assertThat(scheduleExecution.getStatus(), is("OK"));
        assertThat(scheduleExecution.getTrigger(), is("MANUAL"));
        assertThat(scheduleExecution.getProcessLastDeployedBy(), is("bear@gooddata.com"));
        assertThat(scheduleExecution.getCreated(), is(new DateTime(2017, 5, 9, 21, 54, 50, 924, DateTimeZone.UTC)));
    }
}
