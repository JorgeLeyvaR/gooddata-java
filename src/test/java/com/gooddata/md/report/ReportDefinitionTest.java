/*
 * Copyright (C) 2004-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.md.report;

import org.testng.annotations.Test;

import java.util.Collections;

import static com.gooddata.JsonMatchers.serializesToJson;
import static com.gooddata.util.ResourceUtils.readObjectFromResource;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.MatchesPattern.matchesPattern;

public class ReportDefinitionTest {

    public static final String FORMAT = "oneNumber";

    @Test
    public void testDeserialization() throws Exception {
        final ReportDefinition def = readObjectFromResource("/md/report/oneNumberReportDefinition.json", ReportDefinition.class);
        assertThat(def, is(notNullValue()));
        assertThat(def.getFormat(), is(FORMAT));
        assertThat(def.getGrid(), is(notNullValue()));
        assertThat(def.getExplainUri(), is("/gdc/md/PROJECT_ID/obj/2274/explain2"));
    }

    @Test
    public void testSerialization() throws Exception {
        final ReportDefinition def = new ReportDefinition("Untitled",
                new OneNumberReportDefinitionContent(
                        new Grid(Collections.emptyList(), Collections.emptyList(),
                                Collections.emptyList()), "desc", asList(new Filter("(SELECT [/gdc/md/projectId/obj/123]) >= 2")))
        );
        assertThat(def, serializesToJson("/md/report/oneNumberReportDefinition-input.json"));
    }

    @Test
    public void testToStringFormat() throws Exception {
        final ReportDefinition def = readObjectFromResource("/md/report/oneNumberReportDefinition.json", ReportDefinition.class);

        assertThat(def.toString(), matchesPattern(ReportDefinition.class.getSimpleName() + "\\[.*\\]"));
    }

}