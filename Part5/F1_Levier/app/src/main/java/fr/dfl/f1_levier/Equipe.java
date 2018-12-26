package fr.dfl.f1_levier;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

@Entity(

        active = true,
        generateConstructors = true,
        generateGettersSetters = true


)
public class Equipe {

    @Id(autoincrement = true)
    private Long IdEquipe;

    @NotNull
    private String nomEquipe;

    /*Generate*/

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1301680150)
    private transient EquipeDao myDao;

    //Une equipe peut avoir n participant
    @ToMany(referencedJoinProperty = "equipeId")
    private List<Participant> participantList;

    @Generated(hash = 421971927)
    public Equipe(Long IdEquipe, @NotNull String nomEquipe) {
        this.IdEquipe = IdEquipe;
        this.nomEquipe = nomEquipe;
    }

    @Generated(hash = 506729621)
    public Equipe() {
    }

    public Long getIdEquipe() {
        return this.IdEquipe;
    }

    public void setIdEquipe(long IdEquipe) {
        this.IdEquipe = IdEquipe;
    }

    public String getNomEquipe() {
        return this.nomEquipe;
    }

    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
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

    public void setIdEquipe(Long IdEquipe) {
        this.IdEquipe = IdEquipe;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1328826733)
    public List<Participant> getParticipantList() {
        if (participantList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ParticipantDao targetDao = daoSession.getParticipantDao();
            List<Participant> participantListNew = targetDao
                    ._queryEquipe_ParticipantList(IdEquipe);
            synchronized (this) {
                if (participantList == null) {
                    participantList = participantListNew;
                }
            }
        }
        return participantList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1493686052)
    public synchronized void resetParticipantList() {
        participantList = null;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 704978136)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getEquipeDao() : null;
    }


}
