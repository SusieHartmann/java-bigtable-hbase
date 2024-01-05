/*
 * Copyright 2020 Google LLC
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
package com.google.cloud.bigtable.hbase.wrappers;

import com.google.api.core.ApiFuture;
import com.google.api.core.InternalApi;
import com.google.cloud.bigtable.data.v2.models.RowMutationEntry;
import java.io.IOException;

/**
 * Interface to support multiple mutation requests into a single RPC request.
 *
 * <p>For internal use only - public for technical reasons.
 */
@InternalApi("For internal usage only")
public interface BulkMutationWrapper extends AutoCloseable {

  /** Adds a {@link RowMutationEntry} to the underlying batch for mutations. */
  ApiFuture<Void> add(RowMutationEntry rowMutation);

  /** Sends any outstanding entry, present in the current batch but doesn't wait for response. */
  void sendUnsent();

  /** Sends any outstanding RowMutationEntry and blocks until all requests are complete. */
  void flush() throws InterruptedException;

  /** Closes this bulk Mutation and prevents from mutating any more elements. */
  @Override
  void close() throws IOException;
}
