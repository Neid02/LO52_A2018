package fr.dfl.f1_levier;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
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
public class Pitstop {

    @Id(autoincrement = true)
    private Long idPitstop;
    @NotNull
    private String nom;
    @NotNull
    private Double tempsPI;

    /*Generate*/

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1069742297)
    private transient PitstopDao myDao;

    //Un pitstop peut avoir n participant a son arret
    @ToMany(referencedJoinProperty = "pitstopId")
    private List<Participant> participantList;

    @Generated(hash = 1052150617)
    public Pitstop(Long idPitstop, @NotNull String nom, @NotNull Double tempsPI) {
        this.idPitstop = idPitstop;
        this.nom = nom;
        this.tempsPI = tempsPI;
    }

    @Generated(hash = 1192300953)
    public Pitstop() {
    }

    public Long getIdPitstop() {
        return this.idPitstop;
    }

    public void setIdPitstop(Long idPitstop) {
        this.idPitstop = idPitstop;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getTempsPI() {
        return this.tempsPI;
    }

    public void setTempsPI(Double tempsPI) {
        this.tempsPI = tempsPI;
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

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 953529122)
    public List<Participant> getParticipantList() {
        if (participantList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ParticipantDao targetDao = daoSession.getParticipantDao();
            List<Participant> participantListNew = targetDao
                    ._queryPitstop_ParticipantList(idPitstop);
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
    @Generated(hash = 1335587631)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPitstopDao() : null;
    }


}
