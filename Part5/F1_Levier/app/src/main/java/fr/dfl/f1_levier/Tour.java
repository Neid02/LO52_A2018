package fr.dfl.f1_levier;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

@Entity(
        active = true,
        generateConstructors = true,
        generateGettersSetters = true
)
public class Tour {
    @Id(autoincrement = true)
    private Long idTour;
    @NotNull
    private String typeTour;
    @NotNull
    private Double tempsTour;

    /*Generate*/
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 270320152)
    private transient TourDao myDao;
    @ToMany
    @JoinEntity(
            //Table intermediaire
            entity = ParticipantTour.class,
            //Id representant cette table dans la table intermediaire
            sourceProperty = "tourId",
            //Id representant la table voulue dans la table intermediaire donc l'inverse du precedant
            targetProperty ="participantId"


    )
    private List<Participant> participantArrayList;

    @Generated(hash = 1138121136)
    public Tour(Long idTour, @NotNull String typeTour, @NotNull Double tempsTour) {
        this.idTour = idTour;
        this.typeTour = typeTour;
        this.tempsTour = tempsTour;
    }

    @Generated(hash = 419901603)
    public Tour() {
    }

    public Long getIdTour() {
        return this.idTour;
    }

    public void setIdTour(long idTour) {
        this.idTour = idTour;
    }

    public String getTypeTour() {
        return this.typeTour;
    }

    public void setTypeTour(String typeTour) {
        this.typeTour = typeTour;
    }

    public double getTempsTour() {
        return this.tempsTour;
    }

    public void setTempsTour(double tempsTour) {
        this.tempsTour = tempsTour;
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

    public void setIdTour(Long idTour) {
        this.idTour = idTour;
    }

    public void setTempsTour(Double tempsTour) {
        this.tempsTour = tempsTour;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 639142403)
    public List<Participant> getParticipantArrayList() {
        if (participantArrayList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ParticipantDao targetDao = daoSession.getParticipantDao();
            List<Participant> participantArrayListNew = targetDao
                    ._queryTour_ParticipantArrayList(idTour);
            synchronized (this) {
                if (participantArrayList == null) {
                    participantArrayList = participantArrayListNew;
                }
            }
        }
        return participantArrayList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 891454995)
    public synchronized void resetParticipantArrayList() {
        participantArrayList = null;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 194450145)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTourDao() : null;
    }




}
