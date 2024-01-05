/*
 * Copyright 2022 Google LLC
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
package com.google.cloud.bigtable.mirroring.hbase2_x;

/**
 * This class is here to force generation of source javadoc jars so that the maven release process
 * doesn't complain. The shading plugin generated a shaded jar of bigtable-hbase, but it doesn't
 * generate javadoc or source files; this class is here as a hack and better methods should be
 * employed.
 */
public final class MavenPlaceholderHadoop2x {

  private MavenPlaceholderHadoop2x() {}
}
