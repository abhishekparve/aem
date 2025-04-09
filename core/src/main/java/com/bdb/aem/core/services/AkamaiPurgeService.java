package com.bdb.aem.core.services;

import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationResult;

import java.util.List;

/**
 * The Interface AkamaiPurgeService.
 */
public interface AkamaiPurgeService {

    /**
     * purgePage
     *
     * @param replicationAction the event
     */
    public ReplicationResult purgePage(final ReplicationAction replicationAction);

    public ReplicationResult purgeAssets(List<String> resource);

}
