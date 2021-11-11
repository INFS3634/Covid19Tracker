package au.edu.unsw.infs3634.covid19tracker;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Country.class}, version = 1)
public abstract class CountyDatabase extends RoomDatabase {
    public abstract CountryDao countryDao();
}
