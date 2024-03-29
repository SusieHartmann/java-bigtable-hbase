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
package com.google.cloud.bigtable.hbase.util;

import com.google.api.core.InternalApi;
import com.google.protobuf.ByteString;

/** For internal use only - public for technical reasons. */
@InternalApi("For internal usage only")
public class RowKeyWrapper implements Comparable<RowKeyWrapper> {

  private final ByteString key;

  public RowKeyWrapper(ByteString key) {
    this.key = key;
  }

  public ByteString getKey() {
    return key;
  }

  @Override
  public int compareTo(RowKeyWrapper o) {
    return compare(key, o.key);
  }

  private int compare(ByteString key1, ByteString key2) {
    if (key1 == null) {
      if (key2 == null) {
        return 0;
      } else {
        return 1;
      }
    } else if (key2 == null) {
      return -1;
    }

    if (key1 == key2) {
      return 0;
    }

    int size = Math.min(key1.size(), key2.size());

    for (int i = 0; i < size; i++) {
      // compare bytes as unsigned
      int byte1 = key1.byteAt(i) & 0xff;
      int byte2 = key2.byteAt(i) & 0xff;

      int comparison = Integer.compare(byte1, byte2);
      if (comparison != 0) {
        return comparison;
      }
    }
    return Integer.compare(key1.size(), key2.size());
  }

  @Override
  public String toString() {
    return key.toString();
  }
}
