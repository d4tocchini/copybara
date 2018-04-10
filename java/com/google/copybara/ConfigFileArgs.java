/*
 * Copyright (C) 2018 Google Inc.
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

package com.google.copybara;

import com.google.common.base.Preconditions;
import javax.annotation.Nullable;

/**
 * Arguments for a command that expects the CLI arguments be like: <code>config_file [workflow
 * [source_ref]]</code>
 */
public final class ConfigFileArgs {

  private final String configPath;
  @Nullable
  private final String workflowName;
  @Nullable
  private final String sourceRef;

  ConfigFileArgs(String configPath, @Nullable String workflowName, @Nullable String sourceRef) {
    this.configPath = Preconditions.checkNotNull(configPath);
    this.workflowName = workflowName;
    this.sourceRef = sourceRef;
  }

  public String getConfigPath() {
    return configPath;
  }

  @Nullable
  public String getWorkflowName() {
    return workflowName;
  }

  @Nullable
  public String getSourceRef() {
    return sourceRef;
  }

}
