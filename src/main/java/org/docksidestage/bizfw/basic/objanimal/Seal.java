/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.objanimal;

import org.docksidestage.bizfw.basic.objanimal.runner.FastRunner;
import org.docksidestage.bizfw.basic.objanimal.swimmer.FastSwimmer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO ichikawa javadoc修正 by jflute (2026/04/30)
// #1on1: コードコピペの話、Copyrightとauthor, 会社の権利を守る手段の一つ。 (2026/04/30)
/**
 * The object for cat(猫).
 * @author jflute
 */
public class Seal extends Animal implements FastSwimmer {

    private static final Logger logger = LoggerFactory.getLogger(Seal.class);

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Seal() {
    }

    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    @Override
    protected String getBarkWord() {
        return "Arf, arf!";
    }

    // ===================================================================================
    //                                                                            Swimmer
    //                                                                              ======
    @Override
    public void swim() {
        logger.debug("...Swimming now"); // dummy implementation
        downHitPoint();
    }
}
