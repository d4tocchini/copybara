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

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.copybara.config.ConfigValidator;
import com.google.copybara.config.Migration;
import com.google.copybara.exception.RepoException;
import com.google.copybara.exception.ValidationException;
import com.google.copybara.util.ExitCode;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Validates that the configuration is correct.
 */
public class ValidateCmd implements CopybaraCmd {

  private final ConfigValidator configValidator;
  private final Consumer<Migration> migrationRanConsumer;
  private final ConfigLoaderProvider configLoaderProvider;

  ValidateCmd(ConfigValidator configValidator, Consumer<Migration> migrationRanConsumer,
      ConfigLoaderProvider configLoaderProvider) {
    this.configValidator = checkNotNull(configValidator);
    this.migrationRanConsumer = checkNotNull(migrationRanConsumer);
    this.configLoaderProvider = checkNotNull(configLoaderProvider);
  }

  @Override
  public ExitCode run(CommandEnv commandEnv)
      throws ValidationException, IOException, RepoException {
    ConfigFileArgs configFileArgs = commandEnv.parseConfigFileArgs(this,
        /*useSourceRef*/false);
    Copybara copybara = new Copybara(configValidator, migrationRanConsumer);

    return copybara.validate(
        commandEnv.getOptions(),
        configLoaderProvider.newLoader(
            configFileArgs.getConfigPath(),
            configFileArgs.getSourceRef()),
        configFileArgs.getWorkflowName())
        ? ExitCode.SUCCESS : ExitCode.CONFIGURATION_ERROR;
  }

  @Override
  public String name() {
    return "validate";
  }
}
