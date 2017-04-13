/*
 * Copyright (c) 2008-2017, Hazelcast, Inc. All Rights Reserved.
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

package com.hazelcast.spi;

import java.util.Collection;

/**
 * {@code FragmentedMigrationAwareService} is an extension to the {@link MigrationAwareService} which allows
 * migration/replication of partition replicas in smaller fragments.
 * <p>
 * Each replica fragment is distinguished by a {@link ReplicaFragmentNamespace}, all replica data belonging to
 * a specific namespace will be transferred in the same packet.
 * <p>
 * Backup operations created by {@code FragmentedMigrationAwareService} must implement {@link ReplicaFragmentAware} interface
 * and must know their related {@link ReplicaFragmentNamespace}s.
 * <p>
 * Fragmented migration can be enabled/disabled using configuration property
 * {@link com.hazelcast.spi.properties.GroupProperty#PARTITION_FRAGMENTED_MIGRATION_ENABLED}. Anti-entropy system which
 * detects and completes inconsistent backup replicas, always uses fragmented replication for services having support.
 *
 * @see MigrationAwareService
 * @see ReplicaFragmentNamespace
 * @see ReplicaFragmentAware
 * @since 3.9
 */
public interface FragmentedMigrationAwareService extends MigrationAwareService {

    /**
     * Returns all known namespaces for given replication event.
     *
     * @param event replication event
     * @return all known replica fragment namespaces for the replication
     */
    Collection<ReplicaFragmentNamespace> getAllFragmentNamespaces(PartitionReplicationEvent event);

    /**
     * Returns an operation to replicate service data and/or state for a specific partition replica and namespaces
     * on another cluster member. This method is very similar to
     * {@link #prepareReplicationOperation(PartitionReplicationEvent)},
     * instead of copying whole partition replica, allows copying only some portion of replica,
     * specified by given namespaces.
     *
     * <p>
     * This method will be called on source member whenever partitioning system requires
     * to copy/replicate a partition replica. Returned operation will be executed on destination member.
     * If operation fails by throwing exception, migration process will fail and will be rolled back.
     * <p>
     * Returning null is allowed and means service does not have anything to replicate.
     *
     * @param event replication event
     * @param namespaces replica fragment namespaces to replicate
     * @return replication operation or null if nothing will be replicated
     * @see #prepareReplicationOperation(PartitionReplicationEvent)
     */
    Operation prepareReplicationOperation(PartitionReplicationEvent event, Collection<ReplicaFragmentNamespace> namespaces);

}
