package com.bigohealth.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bigohealth.model.Doctor;

@Database(entities = {Doctor.class}, version = 2, exportSchema = false) // CHECK!!?
public abstract class DoctorDatabase extends RoomDatabase {

    public abstract DoctorDao wordDao();

    private static volatile DoctorDatabase INSTANCE;

    public static DoctorDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DoctorDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =  Room.databaseBuilder(context.getApplicationContext(),
                            DoctorDatabase.class, "word_database")
                            .addMigrations(MIGRATION_1_2)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
            database.execSQL("ALTER TABLE 'doctor_data' ADD COLUMN 'docDiscountedFee' TEXT");
            database.execSQL("ALTER TABLE 'doctor_data' ADD COLUMN 'docOnlinePayFee' TEXT");
        }
    };


}