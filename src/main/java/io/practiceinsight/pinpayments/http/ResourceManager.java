/*
 * Copyright (c) 2016 Practice Insight
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.practiceinsight.pinpayments.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * Resource manager to isolate the auto-closing logic in a separate class.
 *
 * @author delight.wjk@gmail.com
 */
public class ResourceManager {

  private static final Logger log = LoggerFactory.getLogger(ResourceManager.class); //NOPMD

  private Closeable resource;

  /**
   * Default non-arg constructor.
   */
  public ResourceManager() {
    // Do nothing here
  }

  /**
   * Constructs a new {@link ResourceManager} instance with resource specified.
   *
   * @param theResource The resource specified.
   */
  private ResourceManager(final Closeable theResource) {
    this.resource = theResource;
  }

  /**
   * Creates a new instance of {@link ResourceManager} with the resource specified.
   *
   * @param theResource The closeable resource specified.
   * @return The new instance of {@link ResourceManager} created.
   */
  public final ResourceManager with(final Closeable theResource) {
    return new ResourceManager(theResource);
  }

  /**
   * Runs the resource handler.
   *
   * @param handler The resource handler specified.
   * @param <T> The result type specified.
   * @return The result of resource processing.
   * @throws IOException If IO errors occur.
   */
  public final <T> T run(final ResourceHandler<T> handler) throws IOException {
    try {
      return handler.handle(resource);
    } finally {
      closeResource();
    }
  }

  /**
   * Runs the resource handler.
   *
   * @param handler The resource handler specified.
   * @throws IOException If IO errors occur.
   */
  public final void run(final VoidResourceHandler handler) throws IOException {
    try {
      handler.handle(resource);
    } finally {
      closeResource();
    }
  }

  /**
   * Closes the resource if not null.
   */
  private void closeResource() {
    if (resource != null) {
      try {
        resource.close();
      } catch (final IOException ex) {
        log.error(ex.getMessage());
      }
    }
  }
}
