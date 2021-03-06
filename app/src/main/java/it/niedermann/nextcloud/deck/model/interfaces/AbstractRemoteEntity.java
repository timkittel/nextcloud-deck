package it.niedermann.nextcloud.deck.model.interfaces;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.Date;

import it.niedermann.nextcloud.deck.model.enums.DBStatus;

@Entity(
        indices = {
                @Index("accountId"),
                @Index("id"),
                @Index("lastModifiedLocal"),
                @Index(value = {"accountId", "id"}, unique = true)
        })
public abstract class AbstractRemoteEntity implements IRemoteEntity {
    @PrimaryKey(autoGenerate = true)
    protected Long localId;

    protected long accountId;

    protected Long id;

    @NonNull
    protected int status = DBStatus.UP_TO_DATE.getId();

    protected Date lastModified;
    protected Date lastModifiedLocal;

    @Ignore
    @Override
    public IRemoteEntity getEntity() {
        return this;
    }

    @Override
    public Long getLocalId() {
        return localId;
    }


    @Override
    public void setLocalId(Long localId) {
        this.localId = localId;
    }


    @Override
    public long getAccountId() {
        return accountId;
    }


    @Override
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }


    @Override
    public Long getId() {
        return id;
    }


    @Override
    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public int getStatus() {
        return status;
    }


    @Override
    public void setStatus(@NonNull int status) {
        this.status = status;
    }


    @Override
    public Date getLastModified() {
        return this.lastModified;
    }


    @Override
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }


    @Override
    public Date getLastModifiedLocal() {
        return this.lastModifiedLocal;
    }


    @Override
    public void setLastModifiedLocal(Date lastModifiedLocal) {
        this.lastModifiedLocal = lastModifiedLocal;
    }


    @Ignore
    @Override
    public DBStatus getStatusEnum() {
        return DBStatus.findById(status);
    }


    @Ignore
    @Override
    public void setStatusEnum(DBStatus status) {
        this.status = status.getId();
    }
}
