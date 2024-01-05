/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.bigtable.beam;

import static org.junit.Assert.assertEquals;

import com.google.cloud.bigtable.hbase.BigtableOptionsFactory;
import org.apache.beam.sdk.options.ValueProvider.StaticValueProvider;
import org.apache.hadoop.conf.Configuration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Tests for {@link com.google.cloud.bigtable.beam.CloudBigtableTableConfiguration}. */
@RunWith(JUnit4.class)
public class CloudBigtableTableConfigurationTest {

  private static final String PROJECT = "my_project";
  private static final String INSTANCE = "instance";
  private static final String TABLE = "some-zone-1a";

  protected static CloudBigtableTableConfiguration buildConfiguration() {
    return configure(new CloudBigtableTableConfiguration.Builder().withTableId(TABLE)).build();
  }

  protected static <ConfigurationBuilder extends CloudBigtableConfiguration.Builder>
      ConfigurationBuilder configure(ConfigurationBuilder builder) {
    builder.withProjectId(PROJECT).withInstanceId(INSTANCE);
    return builder;
  }

  @Test
  public void testHBaseConfig() {
    CloudBigtableTableConfiguration underTest = buildConfiguration();

    Configuration config = underTest.toHBaseConfig();

    assertEquals(PROJECT, config.get(BigtableOptionsFactory.PROJECT_ID_KEY));
    assertEquals(INSTANCE, config.get(BigtableOptionsFactory.INSTANCE_ID_KEY));
    assertEquals(TABLE, underTest.getTableId());
  }

  @Test
  public void testEquals() {
    CloudBigtableTableConfiguration underTest1 = buildConfiguration();
    CloudBigtableTableConfiguration underTest2 = buildConfiguration();
    CloudBigtableTableConfiguration underTest3 =
        underTest1.toBuilder().withConfiguration("somekey", "somevalue").build();
    CloudBigtableTableConfiguration underTest4 =
        underTest1.toBuilder().withProjectId("other_project").build();
    CloudBigtableConfiguration underTest5 =
        configure(new CloudBigtableConfiguration.Builder()).build();

    // Test CloudBigtableTableConfiguration that should be equal.
    Assert.assertEquals(underTest1, underTest2);

    // Test CloudBigtableTableConfiguration with different additionalConfigurations are not equal.
    Assert.assertNotEquals(underTest1, underTest3);

    // Test CloudBigtableTableConfiguration with different ProjectId are not equal.
    Assert.assertNotEquals(underTest1, underTest4);

    // Test CloudBigtableTableConfiguration is not equal to a similar CloudBigtableConfiguration.
    Assert.assertNotEquals(underTest1, underTest5);

    // Test CloudBigtableConfiguration is not equal to a similar CloudBigtableTableConfiguration.
    // (reversed order from the previous test)
    Assert.assertNotEquals(underTest5, underTest1);
  }

  @Test
  public void testToBuilder() {
    CloudBigtableTableConfiguration underTest =
        buildConfiguration().toBuilder().withConfiguration("somekey", "somevalue").build();
    CloudBigtableTableConfiguration copy = underTest.toBuilder().build();
    Assert.assertNotSame(underTest, copy);
    Assert.assertEquals(underTest, copy);
  }

  /**
   * This ensures that the config built from regular parameters are the same as the config built
   * from runtime parameters, so that we don't have to use runtime parameters to repeat the same
   * tests.
   */
  @Test
  public void testRegularAndRuntimeParametersAreEqual() {
    CloudBigtableTableConfiguration withRegularParameters =
        buildConfiguration().toBuilder().withConfiguration("somekey", "somevalue").build();
    CloudBigtableTableConfiguration withRuntimeParameters =
        new CloudBigtableTableConfiguration.Builder()
            .withTableId(StaticValueProvider.of(TABLE))
            .withProjectId(StaticValueProvider.of(PROJECT))
            .withInstanceId(StaticValueProvider.of(INSTANCE))
            .withConfiguration("somekey", StaticValueProvider.of("somevalue"))
            .build();
    Assert.assertNotSame(withRegularParameters, withRuntimeParameters);
    Assert.assertEquals(withRegularParameters, withRuntimeParameters);
  }
}
