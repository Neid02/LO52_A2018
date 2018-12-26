package fr.dfl.f1_levier;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;

@Entity(
        active = true,
        generateConstructors = true,
        generateGettersSetters = true
)

public class Participant {

    @Id(autoincrement = true)
    private Long idParticipant;
    @NotNull
    private Integer echelon;

    /*Generate*/
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1537769566)
    private transient ParticipantDao myDao;

    // Un participant a une et une seule équipe
    private Long equipeId;
    @ToOne(joinProperty = "equipeId")
    private Equipe equipe;

    //Un participant s'arrete a un pitstop
    private Long pitstopId;
    @ToOne(joinProperty = "pitstopId")
    private Pitstop pitstop;

    //Un participant peut faire n tour
    @ToMany
    @JoinEntity(
            //Table intermediaire
            entity = ParticipantTour.class,
            //Id representant cette table dans la table intermediaire
            sourceProperty = "participantId",
            //Id representant la table voulue dans la table intermediaire donc l'inverse du precedant
            targetProperty = "tourId"


    )
    private List<Tour> tourArrayList;
    @Generated(hash = 1480193453)
    private transient Long equipe__resolvedKey;
    @Generated(hash = 2007200101)
    private transient Long pitstop__resolvedKey;

    @Generated(hash = 1200154759)
    public Participant() {
    }

    @Generated(hash = 819318425)
    public Participant(Long idParticipant, @NotNull Integer echelon, Long equipeId, Long pitstopId) {
        this.idParticipant = idParticipant;
        this.echelon = echelon;
        this.equipeId = equipeId;
        this.pitstopId = pitstopId;
    }

    public Long getIdParticipant() {
        return this.idParticipant;
    }

    public void setIdParticipant(long idParticipant) {
        this.idParticipant = idParticipant;
    }

    public int getEchelon() {
        return this.echelon;
    }

    public void setEchelon(int echelon) {
        this.echelon = echelon;
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

    public void setIdParticipant(Long idParticipant) {
        this.idParticipant = idParticipant;
    }

    public void setEchelon(Integer echelon) {
        this.echelon = echelon;
    }

    public Long getEquipeId() {
        return this.equipeId;
    }

    public void setEquipeId(Long equipeId) {
        this.equipeId = equipeId;
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 629281056)
    public Equipe getEquipe() {
        Long __key = this.equipeId;
        if (equipe__resolvedKey == null || !equipe__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EquipeDao targetDao = daoSession.getEquipeDao();
            Equipe equipeNew = targetDao.load(__key);
            synchronized (this) {
                equipe = equipeNew;
                equipe__resolvedKey = __key;
            }
        }
        return equipe;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 77356707)
    public void setEquipe(Equipe equipe) {
        synchronized (this) {
            this.equipe = equipe;
            equipeId = equipe == null ? null : equipe.getIdEquipe();
            equipe__resolvedKey = equipeId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1366408989)
    public List<Tour> getTourArrayList() {
        if (tourArrayList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TourDao targetDao = daoSession.getTourDao();
            List<Tour> tourArrayListNew = targetDao._queryParticipant_TourArrayList(idParticipant);
            synchronized (this) {
                if (tourArrayList == null) {
                    tourArrayList = tourArrayListNew;
                }
            }
        }
        return tourArrayList;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1789574812)
    public synchronized void resetTourArrayList() {
        tourArrayList = null;
    }

    public Long getPitstopId() {
        return this.pitstopId;
    }

    public void setPitstopId(Long pitstopId) {
        this.pitstopId = pitstopId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1731331456)
    public Pitstop getPitstop() {
        Long __key = this.pitstopId;
        if (pitstop__resolvedKey == null || !pitstop__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PitstopDao targetDao = daoSession.getPitstopDao();
            Pitstop pitstopNew = targetDao.load(__key);
            synchronized (this) {
                pitstop = pitstopNew;
                pitstop__resolvedKey = __key;
            }
        }
        return pitstop;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 799751473)
    public void setPitstop(Pitstop pitstop) {
        synchronized (this) {
            this.pitstop = pitstop;
            pitstopId = pitstop == null ? null : pitstop.getIdPitstop();
            pitstop__resolvedKey = pitstopId;
        }
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1996592993)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getParticipantDao() : null;
    }


}
