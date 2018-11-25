package it.niedermann.nextcloud.deck.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import java.util.List;

import it.niedermann.nextcloud.deck.model.enums.DBStatus;
import it.niedermann.nextcloud.deck.model.interfaces.RemoteEntity;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity
public class Stack implements RemoteEntity {
    @Id(autoincrement = true)
    protected Long localId;

    @NotNull
    long accountId;

    @ToOne(joinProperty = "accountId")
    protected Account account;

    protected Long id;

    @NotNull
    @Index
    protected int status = DBStatus.UP_TO_DATE.getId();
    private String title;
    @NotNull
    @Index
    private long boardId;
    @ToOne(joinProperty = "boardId")
    protected Board board;

    private Date deletedAt;
    @NotNull
    @Index
    private int order;

    @ToMany
    @JoinEntity(entity = JoinStackWithCard.class, sourceProperty = "stackId", targetProperty = "cardId")
    private List<Card> cards;

    private Date lastModified;
    private Date lastModifiedLocal;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1489053249)
    private transient StackDao myDao;

    @Generated(hash = 1501133588)
    private transient Long account__resolvedKey;

    @Generated(hash = 483146929)
    private transient Long board__resolvedKey;

    public Stack() {
        super();
    }

    @Generated(hash = 1703952681)
    public Stack(Long localId, long accountId, Long id, int status, String title, long boardId,
            Date deletedAt, int order, Date lastModified, Date lastModifiedLocal) {
        this.localId = localId;
        this.accountId = accountId;
        this.id = id;
        this.status = status;
        this.title = title;
        this.boardId = boardId;
        this.deletedAt = deletedAt;
        this.order = order;
        this.lastModified = lastModified;
        this.lastModifiedLocal = lastModifiedLocal;
    }

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }

    @Override
    public Date getLastModified() {
        return lastModified;
    }

    @Override
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public Date getLastModifiedLocal() {
        return lastModifiedLocal;
    }

    @Override
    public void setLastModifiedLocal(Date lastModifiedLocal) {
        this.lastModifiedLocal = lastModifiedLocal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public DBStatus getStatusEnum() {
        return DBStatus.findById(status);
    }

    public void setStatusEnum(DBStatus status) {
        this.status = status.getId();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2143533054)
    public Account getAccount() {
        long __key = this.accountId;
        if (account__resolvedKey == null || !account__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AccountDao targetDao = daoSession.getAccountDao();
            Account accountNew = targetDao.load(__key);
            synchronized (this) {
                account = accountNew;
                account__resolvedKey = __key;
            }
        }
        return account;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1716871290)
    public void setAccount(@NotNull Account account) {
        if (account == null) {
            throw new DaoException(
                    "To-one property 'accountId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.account = account;
            accountId = account.getId();
            account__resolvedKey = accountId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 430121608)
    public List<Card> getCards() {
        if (cards == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CardDao targetDao = daoSession.getCardDao();
            List<Card> cardsNew = targetDao._queryStack_Cards(localId);
            synchronized (this) {
                if (cards == null) {
                    cards = cardsNew;
                }
            }
        }
        return cards;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 189953180)
    public synchronized void resetCards() {
        cards = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    public int getStatus() {
        return this.status;
    }

    public long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 202456218)
    public Board getBoard() {
        long __key = this.boardId;
        if (board__resolvedKey == null || !board__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BoardDao targetDao = daoSession.getBoardDao();
            Board boardNew = targetDao.load(__key);
            synchronized (this) {
                board = boardNew;
                board__resolvedKey = __key;
            }
        }
        return board;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 260233114)
    public void setBoard(@NotNull Board board) {
        if (board == null) {
            throw new DaoException(
                    "To-one property 'boardId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.board = board;
            boardId = board.getLocalId();
            board__resolvedKey = boardId;
        }
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 864150852)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getStackDao() : null;
    }

}
