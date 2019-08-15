package org.dimensinfin.android.mvc.demo.database;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author Adam Antinoo
 */
@Entity(tableName = "label_table")
public class Label {
    // - F I E L D - S E C T I O N
    @PrimaryKey
    @NonNull
    public UUID id;
    @NonNull
    @ColumnInfo(name = "label_name")
    public String labelName;
    @ColumnInfo(name = "label_desc ription")
    public String labelDescription;

    // - C O N S T R U C T O R - S E C T I O N
    public Label() {
        super();
    }

    // - G E T T E R S   &   S E T T E R S
    @NonNull
    public UUID getId() {
        return id;
    }

    @NonNull
    public String getLabelName() {
        return labelName;
    }

    public String getLabelDescription() {
        return labelDescription;
    }

    public Label setId(@NonNull final UUID id) {
        this.id = id;
        return this;
    }

    public Label setLabelName(@NonNull final String labelName) {
        this.labelName = labelName;
        return this;
    }

    public Label setLabelDescription(final String labelDescription) {
        this.labelDescription = labelDescription;
        return this;
    }

    // - M E T H O D - S E C T I O N
    @Override
    public String toString() {
        return new StringBuilder("Label [")
                .append("name: ").append(0)
                .append("]")
                .append("->").append(super.toString())
                .toString();
    }
}
