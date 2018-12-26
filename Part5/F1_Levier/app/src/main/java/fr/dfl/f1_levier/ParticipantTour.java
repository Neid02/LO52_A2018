package fr.dfl.f1_levier;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(
        active = true,
        generateConstructors = true,
        generateGettersSetters = true
)
public class ParticipantTour {
    @Id(autoincrement = true)
    private Long id;
    private Long participantId;
    private Long tourId;
/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;
/** Used for active entity operations. */
@Generated(hash = 1703295862)
private transient ParticipantTourDao myDao;
@Generated(hash = 399631755)
public ParticipantTour(Long id, Long participantId, Long tourId) {
    this.id = id;
    this.participantId = participantId;
    this.tourId = tourId;
}
@Generated(hash = 1787756634)
public ParticipantTour() {
}
public Long getId() {
    return this.id;
}
public void setId(Long id) {
    this.id = id;
}
public Long getParticipantId() {
    return this.participantId;
}
public void setParticipantId(Long participantId) {
    this.participantId = participantId;
}
public Long getTourId() {
    return this.tourId;
}
public void setTourId(Long tourId) {
    this.tourId = tourId;
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
/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1668163685)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getParticipantTourDao() : null;
}
}
