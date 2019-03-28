package org.dimensinfin.android.mvc.demo.database;

import org.dimensinfin.android.mvc.model.DemoLabel;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author Adam Antinoo
 */
@Entity(tableName = "group_table")
public class Group extends DemoLabel {
    // - F I E L D - S E C T I O N
    @PrimaryKey
    @NonNull
    public UUID id;
    @ColumnInfo(name = "group_name")
    @NonNull
    public String groupName;

    // - C O N S T R U C T O R - S E C T I O N
    public Group() {
        super();
    }

    // - G E T T E R S   &   S E T T E R S
    @NonNull
    public UUID getId() {
        return id;
    }

    @NonNull
    public String getGroupName() {
        return groupName;
    }

    public Group setId(@NonNull final UUID id) {
        this.id = id;
        return this;
    }

    public Group setGroupName(@NonNull final String groupName) {
        this.groupName = groupName;
        return this;
    }

    // - D A T A B A S E
//    public void store () {
//
//    }

    // - B U I L D E R
    public static class Builder {
        private final Group object;

        public Builder() {
            this.object = new Group();
            this.object.id = UUID.randomUUID();
        }

        public Builder withName(final String name) {
            this.object.groupName = name;
            return this;
        }

        public Group build() {
            return object;
        }
    }
}
