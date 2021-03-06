/*
 * Copyright (c) 2008-2019, Hazelcast, Inc. All Rights Reserved.
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

package com.hazelcast.cp.internal.datastructures.atomicref;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicReference;
import com.hazelcast.cp.CPGroupId;
import com.hazelcast.cp.internal.datastructures.AbstractAtomicRegisterSnapshotTest;
import com.hazelcast.cp.internal.datastructures.atomicref.proxy.RaftAtomicRefProxy;
import com.hazelcast.test.HazelcastSerialClassRunner;
import com.hazelcast.test.annotation.ParallelTest;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.Before;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@RunWith(HazelcastSerialClassRunner.class)
@Category({QuickTest.class, ParallelTest.class})
public class RaftAtomicRefSnapshotTest extends AbstractAtomicRegisterSnapshotTest<String> {

    protected IAtomicReference<String> atomicRef;

    @Before
    public void setup() {
        HazelcastInstance[] instances = createInstances();
        String name = "ref@group";
        atomicRef = createAtomicRef(instances, name);
    }

    protected IAtomicReference<String> createAtomicRef(HazelcastInstance[] instances, String name) {
        HazelcastInstance apInstance = instances[instances.length - 1];
        return apInstance.getCPSubsystem().getAtomicReference(name);
    }

    @Override
    protected CPGroupId getGroupId() {
        return ((RaftAtomicRefProxy) atomicRef).getGroupId();
    }

    @Override
    protected String setAndGetInitialValue() {
        String value = randomString();
        atomicRef.set(value);
        return value;
    }

    @Override
    protected String readValue() {
        return atomicRef.get();
    }
}
