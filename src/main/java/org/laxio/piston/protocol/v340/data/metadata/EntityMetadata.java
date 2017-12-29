package org.laxio.piston.protocol.v340.data.metadata;

import org.laxio.piston.piston.entity.Metadata;
import org.laxio.piston.piston.entity.metadata.MetadataBlob;
import org.laxio.piston.piston.list.LockableLinkedList;

import java.util.List;

public class EntityMetadata implements Metadata {

    private final List<MetadataBlob> blobs;

    public EntityMetadata() {
        this.blobs = new LockableLinkedList<>();
    }

    @Override
    public List<MetadataBlob> getBlobs() {
        return blobs;
    }

}
