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

package com.hazelcast.cp.internal;

import com.hazelcast.cp.CPGroupId;

/**
 * Contains methods that are invoked on life cycle changes of a Raft group
 */
public interface RaftGroupLifecycleAwareService {

    /**
     * Called on the thread of the Raft group when the given Raft group is
     * destroyed, either gracefully or forcefully.
     */
    void onGroupDestroy(CPGroupId groupId);
}
