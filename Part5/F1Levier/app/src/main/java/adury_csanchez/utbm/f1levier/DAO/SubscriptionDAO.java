package adury_csanchez.utbm.f1levier.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import adury_csanchez.utbm.f1levier.model.Race;
import adury_csanchez.utbm.f1levier.model.Subscription;
import adury_csanchez.utbm.f1levier.model.Team;

public class SubscriptionDAO {

    public static final String TAG = "SubscriptionDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = {
            DBHelper.COLUMN_SUBSCRIPTIONS_ID,
            DBHelper.COLUMN_SUBSCRIPTIONS_TEAM_ID,
            DBHelper.COLUMN_SUBSCRIPTIONS_RACE_ID
    };

    public SubscriptionDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Subscription createSubscription(long teamId, long raceId) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_SUBSCRIPTIONS_TEAM_ID, teamId);
        values.put(DBHelper.COLUMN_SUBSCRIPTIONS_RACE_ID, raceId);

        long insertId = mDatabase.insert(DBHelper.TABLE_SUBSCRIPTIONS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_SUBSCRIPTIONS, mAllColumns,DBHelper.COLUMN_SUBSCRIPTIONS_ID + " = " + insertId,
                null, null,null, null);
        cursor.moveToFirst();
        Subscription newSubscription = cursorToSubscription(cursor);
        cursor.close();
        return newSubscription;
    }

    public void deleteSubscription(Subscription subscription) {
        long id = subscription.getId();
        System.out.println("the deleted subscription has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_SUBSCRIPTIONS, DBHelper.COLUMN_SUBSCRIPTIONS_ID
                + " = " + id, null);
    }

    public List<Subscription> getAllSubscription() {
        List<Subscription> listSubscriptions = new ArrayList<Subscription>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_SUBSCRIPTIONS, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Subscription subscription = cursorToSubscription(cursor);
                listSubscriptions.add(subscription);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listSubscriptions;
    }

    public Subscription getSubscriptionById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_SUBSCRIPTIONS, mAllColumns,
                DBHelper.COLUMN_SUBSCRIPTIONS_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Subscription subscription = cursorToSubscription(cursor);
        return subscription;
    }

    protected Subscription cursorToSubscription(Cursor cursor) {
        Subscription subscription = new Subscription();
        subscription.setId(cursor.getLong(0));

        // get the team by id
        long teamId = cursor.getLong(1);
        TeamDAO teamDAO = new TeamDAO(mContext);
        Team team = teamDAO.getTeamById(teamId);
        if (team != null)
            subscription.setTeam(team);

        // get the race by id
        long runnerId = cursor.getLong(2);
        RaceDAO raceDAO = new RaceDAO(mContext);
        Race race = raceDAO.getRaceById(runnerId);
        if (race != null)
            subscription.setRace(race);

        return subscription;
    }
}
